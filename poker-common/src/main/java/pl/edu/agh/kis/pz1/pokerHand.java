package pl.edu.agh.kis.pz1;

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


    final int order;
    final String name;
    pokerHand(int order, String name){
        this.order = order;
        this.name = name;
    }
    public int getOrder(){return order;}
    public String toString(){return name;}
}
