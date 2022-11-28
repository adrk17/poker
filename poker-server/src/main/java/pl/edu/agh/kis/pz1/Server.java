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

public class Server {
    private static Selector selector = null;
    private static int numOfPlayers = 2;
    private static ServerSocketChannel sSocket = null;
    private static ServerSocket serverSocket = null;
    private static GameManager gm;
    public static void main(String[] args) {
        System.out.println("Starting server...");
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

            connectAllPlayers();
            gm = new GameManager(numOfPlayers);
            gm.initializeGame();
            sendPlayersTheirCards(gm);
            sendMessagesToAllClients("Please select a maximum of 2 card indexes(0,1,2,3,4) to exchange.\nIf you don't want to discard any cards, type '-1'.\n" +
                    "Example formats: 0,1\n3\n-1\n4,2");
            receiveCardsToDiscard();

        } catch (IOException e) {
            System.out.println("Connection error");
        }
    }
    private static void connectAllPlayers() throws IOException {
        int connectedPlayers = 0;
        try {
            System.out.println("["+connectedPlayers +"/"+ numOfPlayers+"] players connected...");
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
    private static void connectPlayer(ServerSocketChannel sSocket) throws IOException {
        SocketChannel client = sSocket.accept();
        client.configureBlocking(false);
        // dodaje request klienta w selektorze z określoną operacją np. OP_READ albo OP_WRITE
        client.register(selector, SelectionKey.OP_READ);
    }
    private static void sendPlayersTheirCards(GameManager gm){
        int sentCards = 0;
        try {
            System.out.println("Sending cards to players...");
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
    private static String readFromClient(SelectionKey key){
        try {
            // dla klucza key z selektora - requestu - tworzy kanał
            SocketChannel client = (SocketChannel) key.channel();
            // odczytuje bufor z kanału
            ByteBuffer buffer = ByteBuffer.allocate(512);
            client.read(buffer);
            String message = new String(buffer.array()).trim();

            if (message.equalsIgnoreCase("exit")) {
                client.close();
                System.out.println("Connection Closed by Client");
            }
            return message;
        }
        catch (IOException e) {
            System.out.println("Connection error - couldn't read message from client");
            System.exit(1);
        }
        return "error";
    }

    private static void receiveCardsToDiscard(){
        try {
            int receivedMessages = 0;
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
                            SetParser sp = new SetParser(message);
                            if(message.equals("-1")) {
                                sendMessageToClient("No cards were exchanged\n" + gm.getCardDeckInfo(playerID), key);
                            }
                            else if(sp.getReport().equals("ok")){
                                gm.changeCards(playerID, sp.getResultSet());
                                receivedMessages++;
                                sendMessageToClient(gm.getCardDeckInfo(playerID), key);
                            }else{
                                sendMessageToClient("Wrong format, no cards were exchanged\n" + gm.getCardDeckInfo(playerID), key);
                            }
                            receivedMessages++;
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
}
