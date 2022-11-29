package pl.edu.agh.kis.pz1;


import static org.junit.jupiter.api.Assertions.*;

class CardRankTest {

    @org.junit.jupiter.api.Test
    void testGetOrderOfKing() {
        assertEquals(11, cardRank.KING.getOrder());
    }

    @org.junit.jupiter.api.Test
    void testToStringQueen() {
        assertEquals("queen", cardRank.QUEEN.toString());
    }
}