package pl.edu.agh.kis.pz1;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    @org.junit.jupiter.api.Test
    void testDeckFromAnotherDeck(){
        Deck d1 = new Deck();
        d1.addCard(new Card(cardRank.ACE, cardSuit.CLUBS));
        Deck d2 = new Deck(d1);
        assertEquals(d1.getCard(0), d2.getCard(0));
    }

    @org.junit.jupiter.api.Test
    void addCardClubsThree() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N3, cardSuit.CLUBS));
        assertEquals(d.getCard(0), new Card(cardRank.N3, cardSuit.CLUBS));
    }

    @org.junit.jupiter.api.Test
    void getCardIndex0ClubsTwo() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N3, cardSuit.CLUBS));
        assertEquals(d.getCard(0), new Card(cardRank.N2, cardSuit.CLUBS));
    }

    @org.junit.jupiter.api.Test
    void sortTwoThreeKingAce() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.KING, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.ACE, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N3, cardSuit.CLUBS));
        d.sort();
        assertEquals(d.getCard(0), new Card(cardRank.N2, cardSuit.CLUBS));
        assertEquals(d.getCard(1), new Card(cardRank.N3, cardSuit.CLUBS));
        assertEquals(d.getCard(2), new Card(cardRank.KING, cardSuit.CLUBS));
        assertEquals(d.getCard(3), new Card(cardRank.ACE, cardSuit.CLUBS));
    }

    @org.junit.jupiter.api.Test
    void generateCardDeckFullDeck() {
        Deck d = new Deck();
        d.generateCardDeck();
        assertEquals(d.getCard(0), new Card(cardRank.N2, cardSuit.CLUBS));
        assertEquals(d.getCard(1), new Card(cardRank.N3, cardSuit.CLUBS));
        assertEquals(d.getCard(2), new Card(cardRank.N4, cardSuit.CLUBS));
        assertEquals(d.getCard(3), new Card(cardRank.N5, cardSuit.CLUBS));
        assertEquals(d.getCard(4), new Card(cardRank.N6, cardSuit.CLUBS));
        assertEquals(d.getCard(5), new Card(cardRank.N7, cardSuit.CLUBS));
        assertEquals(d.getCard(6), new Card(cardRank.N8, cardSuit.CLUBS));
        assertEquals(d.getCard(7), new Card(cardRank.N9, cardSuit.CLUBS));
        assertEquals(d.getCard(8), new Card(cardRank.N10, cardSuit.CLUBS));
        assertEquals(d.getCard(9), new Card(cardRank.JACK, cardSuit.CLUBS));
        assertEquals(d.getCard(10), new Card(cardRank.QUEEN, cardSuit.CLUBS));
        assertEquals(d.getCard(11), new Card(cardRank.KING, cardSuit.CLUBS));
        assertEquals(d.getCard(12), new Card(cardRank.ACE, cardSuit.CLUBS));
        assertEquals(d.getCard(13), new Card(cardRank.N2, cardSuit.DIAMONDS));
        assertEquals(d.getCard(26), new Card(cardRank.N2, cardSuit.HEARTS));
        assertEquals(d.getCard(39), new Card(cardRank.N2, cardSuit.SPADES));
        assertEquals(d.getCard(51), new Card(cardRank.ACE, cardSuit.SPADES));
    }


    @org.junit.jupiter.api.Test
    void testPrintCardFromDeck() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        d.print();
        String shouldEqual = "clubs: 2 | \n\r\n";
        assertEquals(shouldEqual, outContent.toString());
    }

    @org.junit.jupiter.api.Test
    void popDiamonds5() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N5, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        assertEquals(d.pop(), new Card(cardRank.N5, cardSuit.DIAMONDS));
        assertEquals(d.getCard(0), new Card(cardRank.N2, cardSuit.CLUBS));
    }


    @org.junit.jupiter.api.Test
    void replaceCard2ClubsForAceHearts() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N3, cardSuit.CLUBS));
        d.replaceCard(0, new Card(cardRank.ACE, cardSuit.HEARTS));
        assertEquals(d.getCard(0), new Card(cardRank.ACE, cardSuit.HEARTS));
    }

    @org.junit.jupiter.api.Test
    void testToStringAceHearts2Clubs() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.ACE, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        assertEquals(d.toString(), "| hearts : ace | clubs : 2 | ");
    }
}