package pl.edu.agh.kis.pz1;

/**
 * Klasa pomocnicza do przechowywania par ręki i ostatecznej wartości danej ręki potrzebnej do porównania w razie remisu
 */
public class Tuple {
    /**
     * Pole reprezentujące rękę
     */
    private pokerHand ph;
    /**
     * Pole reprezentujące wartość danej ręki
     */
    private int score;

    /**
     * Konstruktor klasy Tuple
     * @param _ph to parametr reprezentujący rękę
     * @param _score to parametr reprezentujący wartość danej ręki
     */
    public Tuple(pokerHand _ph, int _score) {
        ph = _ph;
        score = _score;
    }
    /**
     * Metoda zwracająca rękę
     * @return zwracana ręka
     */
    public pokerHand getPokerHand() {
        return ph;
    }
    /**
     * Metoda zwracająca wartość danej ręki
     * @return zwracana wartość danej ręki
     */
    public int getScore() {
        return score;
    }
}
