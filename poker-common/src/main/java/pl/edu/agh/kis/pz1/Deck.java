package pl.edu.agh.kis.pz1;
import java.util.*;

/**
 * Klasa reprezentująca talie kart
 */
public class Deck {
    /**
     * Pole reprezentujące listę kart w talii
     */
    private List<Card> cardDeck;
    /**
     * Konstruktor talii kart
     */
    public Deck(){
        cardDeck = new ArrayList<>();
    }

    /**
     * Konstruktor talii kart kopiujący bazową listę kart
     * @param another to parametr reprezentujący bazową listę kart
     */
    public Deck(Deck another){
        cardDeck = new ArrayList<>(another.cardDeck);
    }


    /**
     * Metoda dodająca kartę do talii
     * @param card to parametr reprezentujący kartę do dodania
     */
    public void addCard(Card card){
        cardDeck.add(card);
    }

    /**
     * Metoda zwracająca kartę z talii
     * @param index to parametr reprezentujący indeks karty w talii
     * @return zwracana karta z talii
     */
    public Card getCard(int index){
        return cardDeck.get(index);
    }

    /**
     * Metoda sortująca talię kart
     */
    public void sort(){
        cardDeck.sort(new CompareCards());
    }

    /**
     * Metoda generująca pełną talię kart (52 karty)
     */
    public void generateCardDeck(){
        for (cardSuit suit : cardSuit.values()){
            for (cardRank rank : cardRank.values()){
                addCard(new Card(rank, suit));
            }
        }
    }

    /**
     * Metoda tasująca talię kart
     */
    public void shuffle(){
        Collections.shuffle(cardDeck);
    }

    /**
     * Metoda wypisująca talię kart na ekran
     */
    public void print(){
        for (Card c : cardDeck){
            System.out.print(c.getSuit().toString() + ": " + c.getRank().toString() + " | ");
        }
        System.out.println("\n");
    }

    /**
     * Metoda usuwająca pierwszą kartę z talii i zwracająca ją
     * @return zwracana pierwsza karta z talii
     */
    public Card pop(){
        return cardDeck.remove(0);
    }


    /**
     * Metoda zamieniająca kartę o podanym indeksie w talii na nową kartę
     * @param index to parametr reprezentujący indeks karty do zamiany
     * @param card to parametr reprezentujący nową kartę wstawianą w miejsce starej
     */
    public void replaceCard(int index, Card card){
        cardDeck.set(index, card);
    }

    /**
     * Metoda tworząca tekstową reprezentację talii kart
     * @return zwracana tekstowa reprezentacja talii kart
     */
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("| ");
        for (Card c : cardDeck){
            result.append(c.getSuit().toString()).append(" : ").append(c.getRank().toString()).append(" | ");
        }
        return result.toString();
    }

    /**
     * Gettter zwracający rozmiar talii kart
     */
    public int getSize(){
        return cardDeck.size();
    }

}

