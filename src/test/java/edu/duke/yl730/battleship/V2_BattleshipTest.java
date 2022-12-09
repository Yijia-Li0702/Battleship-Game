package edu.duke.yl730.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class V2_BattleshipTest {

    @Test
    void test_makeCoords_up(){
        Coordinate ul = new Coordinate(1, 2);
        V2_Battleship v = new V2_Battleship("Battleship", ul,'U','b','*');
        HashSet<Coordinate> actual = v.makeCoords(ul, 'U');
        HashMap<Integer,Coordinate> pieces = new HashMap<>();
        HashMap<Integer,Coordinate> actualPiece = v.piecesIndex;
        HashSet<Coordinate> expect = new HashSet<>();
        expect.add(new Coordinate(1,3));
        expect.add(new Coordinate(2,2));
        expect.add(new Coordinate(2,3));
        expect.add(new Coordinate(2,4));
        pieces.put(1,new Coordinate(1,3));
        pieces.put(2,new Coordinate(2,2));
        pieces.put(3,new Coordinate(2,3));
        pieces.put(4,new Coordinate(2,4));
        assertEquals('U',v.getOrientation());
        assertEquals(expect,actual);
        assertEquals(pieces,actualPiece);
        assertThrows(IllegalArgumentException.class, () -> new V2_Battleship<Character>("Battleship", ul,'T','b','*'));
    }
    @Test
    void test_makeCoords_right(){
        Coordinate ul = new Coordinate(1, 2);
        V2_Battleship v = new V2_Battleship("Battleship", ul,'R','b','*');
        HashSet<Coordinate> actual = v.makeCoords(ul, 'R');
        HashMap<Integer,Coordinate> pieces = new HashMap<>();
        HashMap<Integer,Coordinate> actualPiece = v.piecesIndex;
        HashSet<Coordinate> expect = new HashSet<>();
        expect.add(new Coordinate(2,3));
        expect.add(new Coordinate(1,2));
        expect.add(new Coordinate(2,2));
        expect.add(new Coordinate(3,2));
        pieces.put(1,new Coordinate(2,3));
        pieces.put(2,new Coordinate(1,2));
        pieces.put(3,new Coordinate(2,2));
        pieces.put(4,new Coordinate(3,2));
        assertEquals(expect,actual);
        assertEquals(pieces,actualPiece);
    }

    @Test
    void test_makeCoords_down(){
        Coordinate ul = new Coordinate(1, 2);
        V2_Battleship v = new V2_Battleship("Battleship", ul,'D','b','*');
        HashSet<Coordinate> actual = v.makeCoords(ul, 'D');
        HashMap<Integer,Coordinate> pieces = new HashMap<>();
        HashMap<Integer,Coordinate> actualPiece = v.piecesIndex;
        HashSet<Coordinate> expect = new HashSet<>();
        expect.add(new Coordinate(2,3));
        expect.add(new Coordinate(1,2));
        expect.add(new Coordinate(1,3));
        expect.add(new Coordinate(1,4));
        pieces.put(1,new Coordinate(2,3));
        pieces.put(4,new Coordinate(1,2));
        pieces.put(3,new Coordinate(1,3));
        pieces.put(2,new Coordinate(1,4));
        assertEquals(expect,actual);
        assertEquals(pieces,actualPiece);
    }
    @Test
    void test_makeCoords_left(){
        Coordinate ul = new Coordinate(1, 2);
        V2_Battleship v = new V2_Battleship("Battleship", ul,'L','b','*');
        HashSet<Coordinate> actual = v.makeCoords(ul, 'L');
        HashMap<Integer,Coordinate> pieces = new HashMap<>();
        HashMap<Integer,Coordinate> actualPiece = v.piecesIndex;
        HashSet<Coordinate> expect = new HashSet<>();
        expect.add(new Coordinate(2,2));
        expect.add(new Coordinate(3,3));
        expect.add(new Coordinate(2,3));
        expect.add(new Coordinate(1,3));
        pieces.put(1,new Coordinate(2,2));
        pieces.put(2,new Coordinate(3,3));
        pieces.put(3,new Coordinate(2,3));
        pieces.put(4,new Coordinate(1,3));
        assertEquals(expect,actual);
        assertEquals(pieces,actualPiece);
    }
}
