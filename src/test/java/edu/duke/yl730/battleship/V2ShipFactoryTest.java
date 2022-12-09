
package edu.duke.yl730.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class V2ShipFactoryTest {

    @Test
    void test_makeSubmarine() {
        V2ShipFactory v = new V2ShipFactory();
        Coordinate c = new Coordinate(1,2);
        Placement p = new Placement(c,'V');
        checkShip(v.makeSubmarine(p), "Submarine", 's',
                new Coordinate[]{new Coordinate(1, 2), new Coordinate(2, 2)});
        Placement p1 = new Placement(c,'H');
        checkShip(v.makeSubmarine(p1), "Submarine", 's',
                new Coordinate[]{new Coordinate(1, 2), new Coordinate(1, 3)});
        assertThrows(IllegalArgumentException.class, () -> v.makeSubmarine(new Placement(c,'b')));
    }

    @Test
    void test_makeDestroyer() {
        V2ShipFactory v = new V2ShipFactory();
        Coordinate c = new Coordinate(1,2);
        Placement p = new Placement(c,'H');
        checkShip(v.makeDestroyer(p), "Destroyer", 'd',
                new Coordinate[]{new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4)});
        Coordinate[] coordinates = new Coordinate[]{new Coordinate(1, 2), new Coordinate(2, 2),
                new Coordinate(3, 2)};
        Placement p1 = new Placement(c,'V');
        checkShip(v.makeDestroyer(p1), "Destroyer", 'd', coordinates);

    }
    @Test
    void test_moveShip_sub_des() {
        V2ShipFactory v = new V2ShipFactory();
        Placement p = new Placement(new Coordinate(1,2),'V');
        Placement p1 = new Placement(new Coordinate(3,4),'H');
        Ship<Character> mar = v.moveShip(v.makeSubmarine(p1),p);
        checkShip(mar, "Submarine", 's',
                new Coordinate[]{new Coordinate(1, 2), new Coordinate(2, 2)});
        Ship<Character> des = v.moveShip(v.makeDestroyer(p1),new Placement(new Coordinate(1,2),'H'));
        checkShip(des, "Destroyer", 'd',
                new Coordinate[]{new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4)});

    }
    @Test
    void test_moveShip_carrier(){
        Coordinate ul = new Coordinate(1, 2);
        V2_Carrier v_old = new V2_Carrier("Carrier", new Coordinate(3,5),'U','c','*');
        V2ShipFactory fac = new V2ShipFactory();
        V2_Carrier<Character> v = (V2_Carrier<Character>)fac.moveShip(v_old, new Placement(ul,'R'));
        HashSet<Coordinate> actual = v.makeCoords(ul, 'R');
        HashMap<Integer,Coordinate> actualPiece = v.piecesIndex;
        HashMap<Integer,Coordinate> pieces = new HashMap<>();
        HashSet<Coordinate> expect = new HashSet<>();
        expect.add(new Coordinate(2,2));
        expect.add(new Coordinate(2,3));
        expect.add(new Coordinate(2,4));
        expect.add(new Coordinate(1,4));
        expect.add(new Coordinate(1,5));
        expect.add(new Coordinate(1,6));
        assertEquals(expect,actual);
    }

    @Test
    void test_moveShip_battleship(){
        Coordinate ul = new Coordinate(1, 2);
        V2_Battleship v_old = new V2_Battleship("Battleship",  new Coordinate(3,5),'R','b','*');
        V2ShipFactory fac = new V2ShipFactory();
        V2_Battleship<Character> v = (V2_Battleship<Character>)fac.moveShip(v_old, new Placement(ul,'U'));

        HashSet<Coordinate> actual = v.makeCoords(ul, 'U');
        HashMap<Integer,Coordinate> pieces = new HashMap<>();
        HashMap<Integer,Coordinate> actualPiece = v.piecesIndex;
        HashSet<Coordinate> expect = new HashSet<>();
        expect.add(new Coordinate(1,3));
        expect.add(new Coordinate(2,2));
        expect.add(new Coordinate(2,3));
        expect.add(new Coordinate(2,4));
        assertEquals('U',v.getOrientation());
        assertEquals(expect,actual);

    }
    private void checkShip(Ship<Character> testShip, String expectedName,
                           char expectedLetter,  Coordinate[] expectedLocs){
        assertEquals(testShip.getName(), expectedName);
        for(int i = 0; i<expectedLocs.length;i++){
            assertEquals(testShip.getDisplayInfoAt(expectedLocs[i],true),expectedLetter);
        }
    }

}
