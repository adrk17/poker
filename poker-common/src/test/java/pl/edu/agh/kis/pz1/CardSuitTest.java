package pl.edu.agh.kis.pz1;



import static org.junit.jupiter.api.Assertions.*;

class CardSuitTest {

    @org.junit.jupiter.api.Test
    void getOrderOfDiamonds() {
        assertEquals(1, cardSuit.DIAMONDS.getOrder());
    }

    @org.junit.jupiter.api.Test
    void testToStringClubs() {
        assertEquals("clubs", cardSuit.CLUBS.toString());
    }
}