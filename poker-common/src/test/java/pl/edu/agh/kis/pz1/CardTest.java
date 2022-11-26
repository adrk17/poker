package pl.edu.agh.kis.pz1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @org.junit.jupiter.api.Test
    void testCardFromCardACE_CLUBS() {
        Card c1 = new Card(cardRank.ACE, cardSuit.CLUBS);
        Card c2 = new Card(c1);
        assertEquals(c1, c2);
    }
    @org.junit.jupiter.api.Test
    void testPrintCard2Diamonds() {
        Card c = new Card(cardRank.N2, cardSuit.DIAMONDS);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        c.print();
        String shouldEqual = "2 - diamonds\r\n";

        assertEquals(shouldEqual, outContent.toString());
    }

    @org.junit.jupiter.api.Test
    void testEqualsMethod() {
        Card c1 = new Card(cardRank.N3, cardSuit.CLUBS);
        Card c2 = new Card(cardRank.N3, cardSuit.CLUBS);
        assertTrue(c1.equals(c2));
    }

    @org.junit.jupiter.api.Test
    void testHashCode() {
        Card c1 = new Card(cardRank.KING, cardSuit.HEARTS);
        Card c2 = new Card(cardRank.KING, cardSuit.HEARTS);
        assertEquals(c1.hashCode(), c2.hashCode());
    }
}