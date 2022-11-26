package pl.edu.agh.kis.pz1;
import java.util.*;


public class Deck {
    private List<Card> cardDeck;
    public Deck(){
        cardDeck = new ArrayList<>();
    }
    public Deck(Deck another){
        cardDeck = new ArrayList<>(another.cardDeck);
    }


    //Adds card
    public void addCard(Card card){
        cardDeck.add(card);
    }
    public Card getCard(int index){
        return cardDeck.get(index);
    }

    public void sort(){
        cardDeck.sort(new CompareCards());
    }
    public void generateCardDeck(){
        for (cardSuit suit : cardSuit.values()){
            for (cardRank rank : cardRank.values()){
                addCard(new Card(rank, suit));
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(cardDeck);
    }

    public Deck shuffleNew(){
        Deck newDeck = new Deck(this);
        newDeck.shuffle();
        return newDeck;
    }

    public void print(){
        for (Card c : cardDeck){
            System.out.print(c.getSuit().toString() + ": " + c.getRank().toString() + " | ");
        }
        System.out.println("\n");
    }


    public Card pop(){
        return cardDeck.remove(0);
    }
    public Card remove(int index){
        return cardDeck.remove(index);
    }
    public void replaceCard(int index, Card card){
        cardDeck.set(index, card);
    }
}

