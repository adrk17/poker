package pl.edu.agh.kis.pz1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    private int plID = -1000;
    private static SocketChannel client = null;
    private static int port = 8000;
    private static ByteBuffer buffer = ByteBuffer.allocate(1024);
    private static boolean connected = false;
    public static void main(String[] args) throws IOException {
        System.out.println("Starting client...");
        System.out.println("select socket port, default is 8000");
        connectToServer();
        while (connected) {
            getCardsAndID();
            connected = false;
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
            client = SocketChannel.open();
            client.connect(new InetSocketAddress("localhost", port));
            System.out.println("Connected to server");
            connected = true;
        } catch (IOException e) {
            System.out.println("Connection error - couldn't connect to server");
            System.exit(1);
        }
    }

    private static String getCardsAndID() throws IOException {
        buffer.clear();
        client.write(buffer);
        buffer.flip();
        client.read(buffer);
        buffer.flip();
        String s = new String(buffer.array()).trim();
        System.out.println(s);
        return s;
    }
}
