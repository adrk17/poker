package pl.edu.agh.kis.pz1;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import org.apache.log4j.*;

/**
 * Klasa klienta
 */
public class Client {
    private static SocketChannel server = null;
    private static int port = 8000;
    private static boolean connected = false;
    private static final Logger logger = Logger.getLogger(Client.class);
    /**
     * Metoda main czyli główna metoda klienta
     * @param args - argumenty wywołania programu, nie są brane pod uwagę
     */
    public static void main(String[] args) throws IOException {

        logger.info("Starting client...");
        logger.info("select server port, default is 8000");
        // dołączenie do serwera
        connectToServer();
        logger.info("Waiting for other players to connect...");
        // odebranie kart podczas pierwszej rundy
        getCards();
        // rozpoczęcie gry ta część programu wykonuje się tyle razy ile rund trwa gra
        while (connected) {
            logger.info("\n"+getMessageFromServer());
            // wysyłanie akcji do serwera
            sendCardIndicesToServer();
            logger.info(getMessageFromServer());
            logger.info("Waiting for other players to play...");
            logger.info("\n"+getMessageFromServer());
            logger.info("Thank you for playing!");
            // decyzja o dalszym udziale w grze
            checkIfNextRound();
        }
        server.close();
    }
    /**
     * Metoda łącząca się z serwerem i tworząca socket
     * metoda ta jest wywoływana tylko raz na początku gry
     * klient wybiera port serwera i łączy się z nim
     * i oczekuje aż reszta graczy dołączy
     */
    static void connectToServer(){
        Scanner scanner = new Scanner(System.in);
        try{
            scanForInt(scanner);
            server = SocketChannel.open();
            server.connect(new InetSocketAddress("localhost", port));
            logger.info("Connected to server");
            connected = true;
        } catch (IOException e) {
            logger.info("Connection error - couldn't connect to server");
            System.exit(1);
        }
    }

    /**
     * Metoda wczytująca port serwera
     * @param scanner - obiekt klasy Scanner
     */
    private static void scanForInt(Scanner scanner) {
        try{
            port = scanner.nextInt();

        }
        catch (Exception e){
            logger.info("wrong port, default will be used");
        }
    }

    /**
     * Metoda odbierająca wiadomość od serwera a w tym wypadku specyficznie odbiera wiadomość z kartami
     */
    private static void getCards() throws IOException {
        logger.info(getMessageFromServer());
    }

    /**
     * Metoda odbierająca wiadomość od serwera
     * @return - zwraca wiadomość od serwera
     * @throws IOException - wyjątek wyrzucany w przypadku błędu odczytu
     */
    private static String getMessageFromServer() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        server.write(buffer);
        buffer.flip();
        server.read(buffer);
        buffer.flip();
        return new String(buffer.array()).trim();
    }

    /**
     * Metoda wysyłająca wiadomość do serwera, w tym wypadku informacje o dycyzji w sprawie
     * wymiany kart
     * @throws IOException - wyjątek wyrzucany w przypadku błędu IO
     */
    private static void sendCardIndicesToServer() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        server.write(buffer);
        logger.info("Cards sent to the server");
    }

    /**
     * Metoda sprawdzająca czy następna runda ma się odbyć - wniej gracze podejmują decyzje
     * o chęci przystąpienia do następnej
     * @throws IOException - wyjątek wyrzucany w przypadku błędu IO
     */
    private static void checkIfNextRound() throws IOException {
        String input = "";
        Scanner scanner = new Scanner(System.in);
        while (!input.equals("y") && !input.equals("n")) {
            logger.info("If you want to play another round press y, if not press n");
            scanner.useDelimiter("");
            input = scanner.next();
        }
        ByteBuffer buffer = ByteBuffer.wrap(input.getBytes());
        server.write(buffer);
        String message = getMessageFromServer();
        logger.info(message);
        if (message.equals("GameOver")) {
            connected = false;
        }
    }
}
