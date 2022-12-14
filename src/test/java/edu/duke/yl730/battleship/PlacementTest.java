package edu.duke.yl730.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
    public void test_equals() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(1, 3);
    Coordinate c4 = new Coordinate(3, 2);
    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c2, 'V');
    Placement p3 = new Placement(c2, 'H');
    Placement p4 = new Placement(c3, 'V');
    Placement p5 = new Placement(c4, 'H');
    assertEquals(p1, p2);
    assertEquals(p1,p1);
    assertNotEquals(p2,p3);
    assertNotEquals(p3,p4);
    assertNotEquals(p3,p5);
    assertNotEquals(p3, c1);
  }
  @Test
  public void test_toString() {
    Coordinate c1 = new Coordinate(2,3);
    Coordinate c2 = new Coordinate(3,2);
    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c2, 'V'); 
   assertEquals("(2, 3) V",p1.toString());
    assertEquals("(3, 2) V", p2.toString());
  }
  
  @Test
  public void test_hashCode() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(0, 3);
    Coordinate c4 = new Coordinate(2, 1);
    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c2, 'V');
    Placement p3 = new Placement(c3, 'h');
    Placement p4 = new Placement(c4, 'H');
    assertEquals(p1.hashCode(), p2.hashCode());
    assertNotEquals(p1.hashCode(), p3.hashCode());
    assertNotEquals(p1.hashCode(), p4.hashCode());
  }
  
  @Test
  void test_string_constructor_valid_cases() {
    Placement p1 = new Placement("B3h");
    Coordinate c1 = new Coordinate("B3");
    assertEquals(c1, p1.getWhere());
    assertEquals('H', p1.getOrientation());
    Placement p2 = new Placement("D5v");
    Coordinate c2 = new Coordinate("D5");
    assertEquals(c2, p2.getWhere());
    assertEquals('V', p2.getOrientation());
    Placement p3 = new Placement("A9V");
    Coordinate c3 = new Coordinate("A9");
    assertEquals(c3, p3.getWhere());
    assertEquals('V', p3.getOrientation());
    Placement p4 = new Placement("Z0H");
    Coordinate c4 = new Coordinate("Z0");
    assertEquals(c4, p4.getWhere());
    assertEquals('H', p4.getOrientation());
  }
  
  @Test
  public void test_string_constructor_error_cases() {
    assertThrows(IllegalArgumentException.class, () -> new Placement("00V"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("AAh"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("@0"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("[0"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A/"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A:"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A0b"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A12"));
  }
  
}
