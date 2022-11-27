package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.gameStructure.GameManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.Set;

public class Server {
    private static Selector selector = null;
    private static int numOfPlayers = 1;
    private static ServerSocketChannel sSocket = null;
    private static ServerSocket serverSocket = null;
    private static GameManager gm;
    private static ByteBuffer buffer = ByteBuffer.allocate(1024);
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
            connectAllPlayers();
            gm = new GameManager(numOfPlayers);
            gm.initializeGame();
            sendPlayersTheirCards(gm);
        } catch (IOException e) {
            System.out.println("Connection error");
        }
    }
    private static void connectAllPlayers() throws IOException {
        int connectedPlayers = 0;
        try {
            while (connectedPlayers < numOfPlayers) {
                System.out.println("["+connectedPlayers +"/"+ numOfPlayers+"] players connected...");
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keysIterator = selectedKeys.iterator();
                while (keysIterator.hasNext()) {
                    SelectionKey key = keysIterator.next();
                    if (key.isAcceptable()) {
                        try {
                            connectPlayer(sSocket);
                            connectedPlayers++;
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
                        String s = playerID + "Your cards are: \n" + gm.getCardDeckInfo(playerID);
                        sendPlayerCards(s, key);
                        sentCards++;
                        System.out.println("sent cards to player #" + playerID);
                    }else{
                        System.out.println("key not readible");
                    }
                    keysIterator.remove();
                    System.out.println("cards dealt");
                }
            }

        } catch (IOException e) {
            System.out.println("Connection error - couldn't send players their cards");
            System.exit(1);
        }
    }

    private static void sendPlayerCards(String message, SelectionKey key) throws IOException{
        SocketChannel client = (SocketChannel) key.channel();
        System.out.println("Sending message to the client...");
        buffer.clear();
        buffer = ByteBuffer.wrap(message.getBytes());
        client.write(buffer);
        buffer.clear();
    }
    private static void readFromClient(SelectionKey key) throws IOException {
        System.out.println("Read message:");
        // dla klucza key z selektora - requestu - tworzy kanał
        SocketChannel client = (SocketChannel) key.channel();
        // odczytuje bufor z kanału
        ByteBuffer buffer = ByteBuffer.allocate(512);
        client.read(buffer);
        String data = new String(buffer.array()).trim();

        if (data.equalsIgnoreCase("exit")) {
            client.close();
            System.out.println("Connection Closed by Client");
        } else if (!data.isEmpty()) {
            System.out.println("Message: " + data);

            buffer.clear();
            buffer.put("Hello from server".getBytes());
            buffer.flip();
            client.write(buffer);
            buffer.clear();
        } else if(data.isEmpty()){
            System.out.println("Empty message");
        }
    }
}
