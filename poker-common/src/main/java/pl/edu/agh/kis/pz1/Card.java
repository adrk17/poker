package pl.edu.agh.kis.pz1;

import java.util.Objects;

public class Card {
    cardRank rank;
    cardSuit suit;
    public Card(cardRank rank, cardSuit suit){
        this.rank = rank;
        this.suit = suit;
    }

    public Card(Card another){
        this.rank = another.getRank();
        this.suit = another.getSuit();
    }

    public cardRank getRank(){return rank;}
    public cardSuit getSuit(){return suit;}

    public void print(){
        System.out.println(rank.toString() +" - " + suit.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}
