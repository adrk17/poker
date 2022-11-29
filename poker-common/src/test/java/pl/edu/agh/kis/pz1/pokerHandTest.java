package pl.edu.agh.kis.pz1;

import static org.junit.jupiter.api.Assertions.*;

class PokerHandTest {

    @org.junit.jupiter.api.Test
    void getOrderTestStaightShouldEqual5() {
        assertEquals(5, pokerHand.STRAIGHT.getOrder());
    }

    @org.junit.jupiter.api.Test
    void testToStringNameOfTwoPair() {
        assertEquals("two pair", pokerHand.TWOPAIR.toString());
    }
}