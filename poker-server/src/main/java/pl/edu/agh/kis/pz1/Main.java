package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.gameStructure.*;
import java.util.*;

public class Main {
    /**
     * This is the main method yoooooooo
     * */
    public static void main(String[] args) {
        /*
	    Y y = new Y();
        A b = new B();
        b.m();


        //dog
        Dog d1 = new Dog("rex", "Ruff!");
        Dog d2 = new Dog("scruffy", "Wurf!");

        d1.print();
        d2.print();
        Dog d3 = d1;
        Dog d4 = new Dog("rex", "Ruff!");

        System.out.println(d1.equals(d3));
        System.out.println(d1.equals(d4));
        System.out.println(d1 == d4);
        */

        /*
        Deck d = new Deck();
        d.generateCardDeck();
        d.print();*/

        //num of players
        int n = 12;
        //num of cards to distribute
        int cardNum = 5;
        List<Deck> players = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            players.add(new Deck());
        }
        System.out.println("players created");
        /*
        Deck bank = new Deck();
        bank.generateCardDeck();
        System.out.println("generated the deck");
        //bank.sort();
        bank.print();
        //bank.shuffle();
        System.out.println("the deck was shuffled");
        System.out.println("dealing the cards...");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < cardNum; j++) {
                players.get(i).addCard(bank.pop());
            }
        }
        System.out.println("cards dealt");
        for (int i = 0; i < n; i++) {
            System.out.println("player" + (i+1));
            players.get(i).print();
        }
        */

        //two pair
        players.get(0).addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        players.get(0).addCard(new Card(cardRank.N3, cardSuit.CLUBS));
        players.get(0).addCard(new Card(cardRank.N4, cardSuit.HEARTS));
        players.get(0).addCard(new Card(cardRank.N2, cardSuit.HEARTS));
        players.get(0).addCard(new Card(cardRank.N4, cardSuit.CLUBS));
        //one pair
        players.get(1).addCard(new Card(cardRank.N6, cardSuit.CLUBS));
        players.get(1).addCard(new Card(cardRank.N7, cardSuit.CLUBS));
        players.get(1).addCard(new Card(cardRank.N8, cardSuit.CLUBS));
        players.get(1).addCard(new Card(cardRank.N9, cardSuit.CLUBS));
        players.get(1).addCard(new Card(cardRank.N6, cardSuit.HEARTS));
        //full
        players.get(2).addCard(new Card(cardRank.N10, cardSuit.HEARTS));
        players.get(2).addCard(new Card(cardRank.KING, cardSuit.HEARTS));
        players.get(2).addCard(new Card(cardRank.KING, cardSuit.CLUBS));
        players.get(2).addCard(new Card(cardRank.N10, cardSuit.CLUBS));
        players.get(2).addCard(new Card(cardRank.N10, cardSuit.SPADES));
        //4
        players.get(3).addCard(new Card(cardRank.ACE, cardSuit.SPADES));
        players.get(3).addCard(new Card(cardRank.N8, cardSuit.SPADES));
        players.get(3).addCard(new Card(cardRank.ACE, cardSuit.DIAMONDS));
        players.get(3).addCard(new Card(cardRank.ACE, cardSuit.CLUBS));
        players.get(3).addCard(new Card(cardRank.ACE, cardSuit.HEARTS));
        //3
        players.get(4).addCard(new Card(cardRank.QUEEN, cardSuit.HEARTS));
        players.get(4).addCard(new Card(cardRank.QUEEN, cardSuit.SPADES));
        players.get(4).addCard(new Card(cardRank.QUEEN, cardSuit.DIAMONDS));
        players.get(4).addCard(new Card(cardRank.N7, cardSuit.HEARTS));
        players.get(4).addCard(new Card(cardRank.JACK, cardSuit.HEARTS));
        //nic
        players.get(5).addCard(new Card(cardRank.JACK, cardSuit.HEARTS));
        players.get(5).addCard(new Card(cardRank.QUEEN, cardSuit.HEARTS));
        players.get(5).addCard(new Card(cardRank.KING, cardSuit.HEARTS));
        players.get(5).addCard(new Card(cardRank.ACE, cardSuit.HEARTS));
        players.get(5).addCard(new Card(cardRank.N10, cardSuit.HEARTS));

        players.get(6).addCard(new Card(cardRank.JACK, cardSuit.HEARTS));
        players.get(6).addCard(new Card(cardRank.QUEEN, cardSuit.HEARTS));
        players.get(6).addCard(new Card(cardRank.KING, cardSuit.HEARTS));
        players.get(6).addCard(new Card(cardRank.ACE, cardSuit.HEARTS));
        players.get(6).addCard(new Card(cardRank.N4, cardSuit.HEARTS));

