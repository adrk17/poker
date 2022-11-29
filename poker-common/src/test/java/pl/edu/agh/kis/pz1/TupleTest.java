package pl.edu.agh.kis.pz1;



import static org.junit.jupiter.api.Assertions.*;

class TupleTest {

    @org.junit.jupiter.api.Test
    void getPokerHandFlushTest() {
        Tuple t = new Tuple(pokerHand.FLUSH, 1);
        assertEquals(pokerHand.FLUSH, t.getPokerHand());
    }

    @org.junit.jupiter.api.Test
    void getScoreTestScore1() {
        Tuple t = new Tuple(pokerHand.FLUSH, 1);
        assertEquals(1, t.getScore());
    }
}