package edu.duke.yl730.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InBoundsRuleCheckerTest {

    @Test
    void test_checkPlacement() {
        Coordinate ul = new Coordinate(1, 2);
        Coordinate ul1 = new Coordinate(3, 3);
        Coordinate c1 = new Coordinate(-1,3);
        Coordinate c2 = new Coordinate(1,-1);


        RectangleShip<Character> rec1 = new RectangleShip<Character>("Destroyer",ul, 1, 3,'d','*');
        RectangleShip<Character> rec2 = new RectangleShip<Character>("Destroyer",ul1, 3, 1,'d','*');
        RectangleShip<Character> rec3 = new RectangleShip<Character>("Carriers",ul1, 1, 6,'d','*');
        RectangleShip<Character> rec4 = new RectangleShip<Character>("Carriers",c1, 1, 6,'d','*');
        RectangleShip<Character> rec5 = new RectangleShip<Character>("Carriers",c2, 1, 6,'d','*');

        InBoundsRuleChecker<Character> in1 = new InBoundsRuleChecker<Character>(null);
        Board<Character> b = new BattleShipBoard<Character>(5, 6, in1,'X');
        assertEquals (null,in1.checkPlacement(rec1, b));
        //check each direction is valid
        assertEquals ("That placement is invalid: the ship goes off the right of the board.\n",in1.checkPlacement(rec2, b));
        assertEquals ("That placement is invalid: the ship goes off the bottom of the board.\n",in1.checkPlacement(rec3, b));
        assertEquals ("That placement is invalid: the ship goes off the top of the board.\n",in1.checkPlacement(rec4, b));
        assertEquals ("That placement is invalid: the ship goes off the left of the board.\n",in1.checkPlacement(rec5, b));

    }
}