        players.get(7).addCard(new Card(cardRank.JACK, cardSuit.HEARTS));
        players.get(7).addCard(new Card(cardRank.QUEEN, cardSuit.HEARTS));
        players.get(7).addCard(new Card(cardRank.KING, cardSuit.HEARTS));
        players.get(7).addCard(new Card(cardRank.ACE, cardSuit.HEARTS));
        players.get(7).addCard(new Card(cardRank.N5, cardSuit.HEARTS));

        players.get(8).addCard(new Card(cardRank.JACK, cardSuit.HEARTS));
        players.get(8).addCard(new Card(cardRank.QUEEN, cardSuit.HEARTS));
        players.get(8).addCard(new Card(cardRank.KING, cardSuit.HEARTS));
        players.get(8).addCard(new Card(cardRank.ACE, cardSuit.HEARTS));
        players.get(8).addCard(new Card(cardRank.N5, cardSuit.DIAMONDS));

        players.get(9).addCard(new Card(cardRank.JACK, cardSuit.HEARTS));
        players.get(9).addCard(new Card(cardRank.QUEEN, cardSuit.HEARTS));
        players.get(9).addCard(new Card(cardRank.KING, cardSuit.HEARTS));
        players.get(9).addCard(new Card(cardRank.ACE, cardSuit.HEARTS));
        players.get(9).addCard(new Card(cardRank.N10, cardSuit.DIAMONDS));

        players.get(10).addCard(new Card(cardRank.JACK, cardSuit.HEARTS));
        players.get(10).addCard(new Card(cardRank.QUEEN, cardSuit.HEARTS));
        players.get(10).addCard(new Card(cardRank.KING, cardSuit.HEARTS));
        players.get(10).addCard(new Card(cardRank.N9, cardSuit.HEARTS));
        players.get(10).addCard(new Card(cardRank.N10, cardSuit.HEARTS));

        players.get(11).addCard(new Card(cardRank.JACK, cardSuit.HEARTS));
        players.get(11).addCard(new Card(cardRank.QUEEN, cardSuit.HEARTS));
        players.get(11).addCard(new Card(cardRank.KING, cardSuit.HEARTS));
        players.get(11).addCard(new Card(cardRank.ACE, cardSuit.HEARTS));
        players.get(11).addCard(new Card(cardRank.N10, cardSuit.HEARTS));

        HandVerificator hv = new HandVerificator(players);
        List<Tuple> results = hv.verifyHands();
        for (Tuple result : results){
            System.out.println(result.getPokerHand().toString() + ": " + result.getScore());
        }



        //hv.printAnalyzedDecks();
        GameManager gm2 = new GameManager(4);
        gm2.initializeGame();
        gm2.endGame();

        Card card = new Card(cardRank.N2, cardSuit.DIAMONDS);
        card.print();
    }

    void InitializeGame(int plNum){
        List<Deck> players = new ArrayList<Deck>();
        for (int i = 0; i < plNum; i++) {
            players.add(new Deck());
        }
        System.out.println("players created");

        int cardNum = 5;
        Deck bank = new Deck();
        bank.generateCardDeck();
        System.out.println("generated the deck");

        bank.shuffle();
        System.out.println("the deck was shuffled");

        System.out.println("dealing the cards...");
        for (int i = 0; i < plNum; i++) {
            for (int j = 0; j < cardNum; j++) {
                players.get(i).addCard(bank.pop());
            }
        }
        System.out.println("cards dealt");
        for (int i = 0; i < plNum; i++) {
            System.out.println("player" + (i+1));
            players.get(i).print();
        }
    }


    public List<Integer> checkForWinner(List<Tuple> results) {
        if (results == null) {
            System.out.println("No results!");
            return null;
        }
        pokerHand bestHand = pokerHand.HIGHCARD;
        int bestScore = 0;
        List<Integer> winners = new ArrayList<>();
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
        if (winners.size() == 1) {
            System.out.println("The winner is: " + "player #" + winners.get(0) + 1 + "\nWinning hand: " + results.get(winners.get(0)).getPokerHand().toString());
            return winners;
        }
        else {
            System.out.println("It is a draw! The winners are:\n");
            for (var winner : winners) {
                System.out.println("player #"+winner+1);
            }
            System.out.println("Winning hand: " + results.get(winners.get(0)).getPokerHand().toString());
            return winners;
        }
    }

}


/*
class A{
    public void m(){
        System.out.println("A");
    }
}

class B extends A{
    public void m(){
        System.out.println("B");
    }
}
*/
