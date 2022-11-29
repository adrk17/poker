package pl.edu.agh.kis.pz1.gameStructure;

import java.util.HashSet;
import java.util.Set;
/**
 * Klasa reprezentująca parser Stringa do Setu indeksu kart do wymianny
 */
public class SetParser {
    String report = "unchecked";
    Set<Integer> resultSet;
    /**
     * Konstruktor klasy SetParser - który przeprowadza analizę dostarczonego stringa i zapisuje wynk do pola result
     * @param potentialSet to parametr reprezentujący Stringa do przetworzenia
     */
    public SetParser(String potentialSet){
        resultSet = new HashSet<>();
        // string musi mieć długość 1 bądź 3
        if (potentialSet.length() != 3 && potentialSet.length() != 1){
            report = "wrong";
        }
        // string musi składać się z 1 lub 2 cyfr i jednego znaku oddzielającego cyfry
        else if (Character.isDigit(potentialSet.charAt(0)) && potentialSet.length() == 1){
            int first = Integer.parseInt(potentialSet.substring(0,1));
            report = "ok";
            checkIfIntInRange(first);
        }else if(potentialSet.length() == 3 && Character.isDigit(potentialSet.charAt(0)) && Character.isDigit(potentialSet.charAt(2))) {
            int first =Integer.parseInt(potentialSet.substring(0, 1));
            int second = Integer.parseInt(potentialSet.substring(2, 3));
            report = "ok";
            checkIfIntInRange(first);
            checkIfIntInRange(second);
        }else {
            report = "wrong";
        }
    }

    /**
     * Metoda sprawdzająca czy liczba jest w zakresie 0-4 gdyż reprezentuje ona indeks w talii 5 kart gracza
     * @param i to parametr reprezentujący indeks do sprawdzenia
     */
    private void checkIfIntInRange(int i){
        if (i < 0 || i > 4){
            report = "wrong";
        }else{
            resultSet.add(i);
        }
    }

    /**
     * Metoda zwracająca wynik analizy stringa
     * @return zwraca Stringa z wynikiem analizy
     */
    public String getReport(){
        return report;
    }

    /**
     * Metoda zwraca wynikowy Set indeksów kart do wymiany, wartość ma znaczenie tylko i wyłącznie
     * gdy getReport() zwraca "ok"
     * @return zwraca Set indeksów kart do wymiany
     */
    public Set<Integer> getResultSet(){
        return resultSet;
    }
}
