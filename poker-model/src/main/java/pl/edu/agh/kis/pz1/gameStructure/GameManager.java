package pl.edu.agh.kis.pz1.gameStructure;
import pl.edu.agh.kis.pz1.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameManager {
    int numPlayers;
    Deck bank;
    List<Deck> players;
    HandVerificator hv;
    public GameManager(int _numPlayers){
        numPlayers = _numPlayers;
    }
    public GameManager(List<Deck> pl){
        players = pl;
    }


    public void initializeGame(){
        players = new ArrayList<Deck>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Deck());
        }
        System.out.println("players created");

        int cardNum = 5;
        bank = new Deck();
        bank.generateCardDeck();
        System.out.println("generated the deck");

        bank.shuffle();
        System.out.println("the deck was shuffled");

        System.out.println("dealing the cards...");
        for (int i = 0; i < numPlayers; i++) {
            for (int j = 0; j < cardNum; j++) {
                players.get(i).addCard(bank.pop());
            }
        }
        System.out.println("cards dealt");
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("player" + (i+1));
            players.get(i).print();
        }
    }

    public void endGame(){
        if (players == null){return;}
        hv = new HandVerificator(players);
        List<Tuple> results = hv.verifyHands();
        checkForWinner(results);
    }


     List<Integer> checkForWinner(List<Tuple> results) {
        if (results == null) {
            System.out.println("No results!");
            return null;
        }
        pokerHand bestHand = pokerHand.HIGHCARD;
        int bestScore = 0;
        List<Integer> winners = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getPokerHand().getOrder() < bestHand.getOrder()) {
                bestHand = results.get(i).getPokerHand();
                bestScore = results.get(i).getScore();
                winners.clear();
                winners.add(i);
            } else if (results.get(i).getPokerHand() == bestHand) {
                if (results.get(i).getScore() > bestScore){
                    bestScore = results.get(i).getScore();
                    winners.clear();
                    winners.add(i);
                } else if(results.get(i).getScore() == bestScore){
                    winners.add(i);
                }
            }
        }
        if (winners.size() == 1) {
            int w = winners.get(0) + 1;
            System.out.println("The winner is: " + "player #" + w + "\nWinning hand: " + results.get(winners.get(0)).getPokerHand().toString());
        }
        else {
            System.out.println("It is a draw! The winners are:");
            for (var winner : winners) {
                int w = winner+1;
                System.out.println("player #"+w);
            }
            System.out.println("Winning hand: " + results.get(winners.get(0)).getPokerHand().toString());
        }
        return winners;
    }

    void changeCards(int playerIndex, Set<Integer> indexes){
        for (int index : indexes) {
            players.get(playerIndex).replaceCard(index, bank.pop());
        }
        players.get(playerIndex).sort();
    }
}
