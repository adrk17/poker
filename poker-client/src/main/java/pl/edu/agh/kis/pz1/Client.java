package pl.edu.agh.kis.pz1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    private static SocketChannel server = null;
    private static int port = 8000;
    private static boolean connected = false;

    public static void main(String[] args) throws IOException {
        System.out.println("Starting client...");
        System.out.println("select server port, default is 8000");
        connectToServer();
        getCards();
        System.out.println(getMessageFromServer());
        sendCardIndicesToServer();
        System.out.println(getMessageFromServer());
        while (true){

        }
    }
    static void connectToServer(){
        Scanner scanner = new Scanner(System.in);
        try{
            try{
                port = scanner.nextInt();
            }
            catch (Exception e){
                System.out.println("wrong port, default will be used");
            }
            server = SocketChannel.open();
            server.connect(new InetSocketAddress("localhost", port));
            System.out.println("Connected to server");
            connected = true;
        } catch (IOException e) {
            System.out.println("Connection error - couldn't connect to server");
            System.exit(1);
        }
    }

    private static void getCards() throws IOException {
        System.out.println(getMessageFromServer());
    }

    private static String getMessageFromServer() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        server.write(buffer);
        buffer.flip();
        server.read(buffer);
        buffer.flip();
        return new String(buffer.array()).trim();
    }

    private static void sendCardIndicesToServer() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        server.write(buffer);
    }
}
