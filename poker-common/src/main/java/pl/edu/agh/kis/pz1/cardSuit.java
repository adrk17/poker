package pl.edu.agh.kis.pz1;

/**
 * Enum reprezentujący kolor karty
 */
public enum cardSuit {
    CLUBS(0, "clubs"),
    DIAMONDS(1, "diamonds"),
    HEARTS(2, "hearts"),
    SPADES(3, "spades");
    /**
     * Pole reprezentujące wartość koloru karty używana do dalszego sortowania kart
     */
    final int order;
    /**
     * Pole reprezentujące nazwę koloru karty
     */
    final String name;
    /**
     * Konstruktor koloru karty
     * @param order to parametr reprezentujący wartość koloru karty
     * @param name to parametr reprezentujący nazwę koloru karty
     */
    cardSuit(int order, String name){
        this.order = order;
        this.name = name;
    }
    /**
     * Metoda zwracająca wartość koloru karty
     * @return zwracana wartość koloru karty
     */
    public int getOrder(){return order;}
    /**
     * Metoda zwracająca nazwę koloru karty
     * @return zwracana nazwa koloru karty
     */
    public String toString(){return name;}
}
