package edu.duke.yl730.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {

    @Test
    public void test_display_empty_2by2() {
        String body = "A  |  A\n"+
                "B  |  B\n";
        String expectedHeader =  "  0|1\n";
        emptyBoardHelper(2,2,expectedHeader, body);
    }
    @Test
    public void test_display_empty_3by2(){
        String body = "A  | |  A\n"+
                "B  | |  B\n";
        String expectedHeader =  "  0|1|2\n";
        emptyBoardHelper(3,2,expectedHeader, body);
    }
    @Test
    public void test_display_empty_3by5(){
        String body = "A  | |  A\n"+
                "B  | |  B\n"+
                "C  | |  C\n"+
                "D  | |  D\n"+
                "E  | |  E\n";
        String expectedHeader =  "  0|1|2\n";
        emptyBoardHelper(3,5,expectedHeader, body);
    }
    private void emptyBoardHelper(int w, int h, String expectedHeader, String body){
        Board<Character> b1 = new BattleShipBoard<Character>(w, h,'X');
        BoardTextView view = new BoardTextView(b1);
        assertEquals(expectedHeader, view.makeHeader());
        String expected = expectedHeader + body + expectedHeader;
        assertEquals(expected, view.displayMyOwnBoard());
    }


    @Test
    public void test_invalid_board_size() {
        Board<Character> wideBoard = new BattleShipBoard<Character>(11,20,'X');
        Board<Character> tallBoard = new BattleShipBoard<Character>(10,27,'X');
        //you should write two assertThrows here
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
    }
    @Test
    public void test_display_2by2() {
        BattleShipBoard<Character> b1 = new BattleShipBoard(2, 2,'X');
        Coordinate c1 = new Coordinate(0, 0);
        RectangleShip<Character> s1 = new RectangleShip<Character>(c1, 's', '*');
        Coordinate c2 = new Coordinate(1, 1);
        RectangleShip<Character> s2 = new RectangleShip<Character>(c2, 's', '*');
        b1.myShips.add(s2);
        b1.myShips.add(s1);
        String body = "A s|  A\n"+
                "B  |s B\n";
        String expectedHeader =  "  0|1\n";
        BoardHelper(b1,expectedHeader, body);
    }
    @Test
    public void test_display_3by2(){
        BattleShipBoard<Character> b1 = new BattleShipBoard(3, 2,'X');
        Coordinate c1 = new Coordinate(0, 0);
        RectangleShip<Character> s1 = new RectangleShip<Character>(c1, 's', '*');
        Coordinate c2 = new Coordinate(1, 2);
        RectangleShip<Character> s2 = new RectangleShip<Character>(c2, 's', '*');
        b1.myShips.add(s2);
        b1.myShips.add(s1);
        String body = "A s| |  A\n"+
                "B  | |s B\n";
        String expectedHeader =  "  0|1|2\n";
        BoardHelper(b1,expectedHeader, body);
    }
    @Test
    public void test_display_3by5(){
        BattleShipBoard<Character> b1 = new BattleShipBoard(3, 5,'X');
        Coordinate c1 = new Coordinate(0, 0);
        RectangleShip<Character> s1 = new RectangleShip<Character>(c1, 's', '*');
        Coordinate c2 = new Coordinate(4, 1);
        RectangleShip<Character> s2 = new RectangleShip<Character>(c2, 's', '*');
        b1.myShips.add(s2);
        b1.myShips.add(s1);
        String body = "A s| |  A\n"+
                "B  | |  B\n"+
                "C  | |  C\n"+
                "D  | |  D\n"+
                "E  |s|  E\n";
        String expectedHeader =  "  0|1|2\n";
        BoardHelper(b1,expectedHeader, body);
    }
    @Test
    public void test_displayMyBoardWithEnemyNextToIt(){
        BattleShipBoard<Character> b1 = new BattleShipBoard(4, 2,'X');
        V1ShipFactory v = new V1ShipFactory();
        Placement p = new Placement(new Coordinate(0,0),'H');
        Ship<Character> s1 = v.makeSubmarine(p);
        Ship<Character> s2 = v.makeDestroyer(new Placement(new Coordinate(1,1),'H'));
        b1.myShips.add(s2);
        b1.myShips.add(s1);
        BoardTextView view = new BoardTextView(b1);
        String myHeader = "Your ocean";
        String enemyHeader = "Player B's ocean";
        b1.fireAt(new Coordinate(0,0));
        view.displayMyBoardWithEnemyNextToIt(view, myHeader, enemyHeader);

        String Header = "     "+myHeader+"                      "+enemyHeader+"\n";
        String s =           "  0|1|2|3                0|1|2|3\n" +
                             "A *|s| |  A            A s| | |  A\n"+
                             "B  |d|d|d B            B  | | |  B\n"+
                             "  0|1|2|3                0|1|2|3\n";
        String expected = Header+s;
        assertEquals(expected, view.displayMyBoardWithEnemyNextToIt(view,myHeader,enemyHeader));
    }
    @Test
    public void test_displayMyOwnBoard(){
        BattleShipBoard<Character> b1 = new BattleShipBoard(3, 5,'X');
        Coordinate c1 = new Coordinate(0, 0);
        RectangleShip<Character> s1 = new RectangleShip<Character>(c1, 's', '*');
        Coordinate c2 = new Coordinate(4, 1);
        Coordinate c3 = new Coordinate(3,1);
        RectangleShip<Character> s2 = new RectangleShip<Character>(c2, 's', '*');
        b1.myShips.add(s2);
        b1.myShips.add(s1);
        b1.fireAt(c1);
        b1.fireAt(c3);
        String body = "A s| |  A\n"+
                      "B  | |  B\n"+
                      "C  | |  C\n"+
                      "D  |X|  D\n"+
                      "E  | |  E\n";
        String expectedHeader =  "  0|1|2\n";
        BoardTextView view = new BoardTextView(b1);
        assertEquals(expectedHeader, view.makeHeader());
        String expected = expectedHeader + body + expectedHeader;
        assertEquals(expected, view.displayEnemyBoard());

    }


    private void BoardHelper(Board<Character> b, String expectedHeader, String body){
        BoardTextView view = new BoardTextView(b);
        assertEquals(expectedHeader, view.makeHeader());
        String expected = expectedHeader + body + expectedHeader;
        assertEquals(expected, view.displayMyOwnBoard());
    }
//        String Header = "     "+myHeader+"                      "+enemyHeader+"\n";
//        String s ="  0|1|2|3                             0|1|2|3\n" +
//                  "A *|s| |  A                         A s| | |  A\n"+
//                  "B  |d|d|d B                         B  | | |  B\n"+
//                  "  0|1|2|3                             0|1|2|3\n";
//        s = "                      " + '            '


}











