package pl.edu.agh.kis.pz1;

/**
 * Enum reprezentujący rangę karty
 */
public enum cardRank {
    N2(0, "2"),
    N3(1, "3"),
    N4(2, "4"),
    N5(3, "5"),
    N6(4, "6"),
    N7(5, "7"),
    N8(6, "8"),
    N9(7, "9"),
    N10(8,"10"),
    JACK(9, "jack"),
    QUEEN(10, "queen"),
    KING(11, "king"),
    ACE(12, "ace");
    /**
     * Pole reprezentujące wartość rangi karty używana do dalszego sortowania kart
     */
    final int order;
    /**
     * Pole reprezentujące nazwę rangi karty
     */
    final String name;
    /**
     * Konstruktor rangi karty
     * @param order to parametr reprezentujący wartość rangi karty
     * @param name to parametr reprezentujący nazwę rangi karty
     */
    cardRank(int order, String name){
        this.order = order;
        this.name = name;
    }
    /**
     * Metoda zwracająca wartość rangi karty
     * @return zwracana wartość rangi karty
     */
    public int getOrder(){return order;};
    /**
     * Metoda zwracająca nazwę rangi karty
     * @return zwracana nazwa rangi karty
     */
    public String toString(){return name;};

}
