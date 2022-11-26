package pl.edu.agh.kis.pz1.gameStructure;
import pl.edu.agh.kis.pz1.*;

import java.util.*;
/**
 * Klasa reprezentująca sprawdzanie rąk
 */
public class HandVerificator {
    /**
     * Lista talii graczy
     */
    private List<Deck> players;
    /**
     * Lista rąk jakie gracze uzyskali i ich score
     */
    private List<Tuple> playerHands = new ArrayList<>();
    /**
     * Konstruktor klasy HandVerificator
     * @param _players to parametr reprezentujący listę talii graczy
     */
    public HandVerificator(List<Deck> _players) {
        players = _players;
        verifyIfEnoughCards();
    }
    /**
     * Metoda sprawdzająca czy każdy gracz ma wystarczającą ilość kart
     */
    void verifyIfEnoughCards() {
        for (Deck playerDeck : players) {
            try {
                playerDeck.getCard(4);
            } catch (Exception e) {
                System.out.println("Not enough cards in deck!");
                System.exit(1);
            }
        }
    }

    /**
     * Metoda przypisująca graczom zweryfikowane ręce i ich score
     * @return zwraca listę rąk graczy i ich score
     */
    public List<Tuple> verifyHands() {
        for (Deck playerDeck : players) {
            playerHands.add(analyzeDeck(playerDeck));
        }
        return playerHands;
    }

    /**
     * Metoda analizująca talię gracza i zwracająca jego rękę i score, który jest wynikiem sumy wartości kart
     * @param pd to parametr reprezentujący talię gracza
     * @return zwraca rękę gracza i jej score
     */
    Tuple analyzeDeck(Deck pd) {
        // sortowanie kart w talii gracza
        pd.sort();
        // sprawdzenie czy gracz ma kolor
        boolean flush = checkForSameSuit(pd);
        // sprawdzenie czy gracz ma strit
        boolean straight = checkForStraight(pd);

        if (flush && straight) { // sprawdzenie czy gracz ma pokera
            if (pd.getCard(4).getRank() == cardRank.ACE) {
                return new Tuple(pokerHand.ROYALFLUSH, 0);
            } else {
                return new Tuple(pokerHand.STRAIGHTFLUSH, pd.getCard(4).getRank().getOrder()+1);
            }
        } else if (flush) {
            int score = (pd.getCard(0).getRank().getOrder()+1) + ((pd.getCard(1).getRank().getOrder()+1) * 100)
                    + ((pd.getCard(2).getRank().getOrder()+1) * 10000) + ((pd.getCard(3).getRank().getOrder()+1) * 1000000)
                    + ((pd.getCard(4).getRank().getOrder()+1) * 100000000);
            return new Tuple(pokerHand.FLUSH, score);
        } else if (straight) {
            return new Tuple(pokerHand.STRAIGHT, pd.getCard(4).getRank().getOrder()+1);
            // po wykluczeniu pokera, koloru i strita sprawdzamy czy gracz ma trójkę, dwie pary, jedną parę lub nic
        } else {
            return checkSameCardsHands(pd);
        }
    }

    /**
     * Metoda sprawdzająca czy talia kart zawiera tylko karty tego samego koloru
     * @param pd to parametr reprezentujący talię gracza
     * @return zwraca true jeśli gracz ma kolor, false jeśli nie ma
     */
    boolean checkForSameSuit(Deck pd) {
        Card first = pd.getCard(0);
        for (int i = 1; i < 5; i++) {
            if (first.getSuit() != pd.getCard(i).getSuit()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metoda sprawdzająca czy talia kart zawiera strita przy założeniu że talia kart jest posortowana
     * @param pd to parametr reprezentujący talię gracza
     * @return zwraca true jeśli gracz ma strita, false jeśli nie ma
     */
    boolean checkForStraight(Deck pd) {
        for (int i = 0; i < 4; i++) {
            if (pd.getCard(i).getRank().getOrder() != (pd.getCard(i + 1).getRank().getOrder() - 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metoda sprawdzająca czy gracz ma trójkę, dwie pary, jedną parę bądź nic
     * @param pd to parametr reprezentujący talię gracza
     * @return zwraca rękę gracza i ostateczny wynik
     */
    Tuple checkSameCardsHands(Deck pd) {
        // zliczanie ilości kart o tej samej wartości
        Map<Integer, Integer> deck = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            deck.merge(pd.getCard(i).getRank().getOrder(), 1, Integer::sum);
        }
        // sprawdzanie czy gracz ma trójkę, dwie pary, jedną parę bądź nic
        int four = 0;
        int three = 0;
        List<Integer> pairs = new ArrayList<>();
        List<Integer> singles = new ArrayList<>();
        for (var i : deck.keySet()) {
            if (deck.get(i) == 2) {
                pairs.add(i + 1);
            } else if (deck.get(i) == 3) {
                three = i + 1;
            } else if (deck.get(i) == 4) {
                four = i + 1;
            } else if (deck.get(i) == 1) {
                singles.add(i + 1);
            }
        }
        // oblicznaie wyników do poszczególnych rąk w celu rozstrzygnięcia remisów
        int score = 0;
        if (four > 0) {
            score = (four * 100) + singles.get(0);
            return new Tuple(pokerHand.FOUR, score);
        } else if (three > 0 && pairs.size() == 1) {
            score = (three * 100) + pairs.get(0);
            return new Tuple(pokerHand.FULLHOUSE, score);
        } else if (three > 0 && pairs.size() == 0) {
            score = (three * 10000) + (singles.get(1) * 100) + (singles.get(0));
            return new Tuple(pokerHand.THREE, score);
        } else if (pairs.size() == 2) {
            score = (pairs.get(1) * 10000) + (pairs.get(0) * 100) + (singles.get(0));
            return new Tuple(pokerHand.TWOPAIR, score);
        } else if (pairs.size() == 1) {
            score = (pairs.get(0) * 1000000) + (singles.get(2) * 10000) + (singles.get(1) * 100) + (singles.get(0));
            return new Tuple(pokerHand.ONEPAIR, score);
        } else if (pairs.size() == 0) {
            score = (singles.get(4) * 100000000) + (singles.get(3) * 1000000) + (singles.get(2) * 10000) + (singles.get(1) * 100) + (singles.get(0));
            return new Tuple(pokerHand.HIGHCARD, score);
        } else {
            System.out.println("Something went wrong");
            throw new RuntimeException();
        }
    }
}

