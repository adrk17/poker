package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.gameStructure.GameManager;
import pl.edu.agh.kis.pz1.gameStructure.SetParser;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Klasa reprezentująca serwer gry
 */
public class Server {
    // selector do obsługi wielu klientów
    private static Selector selector = null;
    // liczba graczy
    private static int numOfPlayers = 4;
    // socket serwera i jego kanał
    private static ServerSocketChannel sSocket = null;
    private static ServerSocket serverSocket = null;
    // menadżer gry zażądzający rozgrywką
    private static GameManager gm;
    // bool do sprawdzania czy gra się zakończyła
    private static boolean newRound = true;

    /**
     * Metoda główna serwera - służy do inicjalizacji i uruchomienia serwera gry oraz do obsługi klientów
     * @param args argumenty wywołania - przyjmuje jedynie jedną liczbę całkowitą - liczbę graczy
     */
    public static void main(String[] args) {
        System.out.println("Starting server...");
        //obsługa argumentów wywołania
        handleArguments(args);
        try {
            // otwiera nowy selektor dla kanałów klientów łączących się z serwerem
            selector = Selector.open();
            // tworzy socket serwera, z którym będą łączyć się klienci
            sSocket = ServerSocketChannel.open();
            serverSocket = sSocket.socket();
            serverSocket.bind(new InetSocketAddress("localhost", 8000));
            // konfiguruje socket dla nieblokującego IO 
            sSocket.configureBlocking(false);
            // ustawia selektor z dozwolonymi operacjami dla socketu
            int ops = sSocket.validOps();

            sSocket.register(selector, ops, null);
            System.out.println("Server started");

            //faza dołączania graczy
            connectAllPlayers();
            //faza gry która powtarza się dopóki gra się nie zakończy
            while (newRound) {
                gm = new GameManager(numOfPlayers);
                gm.initializeGame();
                sendPlayersTheirCards(gm);
                sendMessagesToAllClients("Please select a maximum of 2 card indexes(0,1,2,3,4) to exchange.\nIf you don't want to discard any cards, type '-1'.\n" +
                        "(Example formats:\n0,1\n3\n-1\n4,2)");
                receiveCardsToDiscard();
                sendResultsToAllPlayers();
                getInfoAboutNewRound();
            }
            //koniec gry i zamknięcie socketu
            System.out.println("Server closed");
            serverSocket.close();

        } catch (IOException e) {
            System.out.println("Connection error");
        }
    }

