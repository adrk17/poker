package pl.edu.agh.kis.pz1.gameStructure;
import pl.edu.agh.kis.pz1.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
/**
 * Klasa zarządzanjąca grą
 */
public class GameManager {
    /**
     * Pole zawierające liczbę graczy biorących udział w grze
     */
    int numPlayers;
    /**
     * Pole zawierające talię dilera
     */
    Deck bank;
    /**
     * Pole zawierające listę talii graczy
     */
    List<Deck> players;
    /**
     * Instancja klasy weryfikującej ręke graczy
     */
    HandVerificator hv;
    Logger logger = Logger.getLogger(GameManager.class);
    final String plString = "player #";
    /**
     * Konstruktor klasy GameManager
     * @param _numPlayers to parametr reprezentujący liczbę graczy biorących udział w grze
     */
    public GameManager(int _numPlayers){
        numPlayers = _numPlayers;
        BasicConfigurator.configure();
    }

    /**
     * Konstruktor klasy GameManager który buduje grę na podstawie istniejących talii graczy
     * używane w testowaniu
     * @param _players - lista talii graczy
     */
    public GameManager(List<Deck> _players){
        numPlayers = _players.size();
        players = _players;
    }

    public Deck getPlayersDeck(int index){
        return players.get(index);
    }
    public int getPlayerNum(){
        return numPlayers;
    }
    public Deck getBankDeck(){
        return bank;
    }

    /**
     * Metoda rozpoczynająca grę, tworząca talie graczy i dilera, tasująca karty i rozdająca graczom po 5 kart
     */
    public void initializeGame(){
        // inicjalizacja talii graczy
        players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Deck());
        }
        logger.info("players created");

        // inicjalizacja talii dilera
        bank = new Deck();
        bank.generateCardDeck();
        logger.info("generated the deck");
        bank.shuffle();
        logger.info("the deck was shuffled");

        // rozdanie kart
        int cardNum = 5;
        logger.info("dealing the cards...");
        for (int i = 0; i < numPlayers; i++) {
            for (int j = 0; j < cardNum; j++) {
                players.get(i).addCard(bank.pop());
            }
        }
        // wyświetlenie kart graczy
        logger.info("cards dealt");
        for (int i = 0; i < numPlayers; i++) {
            logger.info("player" + (i+1));
            players.get(i).print();
        }
    }

    /**
     * Metoda kończąca grę, sprawdzająca ręki graczy i wyświetlająca wyniki
     */
    public String endGame(){
        if (numPlayers == 0){return "no players";}
        hv = new HandVerificator(players);
        List<Tuple> results = hv.verifyHands();
        return checkForWinner(results);
    }

    /**
     * Metoda sprawdzająca który gracz bądź którzy gracze wygrali na bazie wyników dostarczonych przez HandVerificator
     * @param results to parametr reprezentujący wyniki graczy otrzymane z HandVerificator
     * @return zwracana jest lista graczy wygranych
     */
     String checkForWinner(List<Tuple> results) {
         StringBuilder sb = new StringBuilder();
         // sprawdzanie czy są dostępne jakiekolwiek wyniki
        if (results == null) {
            logger.info("No results!");
            sb.append("No results!");
            return sb.toString();
        }
        // inicjalizacja zmiennych do weryfikacji zwycięzcy
        pokerHand bestHand = pokerHand.HIGHCARD;
        int bestScore = 0;
        List<Integer> winners = new ArrayList<>();
        // logika sprawdzająca który gracz ma najlepszą rękę
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getPokerHand().getOrder() < bestHand.getOrder()) {
                bestHand = results.get(i).getPokerHand();
                bestScore = results.get(i).getScore();
                winners.clear();
                winners.add(i);
            } else if (results.get(i).getPokerHand() == bestHand) {
                if (results.get(i).getScore() > bestScore){
                    bestScore = results.get(i).getScore();
                    winners.clear();
                    winners.add(i);
                } else if(results.get(i).getScore() == bestScore){
                    winners.add(i);
                }
            }
        }

        // wyświetlanie wyników
        if (winners.size() == 1) {
            int w = winners.get(0) + 1;
            logger.info("The winner is: " + plString + w + "\nWinning hand: " + results.get(winners.get(0)).getPokerHand().toString());
            return "The winner is: " + plString + w + "!!!"+"\nWinning hand: " + results.get(winners.get(0)).getPokerHand().toString();
        }
        else {
            logger.info("It is a draw! The winners are:");
            sb.append("It is a draw! The winners are:\n");
            for (var winner : winners) {
                int w = winner+1;
                logger.info(plString+w +"!!!");
                sb.append(plString).append(w).append("\n");
            }
            logger.info("Winning hand: " + results.get(winners.get(0)).getPokerHand().toString());
            sb.append("Winning hand: ").append(results.get(winners.get(0)).getPokerHand().toString());
        }
        return sb.toString();
    }

    /**
     * Metoda wymieniająca karty gracza
     * @param playerIndex to parametr reprezentujący indeks gracza który wymienia karty
     * @param indexes to parametr reprezentujący indeksy kart w talii które gracz chce wymienić
     */
    public void changeCards(int playerIndex, Set<Integer> indexes){
        for (int index : indexes) {
            players.get(playerIndex).replaceCard(index, bank.pop());
        }
        players.get(playerIndex).sort();
    }

    /**
     * Metoda zwracająca talie gracza w formie Stringa
     * @param playerIndex to parametr reprezentujący indeks gracza którego talia ma zostać zwrócona
     * @return zwracana jest talia gracza w formie Stringa
     */
   public String getCardDeckInfo(int playerIndex){
        players.get(playerIndex).sort();
       return plString + (playerIndex + 1) + "\n" +
               players.get(playerIndex).toString();
   }

    /**
     * Metoda zwracająca odkryte talie kart wszystkich graczy w formie Stringa
     * @return zwracana są odkryte talie kart wszystkich graczy w formie Stringa
     */
   public String showWholeTable(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n------RESULTS------\nTable:\n");
        for (int i = 0; i < numPlayers; i++) {
           sb.append(getCardDeckInfo(i)).append("\n\n");
        }
        return sb.toString();
   }
}
