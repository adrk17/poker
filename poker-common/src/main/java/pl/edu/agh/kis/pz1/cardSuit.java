package pl.edu.agh.kis.pz1;

public enum cardSuit {
    CLUBS(0, "clubs"),
    DIAMONDS(1, "diamonds"),
    HEARTS(2, "hearts"),
    SPADES(3, "spades");
    final int order;
    final String name;
    cardSuit(int order, String name){
        this.order = order;
        this.name = name;
    }
    public int getOrder(){return order;}
    public String toString(){return name;}
}
