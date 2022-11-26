package pl.edu.agh.kis.pz1.gameStructure;
import pl.edu.agh.kis.pz1.*;

import java.util.*;

public class HandVerificator {
    private List<Deck> players;
    private List<Tuple> playerHands = new ArrayList<>();

    public HandVerificator(List<Deck> _players) {
        players = _players;
        verifyIfEnoughCards();
    }

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

    public List<Tuple> verifyHands() {
        for (Deck playerDeck : players) {
            playerHands.add(analyzeDeck(playerDeck));
        }
        return playerHands;
    }


    Tuple analyzeDeck(Deck pd) {
        //Very important
        pd.sort();

        boolean flush = checkForSameSuit(pd);
        boolean straight = checkForStraight(pd);
        if (flush && straight) {
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
        } else {
            return checkSameCardsHands(pd);
        }
    }


    boolean checkForSameSuit(Deck pd) {
        Card first = pd.getCard(0);
        for (int i = 1; i < 5; i++) {
            if (first.getSuit() != pd.getCard(i).getSuit()) {
                return false;
            }
        }
        return true;
    }

    boolean checkForStraight(Deck pd) {
        for (int i = 0; i < 4; i++) {
            if (pd.getCard(i).getRank().getOrder() != (pd.getCard(i + 1).getRank().getOrder() - 1)) {
                return false;
            }
        }
        return true;
    }

    Tuple checkSameCardsHands(Deck pd) {
        Map<Integer, Integer> deck = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            deck.merge(pd.getCard(i).getRank().getOrder(), 1, Integer::sum);
        }
        int four = 0;
        int three = 0;
        List<Integer> pairs = new ArrayList<>();
        List<Integer> singles = new ArrayList<>();
        for (var i : deck.keySet()) {
            if (deck.get(i) == 2) {
                pairs.add(i+1);
            } else if (deck.get(i) == 3) {
                three = i+1;
            } else if (deck.get(i) == 4) {
                four = i+1;
            } else if(deck.get(i) == 1){
                singles.add(i+1);
            }
        }
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
            score = (singles.get(4) * 100000000) + (singles.get(3) * 1000000) + (singles.get(2) * 10000) + (singles.get(1) * 100) +(singles.get(0));
            return new Tuple(pokerHand.HIGHCARD, score);
        } else {
            System.out.println("Something went wrong");
            throw new RuntimeException();
        }

        /*
        int unique = uniqueRank.size();
        if (matches == 0){
            return pokerHand.HIGHCARD;
        }else if (matches == 1) {
            return pokerHand.ONEPAIR;
        }else if (matches == 2 && unique == 2){
            return pokerHand.TWOPAIR;
        }else if (matches == 2 && unique == 1){
            return pokerHand.THREE;
        }else if (matches == 3 && unique == 2){
            return pokerHand.FULLHOUSE;
        }else if (matches == 3 && unique == 1){
            return pokerHand.FOUR;
        }
        else {
            System.out.println("Something went wrong");
            throw new RuntimeException();
        }
        */
    }
    /*
    public void printAnalyzedDecks() {
        for (int i = 0; i < analyzedDecks.size(); i++) {
            System.out.println("Deck nr " + i);
            if (analyzedDecks.get(i) == null) {
                System.out.println("null\n");
            } else {
                for (var j : analyzedDecks.get(i).keySet()) {
                    System.out.println(j.toString() + ":" + analyzedDecks.get(i).get(j));
                }
                System.out.println();
            }
        }
    }

    public List<Integer> checkForWinner() {
        if (playerHands == null) {
            System.out.println("No results!");
            return null;
        }
        pokerHand bestHand = pokerHand.HIGHCARD;
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < playerHands.size(); i++) {
            if (playerHands.get(i).getOrder() < bestHand.getOrder()) {
                bestHand = playerHands.get(i);
                indices.clear();
                indices.add(i);
            } else if (playerHands.get(i) == bestHand) {
                indices.add(i);
            }
        }
        if (indices.size() == 1) {
            return indices;
        } else {
            return breakTie(indices, bestHand);
        }
    }

    List<Integer> breakTie(List<Integer> indices, pokerHand tieType) {
        for (int i = 0; i < indices.size() - 1; i++) {

        }
    }

    int compareTwoTie(Deck deck1, Deck deck2, Map<cardRank, Integer> analyzed1, Map<cardRank, Integer> analyzed2, pokerHand tieType){
        if (tieType == pokerHand.STRAIGHT || tieType == pokerHand.STRAIGHTFLUSH){
            return Integer.compare(deck1.getCard(-1).getRank().getOrder(), deck2.getCard(-1).getRank().getOrder());
        }else if(tieType == pokerHand.ROYALFLUSH){
            return 0;
        }else if(tieType == pokerHand.FLUSH || tieType == pokerHand.HIGHCARD){
            return strongestCardTie(deck1, deck2);
        }
    }

    int strongestCardTie(Deck deck1, Deck deck2){
        for (int i = 4; i > 0; i--) {
            int tmp = Integer.compare(deck1.getCard(i).getRank().getOrder(), deck2.getCard(i).getRank().getOrder());
            if (tmp != 0){
                return tmp;
            }
        }
        return 0;
    }

    int checkOnePairTie(Deck deck1, Deck deck2, Map<cardRank, Integer> analyzed1, Map<cardRank, Integer> analyzed2){
        for ( var a1 : analyzed1.keySet()) {

        }
    }

     */
}

