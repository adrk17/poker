package pl.edu.agh.kis.pz1;

public class Tuple {
    private pokerHand ph;
    private int score;

    public Tuple(pokerHand _ph, int _score) {
        ph = _ph;
        score = _score;
    }

    public pokerHand getPokerHand() {
        return ph;
    }
    public int getScore() {
        return score;
    }
}
