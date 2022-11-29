package pl.edu.agh.kis.pz1.gameStructure;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SetParserTest {

    @org.junit.jupiter.api.Test
    void SetParserTestConstructor(){
        SetParser setParser = new SetParser("1,2");
        assertNotNull(setParser);
    }
    @org.junit.jupiter.api.Test
    void getReportTestGood() {
        SetParser setParser = new SetParser("1,2");
        assertEquals("ok", setParser.getReport());
    }
    @org.junit.jupiter.api.Test
    void getReportTestBad() {
        SetParser setParser = new SetParser("1,2,3");
        assertEquals("wrong", setParser.getReport());
    }
    @org.junit.jupiter.api.Test
    void getResultSetTestSize() {
        SetParser setParser = new SetParser("1,2");
        assertEquals(2, setParser.getResultSet().size());
    }
}