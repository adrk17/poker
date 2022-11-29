package pl.edu.agh.kis.pz1;

import java.util.Comparator;

public class CompareCards implements Comparator<Card> {

    /**
     * Metoda porównująca karty
     * @param o1 to parametr reprezentujący pierwszą kartę do porównania
     * @param o2 to parametr reprezentujący drugą kartę do porównania
     * @return zwracana wartość logiczna porównania
     */
    @Override
    public int compare(Card o1, Card o2) {
        if (o1.getRank().getOrder() > o2.getRank().getOrder()){return 1;}
        else if(o1.getRank().getOrder() < o2.getRank().getOrder()){return -1;}
        else {
            return Integer.compare(o1.getSuit().getOrder(), o2.getSuit().getOrder());
        }
    }
}
