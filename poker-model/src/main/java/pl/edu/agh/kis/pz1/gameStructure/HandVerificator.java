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
    private final List<Deck> players;
    /**
     * Lista rąk jakie gracze uzyskali i ich score
     */
    private final List<Tuple> playerHands = new ArrayList<>();
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
            if (playerDeck.getSize() != 5) {
                throw new IllegalArgumentException("Not enough cards in player deck");
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
        for (var i : deck.entrySet()) {
            if (deck.get(i.getKey()) == 2) {
                pairs.add(i.getKey() + 1);
            } else if (deck.get(i.getKey()) == 3) {
                three = i.getKey() + 1;
            } else if (deck.get(i.getKey()) == 4) {
                four = i.getKey() + 1;
            } else if (deck.get(i.getKey()) == 1) {
                singles.add(i.getKey() + 1);
            }
        }
        // oblicznaie wyników do poszczególnych rąk w celu rozstrzygnięcia remisów
        return getTupleOfResults(four, three, pairs, singles);
    }

    /**
     * Metoda obliczająca wynik dla poszczególnych rąk gracza
     * @param four - parametr reprezentujący ilość czwórek
     * @param three - parametr reprezentujący ilość trójek
     * @param pairs - parametr reprezentujący ilość par
     * @param singles - parametr reprezentujący ilość kart o innej wartości niż pozostałe
     * @return zwraca rękę gracza i ostateczny wynik
     */
    private Tuple getTupleOfResults(int four, int three, List<Integer> pairs, List<Integer> singles) {
        int score;
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
        }
        else {
            return new Tuple(pokerHand.HIGHCARD, 0);
        }
    }

}

