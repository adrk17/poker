package pl.edu.agh.kis.pz1;

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
    final int order;
    final String name;
    cardRank(int order, String name){
        this.order = order;
        this.name = name;
    }
    public int getOrder(){return order;};
    public String toString(){return name;};

}
