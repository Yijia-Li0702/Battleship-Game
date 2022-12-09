package edu.duke.yl730.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoCollisionRuleCheckerTest {
    @Test
    void test_checkPlacement() {
        Placement p1= new Placement(new Coordinate(1,2),'V');
        Placement p2= new Placement(new Coordinate(5,2),'H');
        Placement p3= new Placement(new Coordinate(0,0),'V');
        Placement p4= new Placement(new Coordinate(1,2),'H');


        V1ShipFactory v = new V1ShipFactory();
        //put s1,s2 to the board
        Ship<Character> s1 = v.makeDestroyer(p1);
        Ship<Character> s2 = v.makeSubmarine(p2);
        //No collision with other ship
        Ship<Character> s3 = v.makeSubmarine(p3);
        //With collision with p1
        Ship<Character> s4 = v.makeSubmarine(p4);

        NoCollisionRuleChecker<Character> no = new NoCollisionRuleChecker<>(null);
        Board<Character> b = new BattleShipBoard<Character>(5, 6, no,'X');
        b.tryAddShip(s1);
        b.tryAddShip(s2);
        assertEquals (null,no.checkPlacement(s3,b));
        assertEquals ("That placement is invalid: the ship overlaps another ship.\n",no.checkPlacement(s4,b));


    }

    @Test
    void test_both_checkPlacement(){
        Placement p1= new Placement(new Coordinate(1,2),'V');
        Placement p2= new Placement(new Coordinate(5,2),'H');
        Placement p3= new Placement(new Coordinate(0,0),'V');
        Placement p4= new Placement(new Coordinate(1,2),'H');
        Placement p5= new Placement(new Coordinate(0,3),'H');

        V1ShipFactory v = new V1ShipFactory();
        //put s1,s2 to the board
        Ship<Character> s1 = v.makeDestroyer(p1);
        Ship<Character> s2 = v.makeSubmarine(p2);
        //No collision with other ship
        Ship<Character> s3 = v.makeSubmarine(p3);
        //With collision with p1
        Ship<Character> s4 = v.makeSubmarine(p4);
        Ship<Character> s5 = v.makeCarrier(p5);

        PlacementRuleChecker<Character> no = new NoCollisionRuleChecker<>(new InBoundsRuleChecker<Character>(null));
        Board<Character> b = new BattleShipBoard<Character>(5, 6, no,'X');
        b.tryAddShip(s1);
        b.tryAddShip(s2);
        assertEquals (null,no.checkPlacement(s3,b));
        assertEquals ("That placement is invalid: the ship overlaps another ship.\n",no.checkPlacement(s4,b));
        assertEquals ("That placement is invalid: the ship goes off the right of the board.\n",no.checkPlacement(s5,b));

    }
}
