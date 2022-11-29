package pl.edu.agh.kis.pz1.gameStructure;



import org.junit.jupiter.api.Assertions;
import pl.edu.agh.kis.pz1.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HandVerificatorTest {

    @org.junit.jupiter.api.Test
    void verifyIfEnoughCardsThrowsException() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        List<Deck> decks = List.of(d);
        Assertions.assertThrows(IllegalArgumentException.class, () -> new HandVerificator(decks));
    }
    @org.junit.jupiter.api.Test
    void verifyIfEnoughCardsNoException(){
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N3, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N4, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N5, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N6, cardSuit.CLUBS));
        List<Deck> decks = List.of(d);
        Assertions.assertDoesNotThrow(() -> {
            new HandVerificator(decks);
        });
    }

    // TEST HANDS

    @org.junit.jupiter.api.Test
    void verifyHands1PlayerStraightShouldEqualStraight() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N3, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N4, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N5, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N6, cardSuit.CLUBS));
        List<Deck> decks = List.of(d);
        HandVerificator hv = new HandVerificator(decks);
        assertEquals(pokerHand.STRAIGHT, hv.verifyHands().get(0).getPokerHand());
    }

    @org.junit.jupiter.api.Test
    void analyzeDeckRoyalFlush() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.ACE, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.KING, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.JACK, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N10, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(pokerHand.ROYALFLUSH, hv.analyzeDeck(d).getPokerHand());
    }

    @org.junit.jupiter.api.Test
    void analyzeDeckStraightFlush() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N9, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N8, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.JACK, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N10, cardSuit.HEARTS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(pokerHand.STRAIGHTFLUSH, hv.analyzeDeck(d).getPokerHand());
    }

    @org.junit.jupiter.api.Test
    void analyzeDeckStraight() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N9, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N8, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.JACK, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N10, cardSuit.HEARTS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(pokerHand.STRAIGHT, hv.analyzeDeck(d).getPokerHand());
    }

    @org.junit.jupiter.api.Test
    void analyzeDeckFlush() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N2, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N8, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N5, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N10, cardSuit.HEARTS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(pokerHand.FLUSH, hv.analyzeDeck(d).getPokerHand());
    }

    @org.junit.jupiter.api.Test
    void checkForSameSuit1Not() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N5, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N4, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N3, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N6, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertFalse(hv.checkForSameSuit(d));
    }

    @org.junit.jupiter.api.Test
    void checkForSameSuit2() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N4, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N10, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N6, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertTrue(hv.checkForSameSuit(d));
    }

    @org.junit.jupiter.api.Test
    void checkForSameSuit3() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N9, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N2, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N4, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N10, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N6, cardSuit.HEARTS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertTrue(hv.checkForSameSuit(d));
    }

    @org.junit.jupiter.api.Test
    void checkForStraight1() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N5, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N4, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N3, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N6, cardSuit.CLUBS));
        d.sort();
        HandVerificator hv = new HandVerificator(List.of(d));
        assertTrue(hv.checkForStraight(d));
    }

    @org.junit.jupiter.api.Test
    void checkForStraight2() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.ACE, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.KING, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N10, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.JACK, cardSuit.CLUBS));
        d.sort();
        HandVerificator hv = new HandVerificator(List.of(d));
        assertTrue(hv.checkForStraight(d));
    }

    @org.junit.jupiter.api.Test
    void checkForStraight3Not() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.ACE, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.KING, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N10, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.JACK, cardSuit.CLUBS));
        d.sort();
        HandVerificator hv = new HandVerificator(List.of(d));
        assertFalse(hv.checkForStraight(d));
    }
    @org.junit.jupiter.api.Test
    void checkSameCardsHandsNothing1() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N10, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N4, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.KING, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N6, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(pokerHand.HIGHCARD, hv.checkSameCardsHands(d).getPokerHand());
    }
    @org.junit.jupiter.api.Test
    void checkSameCardsHandsNothing2() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.JACK, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N10, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.ACE, cardSuit.SPADES));
        d.addCard(new Card(cardRank.KING, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N6, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(pokerHand.HIGHCARD, hv.checkSameCardsHands(d).getPokerHand());
    }
    @org.junit.jupiter.api.Test
    void checkSameCardsHandsPair1() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.JACK, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N10, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.ACE, cardSuit.SPADES));
        d.addCard(new Card(cardRank.JACK, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N6, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(pokerHand.ONEPAIR, hv.checkSameCardsHands(d).getPokerHand());
    }
    @org.junit.jupiter.api.Test
    void checkSameCardsHandsPair2() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.JACK, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N6, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.ACE, cardSuit.SPADES));
        d.addCard(new Card(cardRank.KING, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N6, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(pokerHand.ONEPAIR, hv.checkSameCardsHands(d).getPokerHand());
    }
    @org.junit.jupiter.api.Test
    void checkSameCardsHandsTwoPair1() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.JACK, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N6, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.ACE, cardSuit.SPADES));
        d.addCard(new Card(cardRank.ACE, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N6, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(pokerHand.TWOPAIR, hv.checkSameCardsHands(d).getPokerHand());
    }
    @org.junit.jupiter.api.Test
    void checkSameCardsHandsTwoPair2() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.QUEEN, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N6, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.ACE, cardSuit.SPADES));
        d.addCard(new Card(cardRank.ACE, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.DIAMONDS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(pokerHand.TWOPAIR, hv.checkSameCardsHands(d).getPokerHand());
    }
    @org.junit.jupiter.api.Test
    void checkSameCardsHandsThree1() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.ACE, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N6, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.ACE, cardSuit.SPADES));
        d.addCard(new Card(cardRank.ACE, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.DIAMONDS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(pokerHand.THREE, hv.checkSameCardsHands(d).getPokerHand());
    }
    @org.junit.jupiter.api.Test
    void checkSameCardsHandsThree2() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.ACE, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.KING, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.KING, cardSuit.SPADES));
        d.addCard(new Card(cardRank.KING, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.DIAMONDS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(pokerHand.THREE, hv.checkSameCardsHands(d).getPokerHand());
    }
    @org.junit.jupiter.api.Test
    void checkSameCardsHandsFour1() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N4, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N4, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N4, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.KING, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N4, cardSuit.SPADES));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(pokerHand.FOUR, hv.checkSameCardsHands(d).getPokerHand());
    }
    @org.junit.jupiter.api.Test
    void checkSameCardsHandsFour2() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.JACK, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N4, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.JACK, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.JACK, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.JACK, cardSuit.SPADES));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(pokerHand.FOUR, hv.checkSameCardsHands(d).getPokerHand());
    }
    @org.junit.jupiter.api.Test
    void checkSameCardsHandsFullHouse1() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.JACK, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N4, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N4, cardSuit.SPADES));
        d.addCard(new Card(cardRank.JACK, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.JACK, cardSuit.SPADES));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(pokerHand.FULLHOUSE, hv.checkSameCardsHands(d).getPokerHand());
    }
    @org.junit.jupiter.api.Test
    void checkSameCardsHandsFullHouse2() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.KING, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.KING, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N2, cardSuit.SPADES));
        d.addCard(new Card(cardRank.N2, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N2, cardSuit.DIAMONDS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(pokerHand.FULLHOUSE, hv.checkSameCardsHands(d).getPokerHand());
    }


    // TEST SCORE

    @org.junit.jupiter.api.Test
    void checkPointsHandsHighCardShouldEqual1109040301() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N10, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N4, cardSuit.SPADES));
        d.addCard(new Card(cardRank.N5, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.DIAMONDS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(1109040301, hv.checkSameCardsHands(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsHighCardShouldEqual706040301() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N7, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N4, cardSuit.SPADES));
        d.addCard(new Card(cardRank.N5, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N8, cardSuit.DIAMONDS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(706040301, hv.checkSameCardsHands(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsPairShouldEqual1090403() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.N10, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N4, cardSuit.SPADES));
        d.addCard(new Card(cardRank.N5, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N2, cardSuit.DIAMONDS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(1090403, hv.checkSameCardsHands(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsPairShouldEqual12111001() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.KING, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.JACK, cardSuit.SPADES));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.KING, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(12111001, hv.checkSameCardsHands(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsTwoPairShouldEqual121104() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N5, cardSuit.CLUBS));
        d.addCard(new Card(cardRank.KING, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.SPADES));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.KING, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(121104, hv.checkSameCardsHands(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsTwoPairShouldEqual50212() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N6, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.KING, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N3, cardSuit.SPADES));
        d.addCard(new Card(cardRank.N3, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N6, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(50212, hv.checkSameCardsHands(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsThreeShouldEqual50201() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N6, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N6, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N3, cardSuit.SPADES));
        d.addCard(new Card(cardRank.N2, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N6, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(50201, hv.checkSameCardsHands(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsThreeShouldEqual131205() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N6, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.ACE, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.ACE, cardSuit.SPADES));
        d.addCard(new Card(cardRank.ACE, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.KING, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(131205, hv.checkSameCardsHands(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsFourShouldEqual1307() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N8, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.ACE, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.ACE, cardSuit.SPADES));
        d.addCard(new Card(cardRank.ACE, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.ACE, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(1307, hv.checkSameCardsHands(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsFourShouldEqual813() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N9, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N9, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N9, cardSuit.SPADES));
        d.addCard(new Card(cardRank.ACE, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N9, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(813, hv.checkSameCardsHands(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsFullHouseShouldEqual807() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N9, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N8, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N9, cardSuit.SPADES));
        d.addCard(new Card(cardRank.N8, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N9, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(807, hv.checkSameCardsHands(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsFullHouseShouldEqual1113() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.ACE, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.ACE, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.SPADES));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.CLUBS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(1113, hv.checkSameCardsHands(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsFlushShouldEqual1312110402() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N5, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.ACE, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.KING, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N3, cardSuit.HEARTS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(1312110402, hv.analyzeDeck(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsFlushShouldEqual706040201() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N5, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N8, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N7, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N2, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N3, cardSuit.HEARTS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(706040201, hv.analyzeDeck(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsStraightShouldEqual5() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N2, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N6, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N5, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N4, cardSuit.SPADES));
        d.addCard(new Card(cardRank.N3, cardSuit.HEARTS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(5, hv.analyzeDeck(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsStraightShouldEqual10() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N9, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N7, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.JACK, cardSuit.HEARTS));
        d.addCard(new Card(cardRank.N10, cardSuit.SPADES));
        d.addCard(new Card(cardRank.N8, cardSuit.HEARTS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(10, hv.analyzeDeck(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsStraightFlushShouldEqual10() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N9, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N7, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.JACK, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N10, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N8, cardSuit.DIAMONDS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(10, hv.analyzeDeck(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsStraightFlushShouldEqual7() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.N8, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N7, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N6, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N4, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N5, cardSuit.DIAMONDS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(7, hv.analyzeDeck(d).getScore());
    }

    @org.junit.jupiter.api.Test
    void checkPointsHandsRoyalFlushShouldEqual0() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.ACE, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.KING, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.QUEEN, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.JACK, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.N10, cardSuit.DIAMONDS));
        HandVerificator hv = new HandVerificator(List.of(d));
        assertEquals(0, hv.analyzeDeck(d).getScore());
    }

    // RUNTIME EXCEPTION
    @org.junit.jupiter.api.Test
    void runtimeExceptionTest() {
        Deck d = new Deck();
        d.addCard(new Card(cardRank.JACK, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.JACK, cardSuit.SPADES));
        d.addCard(new Card(cardRank.JACK, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.JACK, cardSuit.DIAMONDS));
        d.addCard(new Card(cardRank.JACK, cardSuit.DIAMONDS));

        HandVerificator hv = new HandVerificator(List.of(d));
        assertThrows(RuntimeException.class, () -> hv.analyzeDeck(d));
    }
}