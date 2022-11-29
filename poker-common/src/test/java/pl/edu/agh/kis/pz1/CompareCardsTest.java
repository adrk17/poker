package pl.edu.agh.kis.pz1;



import static org.junit.jupiter.api.Assertions.*;

class CompareCardsTest {

    @org.junit.jupiter.api.Test
    void compareTwoCardsKingAceShouldEqualAceStrongest() {
        Card c1 = new Card(cardRank.KING, cardSuit.CLUBS);
        Card c2 = new Card(cardRank.ACE, cardSuit.CLUBS);
        CompareCards cc = new CompareCards();
        assertEquals(-1, cc.compare(c1, c2));
    }
    @org.junit.jupiter.api.Test
    void compareTwoCardsAceDiamondsAceClubsDiamondsStrongest(){
        Card c1 = new Card(cardRank.ACE, cardSuit.DIAMONDS);
        Card c2 = new Card(cardRank.ACE, cardSuit.CLUBS);
        CompareCards cc = new CompareCards();
        assertEquals(1, cc.compare(c1, c2));
    }
}