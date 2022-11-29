package pl.edu.agh.kis.pz1;

/**
 * Klasa reprezentująca rękę w grze w pokera
 */
public enum pokerHand {
    HIGHCARD(9, "high card"),
    ONEPAIR(8, "one pair"),
    TWOPAIR(7, "two pair"),
    THREE(6, "three of a kind"),
    STRAIGHT(5, "straight"),
    FLUSH(4, "flush"),
    FULLHOUSE(3, "full house"),
    FOUR(2, "four of a kind"),
    STRAIGHTFLUSH(1, "straight flush"),
    ROYALFLUSH(0, "royal flush");

    /**
     * Pole reprezentujące wartość ręki w grze w pokera
     */
    final int order;
    /**
     * Pole reprezentujące nazwę ręki
     */
    final String name;
    /**
     * Konstruktor ręki
     * @param order to parametr reprezentujący wartość ręki
     * @param name to parametr reprezentujący nazwę ręki
     */
    pokerHand(int order, String name){
        this.order = order;
        this.name = name;
    }
    /**
     * Metoda zwracająca wartość ręki
     * @return zwracana wartość ręki
     */
    public int getOrder(){return order;}
    /**
     * Metoda zwracająca nazwę ręki
     * @return zwracana nazwa ręki
     */
    @Override
    public String toString(){return name;}
}
