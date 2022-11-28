package pl.edu.agh.kis.pz1.gameStructure;
import pl.edu.agh.kis.pz1.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    /**
     * Konstruktor klasy GameManager
     * @param _numPlayers to parametr reprezentujący liczbę graczy biorących udział w grze
     */
    public GameManager(int _numPlayers){
        numPlayers = _numPlayers;
    }

    /**
     * Metoda rozpoczynająca grę, tworząca talie graczy i dilera, tasująca karty i rozdająca graczom po 5 kart
     */
    public void initializeGame(){
        // inicjalizacja talii graczy
        players = new ArrayList<Deck>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Deck());
        }
        System.out.println("players created");

        // inicjalizacja talii dilera
        bank = new Deck();
        bank.generateCardDeck();
        System.out.println("generated the deck");
        bank.shuffle();
        System.out.println("the deck was shuffled");

        // rozdanie kart
        int cardNum = 5;
        System.out.println("dealing the cards...");
        for (int i = 0; i < numPlayers; i++) {
            for (int j = 0; j < cardNum; j++) {
                players.get(i).addCard(bank.pop());
            }
        }
        // wyświetlenie kart graczy
        System.out.println("cards dealt");
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("player" + (i+1));
            players.get(i).print();
        }
    }

    /**
     * Metoda kończąca grę, sprawdzająca ręki graczy i wyświetlająca wyniki
     */
    public void endGame(){
        if (players == null){return;}
        hv = new HandVerificator(players);
        List<Tuple> results = hv.verifyHands();
        checkForWinner(results);
    }

    /**
     * Metoda sprawdzająca który gracz bądź którzy gracze wygrali na bazie wyników dostarczonych przez HandVerificator
     * @param results to parametr reprezentujący wyniki graczy otrzymane z HandVerificator
     * @return zwracana jest lista graczy wygranych
     */
     List<Integer> checkForWinner(List<Tuple> results) {
         // sprawdzanie czy są dostępne jakiekolwiek wyniki
        if (results == null) {
            System.out.println("No results!");
            return null;
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
            System.out.println("The winner is: " + "player #" + w + "\nWinning hand: " + results.get(winners.get(0)).getPokerHand().toString());
        }
        else {
            System.out.println("It is a draw! The winners are:");
            for (var winner : winners) {
                int w = winner+1;
                System.out.println("player #"+w);
            }
            System.out.println("Winning hand: " + results.get(winners.get(0)).getPokerHand().toString());
        }
        return winners;
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

   public String getCardDeckInfo(int playerIndex){
       return "player #" + (playerIndex + 1) + "\n" +
               players.get(playerIndex).toString();
   }
}