    /**
     * Metoda służąca do obsługi argumentów wywołania serwera
     * @param args argumenty wywołania
     */
    private static void handleArguments(String[] args) {
        if (args.length == 1) {
            try {
                numOfPlayers = Integer.parseInt(args[0]);
                if (numOfPlayers < 2 || numOfPlayers > 4) {
                    System.out.println("Wrong number of players, default will be used");
                    numOfPlayers = 2;
                }
            } catch (NumberFormatException e) {
                System.out.println("Wrong argument, default number of players will be used");
                numOfPlayers = 2;
            }
        }
    }
    /**
     * Metoda służąca do dołączania graczy do gry, wywoływana w momencie uruchomienia serwera
     *  serwer czeka aż wszyscy gracze dołączą do gry
     * @throws IOException wyjątek wyrzucany w przypadku błędu IO
     */
    private static void connectAllPlayers() throws IOException {
        int connectedPlayers = 0;
        try {
            System.out.println("["+connectedPlayers +"/"+ numOfPlayers+"] players connected...");
            // dopóki nie dołączy się odpowiednia liczba graczy serwer czeka na ich połączenie
            while (connectedPlayers < numOfPlayers) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keysIterator = selectedKeys.iterator();
                while (keysIterator.hasNext()) {
                    SelectionKey key = keysIterator.next();
                    if (key.isAcceptable()) {
                        try {
                            connectPlayer(sSocket);
                            connectedPlayers++;
                            System.out.println("["+connectedPlayers +"/"+ numOfPlayers+"] players connected...");
                        }
                        catch (IOException e) {
                            System.out.println("Couldn't connect player");
                            System.exit(1);
                        }
                    }
                    keysIterator.remove();
                }
            }
            System.out.println("["+connectedPlayers +"/"+ numOfPlayers+"] players connected...");
        } catch (IOException e) {
            System.out.println("Connection error - couldn't connect to all players");
            System.exit(1);
        }
    }
    /**
     * Metoda służąca do dołączenia pojedynczego gracza do gry
     * @param sSocket socket serwera
     * @throws IOException wyjątek wyrzucany w przypadku błędu IO
     */
    private static void connectPlayer(ServerSocketChannel sSocket) throws IOException {
        SocketChannel client = sSocket.accept();
        client.configureBlocking(false);
        // dodaje request klienta w selektorze z określoną operacją np. OP_READ albo OP_WRITE
        client.register(selector, SelectionKey.OP_READ);
    }
    /**
     * Metoda służąca do wysłania kart do graczy, GameMenager jest wykorzystywany do pobrania kart które
     * mają zostać wysłane do graczy w formie Stringa, komunikatu
     * @param gm menadżer gry
     * @throws IOException wyjątek wyrzucany w przypadku błędu IO
     */
    private static void sendPlayersTheirCards(GameManager gm){
        int sentCards = 0;
        try {
            System.out.println("Sending cards to players...");
            // dopóki nie zostaną wysłane karty do wszystkich graczy serwer czeka na ich wysłanie
            while (sentCards < numOfPlayers){
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keysIterator = selectedKeys.iterator();
                while (keysIterator.hasNext()) {
                    SelectionKey key = keysIterator.next();
                    if (key.isReadable()) {
                        int playerID = sentCards;
                        key.attach(playerID);
                        String s = gm.getCardDeckInfo(playerID);
                        sendMessageToClient(s, key);
                        sentCards++;
                        System.out.println("sent cards to player #" + playerID);
                    }else{
                        System.out.println("key not readible");
                    }
                    keysIterator.remove();
                    System.out.println("cards sent to all players");
                }
            }
        } catch (IOException e) {
            System.out.println("Connection error - couldn't send players their cards");
            System.exit(1);
        }
    }
    /**
     * Metoda służąca do wysłania wiadomości do wszystkich klientów
     * @param message wiadomość do wysłania
     * @throws IOException wyjątek wyrzucany w przypadku błędu IO
     */
    private static void sendMessagesToAllClients(String message){
        try {
            int sentMessages = 0;
            while (sentMessages < numOfPlayers){
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keysIterator = selectedKeys.iterator();
                while (keysIterator.hasNext()) {
                    SelectionKey key = keysIterator.next();
                    if (key.isReadable()) {
                        sendMessageToClient(message, key);
                        sentMessages++;
                    }
                    keysIterator.remove();
                }
            }
        }
        catch (IOException e) {
            System.out.println("Connection error - couldn't send messages to all clients");
            System.exit(1);
        }
    }
    /**
     * Metoda służąca do wysłania wiadomości do pojedynczego klienta
     * @param message wiadomość do wysłania
     * @param key klucz do klienta
     * @throws IOException wyjątek wyrzucany w przypadku błędu IO
     */
    private static void sendMessageToClient(String message, SelectionKey key) {
        try{
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            SocketChannel client = (SocketChannel) key.channel();
            System.out.println("Sending message to the client...");
            buffer.clear();
            buffer = ByteBuffer.wrap(message.getBytes());
            client.write(buffer);
            buffer.clear();
        }
        catch (IOException e) {
            System.out.println("Connection error - couldn't send message to client");
            System.exit(1);
        }
    }
    /**
     * Metoda służąca do odbierania wiadomości od wskazanego klienta
     * @param key klucz do klienta
     * @throws IOException wyjątek wyrzucany w przypadku błędu IO
     */
    private static String readFromClient(SelectionKey key){
        try {
            // dla klucza key z selektora - requestu - tworzy kanał
            SocketChannel client = (SocketChannel) key.channel();
            // odczytuje bufor z kanału
            ByteBuffer buffer = ByteBuffer.allocate(512);
            client.read(buffer);

            return new String(buffer.array()).trim();
        }
        catch (IOException e) {
            System.out.println("Connection error - couldn't read message from client");
            System.exit(1);
        }
        return "error";
    }

    /**
     * Metoda służąca do odebrania informacji od klientów o ich ruchach - w tym wypadku o ich decyzji
     * co do wyboru kart do wymiany. Metoda wysyła również wiadomość o nowych kartach do klientów
     */
    private static void receiveCardsToDiscard(){
        try {
            int receivedMessages = 0;
            // dopóki nie otrzyma wiadomości od wszystkich graczy serwer czeka na ich otrzymanie
            while (receivedMessages < numOfPlayers){
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keysIterator = selectedKeys.iterator();
                while (keysIterator.hasNext()) {
                    SelectionKey key = keysIterator.next();
                    // sprawdzanie czy request jest do odczytu i czy należy do odpowiedniego gracza
                    if (key.isReadable() && (int)key.attachment() == receivedMessages) {
                        String message = readFromClient(key);
                        if (!message.isEmpty()){
                            int playerID = (int) key.attachment();
                            System.out.println("Player #" + playerID + " sent message: " + message);
                            SetParser sp = new SetParser(message);
                            // wiadomość pusta bez żadnych kart do wymiany
                            if(message.equals("-1")) {
                                sendMessageToClient("No cards were exchanged\n" + gm.getCardDeckInfo(playerID)+"\n\n", key);
                                receivedMessages++;
                            }
                            // wiadomość z poprawnym formatem, do klientów wysyłane są nowe karty
                            else if(sp.getReport().equals("ok")){
                                gm.changeCards(playerID, sp.getResultSet());
                                receivedMessages++;
                                sendMessageToClient(gm.getCardDeckInfo(playerID)+"\n\n", key);
                            // wiadomość z niepoprawnym formatem, do klientów wysyłane są stare karty
                            }else{
                                sendMessageToClient("Wrong format, no cards were exchanged\n" + gm.getCardDeckInfo(playerID)+"\n\n", key);
                                receivedMessages++;
                            }
                        }

                    }
                    keysIterator.remove();
                }
            }
        }
        catch (IOException e) {
            System.out.println("Connection error - couldn't receive messages from all clients");
            System.exit(1);
        }
    }

    /**
     * Metoda służąca do wysłania rezultatów gry do wszystkich graczy
     */
    private static void sendResultsToAllPlayers(){
        String results = gm.showWholeTable() + gm.endGame();
        sendMessagesToAllClients(results);
    }


    /**
     * Metoda odpowiedzialna za obsługę decyzji graczy o rozpoczęciu nowej rundy
     * Jeżeli co najmniej 1 wiadomość o chęci rozpoczęcia nowej rundy jest negatywna, to rozgrywka kończy się
     * a do klientów wysyłana jest wiadomość o końcu gry
     */
    private static void getInfoAboutNewRound(){
        try {
            int receivedMessages = 0;
            boolean gameOver = false;
            // czeka na wiadomości od wszystkich graczy
            while (receivedMessages < numOfPlayers){
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keysIterator = selectedKeys.iterator();
                while (keysIterator.hasNext()) {
                    SelectionKey key = keysIterator.next();
                    if (key.isReadable() && (int)key.attachment() == receivedMessages) {
                        String message = readFromClient(key);
                        if (!message.isEmpty()){
                            int playerID = (int) key.attachment();
                            System.out.println("Player #" + playerID + " sent message: " + message);
                            if(message.equals("y")){
                                receivedMessages++;
                            }else if(message.equals("n")){
                                receivedMessages++;
                                newRound = false;
                                gameOver = true;
                            }
                        }

                    }
                    keysIterator.remove();
                }
            }
            if(gameOver){
                sendMessagesToAllClients("GameOver");
            }
        }
        catch (IOException e) {
            System.out.println("Connection error - couldn't receive messages from all clients");
            System.exit(1);
        }
    }
}
