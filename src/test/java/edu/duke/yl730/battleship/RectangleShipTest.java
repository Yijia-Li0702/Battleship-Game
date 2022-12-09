package edu.duke.yl730.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class RectangleShipTest {
  @Test
  public void test_makeCoords() {
    Coordinate ul = new Coordinate(1, 2);
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(2, 2);
    Coordinate c3 = new Coordinate(3, 2);
    RectangleShip<Character> rec = new RectangleShip<Character>("submarine",ul, 1, 3,'s','*');
    HashSet<Coordinate> actual = rec.makeCoords(ul, 1, 3);
    HashSet<Coordinate> expected = new HashSet<>();
    expected.add(c1);
    expected.add(c2);
    expected.add(c3);
    assertEquals(actual, expected);
  }


  @Test
  public void test_occupiesCoordinates() {
    Coordinate ul = new Coordinate(1, 2);
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(2, 2);
    Coordinate c3 = new Coordinate(3, 2);
    Coordinate c4 = new Coordinate(4,5);
    RectangleShip<Character> rec = new RectangleShip<Character>("submarine",ul, 1, 3,'s','*');
    assert (rec.occupiesCoordinates(c1));
    assert (rec.occupiesCoordinates(c2));
    assert (rec.occupiesCoordinates(c3));
    assert (!rec.occupiesCoordinates(c4));
  }

  @Test
  public void test_recordHitAt_and_wasHitAt(){
    Coordinate ul = new Coordinate(1, 2);
    RectangleShip<Character> rec = new RectangleShip<Character>("submarine",ul, 1, 3,'s','*');
    Coordinate c1 = new Coordinate(2, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(4,5);
    rec.recordHitAt(c1);
    assert (rec.wasHitAt(c1));
    assert (!rec.wasHitAt(c2));
    assertThrows(IllegalArgumentException.class, () -> rec.wasHitAt(c3));
    assertThrows(IllegalArgumentException.class, () -> rec.recordHitAt(c3));
  }

  @Test
  public void test_isSunk(){
    Coordinate ul = new Coordinate(1, 2);
    RectangleShip<Character> rec = new RectangleShip<Character>("submarine",ul, 1, 3,'s','*');
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(2, 2);
    Coordinate c3 = new Coordinate(3, 2);
    rec.recordHitAt(c1);
    rec.recordHitAt(c2);
    assert (!rec.isSunk());
    rec.recordHitAt(c3);
    assert (rec.isSunk());
  }

  @Test
  public void test_getDisplayInfoAt(){
    Coordinate ul = new Coordinate(1, 2);
    RectangleShip<Character> rec = new RectangleShip<Character>("submarine",ul, 1, 3,'s','*');
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(2, 2);
    Coordinate c3 = new Coordinate(3, 2);
    Coordinate c4 = new Coordinate(4,5);

    rec.recordHitAt(c1);
    rec.recordHitAt(c2);
    assertEquals(rec.getDisplayInfoAt(c1,true),'*');
    assertEquals(rec.getDisplayInfoAt(c2,true),'*');
    assertEquals(rec.getDisplayInfoAt(c3,true),'s');
    //rec.recordHitAt();
    assertEquals(rec.getDisplayInfoAt(c1,false),'s');

    assertThrows(IllegalArgumentException.class, () -> rec.getDisplayInfoAt(c4,true));
  }
  @Test
  public void test_getName(){
    Coordinate ul = new Coordinate(1, 2);
    RectangleShip<Character> rec = new RectangleShip<Character>("submarine",ul, 1, 3,'s','*');
    assertEquals(rec.getName(),"submarine");
  }
  @Test
  public void test_getCoordinates(){
    Coordinate ul = new Coordinate(1, 2);
    RectangleShip<Character> rec = new RectangleShip<Character>("submarine",ul, 1, 3,'s','*');
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(2, 2);
    Coordinate c3 = new Coordinate(3, 2);
    Set<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(c1);
    expected.add(c2);
    expected.add(c3);
    assertEquals(expected, rec.getCoordinates());
  }

}










