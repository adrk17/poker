package pl.edu.agh.kis.pz1;

import java.util.Objects;
/***
 * Ta klasa reprezentuje kartę w grze w pokera
 * */
public class Card {
    /***
     * To jest pole reprezentujące rangę karty
     * */
    cardRank rank;
    /***
     * To jest pole reprezentujące kolor karty
     * */
    cardSuit suit;
    /***
     * To jest konstruktor karty
     * @param rank to jest parametr reprezentujący rangę karty
     * @param suit to jest parametr reprezentujący kolor karty
     * */
    public Card(cardRank rank, cardSuit suit){
        this.rank = rank;
        this.suit = suit;
    }
    /***
     * To jest konstruktor karty w oparciu o inną kartę
     * @param another to parametr reprezentujący kartę bazową
     * */
    public Card(Card another){
        this.rank = another.getRank();
        this.suit = another.getSuit();
    }

    /***
     * To jest metoda zwracająca rangę karty
     * @return to jest zwracana wartość rangi karty
     * */
    public cardRank getRank(){return rank;}
    /***
     * To jest metoda zwracająca kolor karty
     * @return zwracana wartość koloru karty
     * */
    public cardSuit getSuit(){return suit;}

    /***
     * To jest metoda wypisująca kartę na ekran
     * */
    public void print(){
        System.out.println(rank.toString() +" - " + suit.toString());
    }

    /***
     * To jest metoda porównująca karty
     * @param o jest parametrem reprezentującym kartę do porównania
     * @return zwracana wartość logiczna porównania
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    /***
     * To jest metoda zwracająca hashcode karty
     * @return zwracana wartość hashcode karty
     * */
    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}
