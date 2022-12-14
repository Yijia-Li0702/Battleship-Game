package edu.duke.yl730.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class V2_CarrierTest {

    @Test
    void test_makeCoords_up(){
        Coordinate ul = new Coordinate(1, 2);
        V2_Carrier v = new V2_Carrier("Carrier", ul,'U','c','*');
        HashSet<Coordinate> actual = v.makeCoords(ul, 'U');
        HashMap<Integer,Coordinate> pieces = new HashMap<>();
        HashMap<Integer,Coordinate> actualPiece = v.piecesIndex;
        HashSet<Coordinate> expect = new HashSet<>();
        expect.add(new Coordinate(1,2));
        expect.add(new Coordinate(2,2));
        expect.add(new Coordinate(3,2));
        expect.add(new Coordinate(3,3));
        expect.add(new Coordinate(4,3));
        expect.add(new Coordinate(5,3));

        pieces.put(1,new Coordinate(1,2));
        pieces.put(2,new Coordinate(2,2));
        pieces.put(3,new Coordinate(3,2));
        pieces.put(4,new Coordinate(3,3));
        pieces.put(5,new Coordinate(4,3));
        pieces.put(6,new Coordinate(5,3));

        assertEquals(expect,actual);
        assertEquals(pieces,actualPiece);

        assertThrows(IllegalArgumentException.class, () -> new V2_Carrier<Character>("Carrier", ul,'T','c','*'));

    }
    @Test
    void test_makeCoords_right(){
        Coordinate ul = new Coordinate(1, 2);
        V2_Carrier v = new V2_Carrier("Carrier", ul,'R','c','*');
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

        pieces.put(1,new Coordinate(2,2));
        pieces.put(2,new Coordinate(2,3));
        pieces.put(3,new Coordinate(2,4));
        pieces.put(4,new Coordinate(1,4));
        pieces.put(5,new Coordinate(1,5));
        pieces.put(6,new Coordinate(1,6));

        assertEquals(expect,actual);
        assertEquals(pieces,actualPiece);
    }
    @Test
    void test_makeCoords_Down(){
        Coordinate ul = new Coordinate(1, 2);
        V2_Carrier v = new V2_Carrier("Carrier", ul,'D','c','*');
        HashSet<Coordinate> actual = v.makeCoords(ul, 'D');
        HashMap<Integer,Coordinate> actualPiece = v.piecesIndex;
        HashMap<Integer,Coordinate> pieces = new HashMap<>();
        HashSet<Coordinate> expect = new HashSet<>();
        expect.add(new Coordinate(5,2));
        expect.add(new Coordinate(4,2));
        expect.add(new Coordinate(3,2));
        expect.add(new Coordinate(3,3));
        expect.add(new Coordinate(2,3));
        expect.add(new Coordinate(1,3));

        pieces.put(1,new Coordinate(5,2));
        pieces.put(2,new Coordinate(4,2));
        pieces.put(3,new Coordinate(3,2));
        pieces.put(4,new Coordinate(3,3));
        pieces.put(5,new Coordinate(2,3));
        pieces.put(6,new Coordinate(1,3));

        assertEquals(expect,actual);
        assertEquals(pieces,actualPiece);
    }
    @Test
    void test_makeCoords_left(){
        Coordinate ul = new Coordinate(1, 2);
        V2_Carrier v = new V2_Carrier("Carrier", ul,'L','c','*');
        HashSet<Coordinate> actual = v.makeCoords(ul, 'L');
        HashMap<Integer,Coordinate> actualPiece = v.piecesIndex;
        HashMap<Integer,Coordinate> pieces = new HashMap<>();
        HashSet<Coordinate> expect = new HashSet<>();
        expect.add(new Coordinate(2,6));
        expect.add(new Coordinate(2,5));
        expect.add(new Coordinate(2,4));
        expect.add(new Coordinate(1,4));
        expect.add(new Coordinate(1,3));
        expect.add(new Coordinate(1,2));

        pieces.put(1,new Coordinate(2,6));
        pieces.put(2,new Coordinate(2,5));
        pieces.put(3,new Coordinate(2,4));
        pieces.put(4,new Coordinate(1,4));
        pieces.put(5,new Coordinate(1,3));
        pieces.put(6,new Coordinate(1,2));

        assertEquals(expect,actual);
        assertEquals(pieces,actualPiece);
    }
}
