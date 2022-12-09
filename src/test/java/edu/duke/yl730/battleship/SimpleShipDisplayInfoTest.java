package edu.duke.yl730.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleShipDisplayInfoTest {

    @Test
    void test_getInfo() {
        Character onhit = 'h';
        Character myData ='m';
        Boolean hit = true;
        SimpleShipDisplayInfo s = new SimpleShipDisplayInfo(myData,onhit);
        assertEquals (s.getInfo(null,true),onhit);
        assertEquals(s.getInfo(null,false),myData);
    }
}
