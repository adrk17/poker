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
    void testEqualsMethod() {
        Card c1 = new Card(cardRank.N3, cardSuit.CLUBS);
        Card c2 = new Card(cardRank.N3, cardSuit.CLUBS);
        assertEquals(c1, c2);
    }

    @org.junit.jupiter.api.Test
    void testHashCode() {
        Card c1 = new Card(cardRank.KING, cardSuit.HEARTS);
        Card c2 = new Card(cardRank.KING, cardSuit.HEARTS);
        assertEquals(c1.hashCode(), c2.hashCode());
    }
}