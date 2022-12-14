package edu.duke.yl730.battleship;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class V1ShipFactoryTest {

    @Test
    void test_makeSubmarine() {
        V1ShipFactory v = new V1ShipFactory();
        Coordinate c = new Coordinate(1,2);
        Placement p = new Placement(c,'V');
        checkShip(v.makeSubmarine(p), "Submarine", 's',
                new Coordinate[]{new Coordinate(1, 2), new Coordinate(2, 2)});
        Placement p1 = new Placement(c,'H');
        checkShip(v.makeSubmarine(p1), "Submarine", 's',
                new Coordinate[]{new Coordinate(1, 2), new Coordinate(1, 3)});
        Placement p2 = new Placement(c,'U');
        assertThrows(IllegalArgumentException.class, () -> v.makeSubmarine(p2));

    }
    @Test
    void test_makeBattleship() {
        V1ShipFactory v = new V1ShipFactory();
        Coordinate c = new Coordinate(1,2);
        Placement p = new Placement(c,'V');
        checkShip(v.makeBattleship(p), "Battleship", 'b',
                new Coordinate[]{new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2),
                        new Coordinate(4,2)});
        Placement p1 = new Placement(c,'H');
        checkShip(v.makeBattleship(p1), "Battleship", 'b',
                new Coordinate[]{new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4),
                new Coordinate(1,5)});
    }
    @Test
    void test_makeCarrier() {
        V1ShipFactory v = new V1ShipFactory();
        Coordinate c = new Coordinate(1,2);
        Placement p = new Placement(c,'V');
        checkShip(v.makeCarrier(p), "Carrier", 'c',
                new Coordinate[]{new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2),
                        new Coordinate(4,2), new Coordinate(5,2), new Coordinate(6,2)});
                ;
        Placement p1 = new Placement(c,'H');
        checkShip(v.makeCarrier(p1), "Carrier", 'c',
                new Coordinate[]{new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4),
                        new Coordinate(1,5),new Coordinate(1,6), new Coordinate(1,7)});
    }
    @Test
    void test_makeDestroyer() {
        V1ShipFactory v = new V1ShipFactory();
        Coordinate c = new Coordinate(1,2);
        Placement p = new Placement(c,'H');
        checkShip(v.makeDestroyer(p), "Destroyer", 'd',
                new Coordinate[]{new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4)});
        Coordinate[] coordinates = new Coordinate[]{new Coordinate(1, 2), new Coordinate(2, 2),
                new Coordinate(3, 2)};
        Placement p1 = new Placement(c,'V');
        checkShip(v.makeDestroyer(p1), "Destroyer", 'd', coordinates);

    }
    private void checkShip(Ship<Character> testShip, String expectedName,
                           char expectedLetter,  Coordinate[] expectedLocs){
        assertEquals(testShip.getName(), expectedName);
        for(int i = 0; i<expectedLocs.length;i++){
            //assert(testShip.occupiesCoordinates(expectedLocs[i]));
            assertEquals(testShip.getDisplayInfoAt(expectedLocs[i],true),expectedLetter);
        }
    }

}

