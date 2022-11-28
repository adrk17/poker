package pl.edu.agh.kis.pz1.gameStructure;

import java.util.HashSet;
import java.util.Set;

public class SetParser {
    String report = "unchecked";
    Set<Integer> resultSet;
    public SetParser(String potentialSet){
        resultSet = new HashSet<>();
        if (potentialSet.length() != 3 && potentialSet.length() != 1){
            report = "wrong";
        }
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

    private void checkIfIntInRange(int i){
        if (i < 0 || i > 4){
            report = "wrong";
        }else{
            resultSet.add(i);
        }
    }
    public String getReport(){
        return report;
    }
    public Set<Integer> getResultSet(){
        return resultSet;
    }
}
