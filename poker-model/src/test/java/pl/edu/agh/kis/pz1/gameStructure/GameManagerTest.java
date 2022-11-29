package pl.edu.agh.kis.pz1.gameStructure;


import pl.edu.agh.kis.pz1.*;

import java.util.HashSet;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {
    @org.junit.jupiter.api.Test
    void testConstructor2PlayersEqual2() {
        GameManager gm = new GameManager(2);
        assertEquals(2, gm.getPlayerNum());
    }

    @org.junit.jupiter.api.Test
    void testConstructorFromExistingPlayers(){
        List<Deck> decks = List.of(new Deck(), new Deck());
        decks.get(0).addCard(new Card(cardRank.ACE, cardSuit.CLUBS));
        decks.get(0).addCard(new Card(cardRank.KING, cardSuit.CLUBS));
        decks.get(0).addCard(new Card(cardRank.N2, cardSuit.CLUBS));
        decks.get(1).addCard(new Card(cardRank.ACE, cardSuit.CLUBS));
        decks.get(1).addCard(new Card(cardRank.KING, cardSuit.CLUBS));
        GameManager gm = new GameManager(decks);
        assertEquals(2, gm.getPlayerNum());
        assertEquals(decks.get(0).getSize(), gm.getPlayersDeck(0).getSize());
        assertEquals(decks.get(1).getSize(), gm.getPlayersDeck(1).getSize());
        assertEquals(decks.get(0).getCard(0), gm.getPlayersDeck(0).getCard(0));
    }

    @org.junit.jupiter.api.Test
    void initializeGameTestIfAllPlayersMade() {
        GameManager gm = new GameManager(2);
        gm.initializeGame();
        assertEquals(2, gm.getPlayerNum());
    }

    @org.junit.jupiter.api.Test
    void initializeGameTestIfAllPlayersHave5Cards(){
        GameManager gm = new GameManager(2);
        gm.initializeGame();
        assertEquals(5, gm.getPlayersDeck(0).getSize());
    }
    @org.junit.jupiter.api.Test
    void initializeGameTestIfBankHas42CardsWith2Players(){
        GameManager gm = new GameManager(2);
        gm.initializeGame();
        assertEquals(42, gm.getBankDeck().getSize());
    }
    @org.junit.jupiter.api.Test
    void endGameTestIfNoPlayers() {
        GameManager gm = new GameManager(0);
        gm.initializeGame();
        assertEquals("no players", gm.endGame());
    }

    @org.junit.jupiter.api.Test
    void checkForWinnerWithResultsRoyalPokerHighCard() {
        GameManager gm = new GameManager(2);
        Tuple t1 = new Tuple(pokerHand.ROYALFLUSH, 100);
        Tuple t2 = new Tuple(pokerHand.HIGHCARD, 10000000);
        List<Tuple> l = List.of(t1, t2);
        String res = gm.checkForWinner(l);
        assertEquals("The winner is: player #1!!!\nWinning hand: royal flush", res);
    }
    @org.junit.jupiter.api.Test
    void checkForWinnerWithSamePokerHandButDifferentScore(){
        GameManager gm = new GameManager(2);
        Tuple t1 = new Tuple(pokerHand.THREE, 1111);
        Tuple t2 = new Tuple(pokerHand.THREE, 1112);
        List<Tuple> l = List.of(t1, t2);
        String res = gm.checkForWinner(l);
        assertEquals("The winner is: player #2!!!\nWinning hand: three of a kind", res);
    }

    @org.junit.jupiter.api.Test
    void checkForWinnerDraw(){
        GameManager gm = new GameManager(2);
        Tuple t1 = new Tuple(pokerHand.THREE, 1111);
        Tuple t2 = new Tuple(pokerHand.THREE, 1111);
        List<Tuple> l = List.of(t1, t2);
        String res = gm.checkForWinner(l);
        assertEquals("It is a draw! The winners are:\nplayer #1\nplayer #2\nWinning hand: three of a kind", res);
    }

    @org.junit.jupiter.api.Test
    void checkForWinnerNoResults(){
        GameManager gm = new GameManager(2);
        List<Tuple> l = null;
        String res = gm.checkForWinner(l);
        assertEquals("No results!", res);
    }

    @org.junit.jupiter.api.Test
    void changeCardsAtIndex0And1Player0(){
        GameManager gm = new GameManager(1);
        gm.initializeGame();
        Card c1 = gm.getPlayersDeck(0).getCard(0);
        Card c2 = gm.getPlayersDeck(0).getCard(1);
        gm.changeCards(0, new HashSet<>(List.of(0, 1)));
        assertNotEquals(c1, gm.getPlayersDeck(0).getCard(0));
        assertNotEquals(c2, gm.getPlayersDeck(0).getCard(1));
    }

    @org.junit.jupiter.api.Test
    void changeCardsAtIndex0Player0(){
        GameManager gm = new GameManager(1);
        gm.initializeGame();
        Card c1 = gm.getPlayersDeck(0).getCard(0);
        gm.changeCards(0, new HashSet<>(List.of(0)));
        assertNotEquals(c1, gm.getPlayersDeck(0).getCard(0));
    }

    @org.junit.jupiter.api.Test
    void changeCardsAtIndexAssertPopFromBank(){
        GameManager gm = new GameManager(1);
        gm.initializeGame();
        int bankSize = gm.getBankDeck().getSize();
        gm.changeCards(0, new HashSet<>(List.of(0, 1, 2)));
        assertEquals(bankSize - 3, gm.getBankDeck().getSize());
    }

    @org.junit.jupiter.api.Test
    void getCardDeckInfoTest2Players2Cards() {
        List<Deck> decks = List.of(new Deck(), new Deck());
        decks.get(0).addCard(new Card(cardRank.ACE, cardSuit.CLUBS));
        decks.get(0).addCard(new Card(cardRank.KING, cardSuit.CLUBS));
        decks.get(1).addCard(new Card(cardRank.ACE, cardSuit.DIAMONDS));
        decks.get(1).addCard(new Card(cardRank.KING, cardSuit.DIAMONDS));
        GameManager gm = new GameManager(decks);
        assertEquals("player #2\n| diamonds : king | diamonds : ace | ", gm.getCardDeckInfo(1));
    }

    @org.junit.jupiter.api.Test
    void showWholeTableTest2Players2Cards() {
        List<Deck> decks = List.of(new Deck(), new Deck());
        decks.get(0).addCard(new Card(cardRank.ACE, cardSuit.CLUBS));
        decks.get(0).addCard(new Card(cardRank.KING, cardSuit.CLUBS));
        decks.get(1).addCard(new Card(cardRank.ACE, cardSuit.DIAMONDS));
        decks.get(1).addCard(new Card(cardRank.KING, cardSuit.DIAMONDS));
        GameManager gm = new GameManager(decks);
        String expected = "\n\n------RESULTS------\nTable:\nplayer #1\n| clubs : king | clubs : ace | \n\nplayer #2\n| diamonds : king | diamonds : ace | \n\n";
        assertEquals(expected, gm.showWholeTable());
    }

}