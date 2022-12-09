package edu.duke.yl730.battleship;


//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;

public class BattleShipBoardTest<T> {
    @Test
    public void test_invalid_dimensions() {
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0,'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20,'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5,'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20,'X'));
    }
    @Test
    public void test_width_and_height() {
        Board<Character> b1 = new BattleShipBoard<Character>(10, 20,'X');
        assertEquals(10, b1.getWidth());
        assertEquals(20, b1.getHeight());
    }

    @Test
    public void  test_tryAddShip(){
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

        //PlacementRuleChecker<Character> no = new NoCollisionRuleChecker<>(new InBoundsRuleChecker<Character>(null));
        Board<Character> b = new BattleShipBoard<Character>(5, 6,'X');
        assertEquals (null,b.tryAddShip(s1));
        assertEquals (null,b.tryAddShip(s2));

        assertEquals (null,b.tryAddShip(s3));
        assertEquals ("That placement is invalid: the ship overlaps another ship.\n",b.tryAddShip(s4));
        assertEquals ("That placement is invalid: the ship goes off the right of the board.\n",b.tryAddShip(s5));
    }

    @Test
    public void  test_whatIsAt_and_tryAddShip(){
        BattleShipBoard<Character> b1 = new BattleShipBoard(10, 20,'X');

        Character[][] expected = new Character[20][10];
        checkWhatIsAtBoard(b1,expected);
        Coordinate c1 = new Coordinate(2, 3);
        RectangleShip<Character>  s1 = new RectangleShip<Character> (c1,'s', '*');
        Coordinate c2 = new Coordinate(5, 5);
        RectangleShip<Character>  s2 = new RectangleShip<Character> (c2,'s', '*');
        b1.tryAddShip(s2);
        b1.tryAddShip(s1);
        assertEquals(s1,b1.whatShipAt(c1));
        assertEquals(null,b1.whatShipAt(new Coordinate(0,0)));

        expected[2][3] = 's';
        expected[5][5] = 's';
        checkWhatIsAtBoard(b1,expected);
    }
    @Test
    public void  test_fireAt(){
        BattleShipBoard<Character> b1 = new BattleShipBoard(10, 20,'X');
        Coordinate c1 = new Coordinate(2, 3);
        RectangleShip<Character>  s1 = new RectangleShip<Character> (c1,'s', '*');
        Coordinate c2 = new Coordinate(5, 5);
        Coordinate c3 = new Coordinate(0, 0);

        RectangleShip<Character>  s2 = new RectangleShip<Character> (c2,'s', '*');
        b1.tryAddShip(s2);
        b1.tryAddShip(s1);
        assertSame(s1,b1.fireAt(c1));
        assertEquals(null,b1.whatIsAt(c3,false));
        b1.fireAt(c3);
        assertEquals('X',b1.whatIsAt(c3,false));

        assertSame(null,b1.fireAt(c3));
        assert(s1.isSunk());
        assert (!s2.isSunk());
    }

    @Test
    public void  test_sonarScan(){
        BattleShipBoard<Character> b1 = new BattleShipBoard(10, 10,'X');
        V2ShipFactory v = new V2ShipFactory();
        Coordinate c1 = new Coordinate(2, 3);
        Coordinate c2 = new Coordinate(5, 5);
        Coordinate c3 = new Coordinate(0, 0);
        Ship<Character> sub = v.makeSubmarine(new Placement(c3,'H'));
        Ship<Character> carrier = v.makeCarrier(new Placement(c2,'U'));
        Ship<Character> Battleship = v.makeBattleship(new Placement(c1,'U'));
        b1.tryAddShip(sub);
        b1.tryAddShip(carrier);
        b1.tryAddShip(Battleship);
        String actual = b1.sonarScan(new Coordinate(4,4));
        String expected = "Submarines occupy 0 squares\n"+
                "Destroyers occupy 0 squares\n"+
                "Battleships occupy 4 squares\n"+
                "Carriers occupy 2 squares\n";
        assertEquals(expected,actual);
    }

    @Test
    public void  test_sonarScan1(){
        BattleShipBoard<Character> board = new BattleShipBoard(10, 10,'X');
        V2ShipFactory v = new V2ShipFactory();
        Ship<Character> s1 = v.makeSubmarine(new Placement(new Coordinate(0,0),'V'));
        Ship<Character> s2 = v.makeSubmarine(new Placement(new Coordinate(0,1),'V'));
        Ship<Character> d1 = v.makeDestroyer(new Placement(new Coordinate(0,2),'V'));
        Ship<Character> d2 = v.makeDestroyer(new Placement(new Coordinate(0,3),'V'));
        Ship<Character> d3 = v.makeDestroyer(new Placement(new Coordinate(0,4),'H'));
        Ship<Character> b1 = v.makeBattleship(new Placement(new Coordinate(1,4),'D'));
        Ship<Character> b2 = v.makeBattleship(new Placement(new Coordinate(3,0),'L'));
        board.tryAddShip(s1);
        board.tryAddShip(s2);
        board.tryAddShip(d1);
        board.tryAddShip(d2);
        board.tryAddShip(d3);
        board.tryAddShip(b1);
        board.tryAddShip(b2);
        //System.out.println(board.sonarScan(new Coordinate(2,3)));
    }



    private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected){
        for(int r = 0; r < expected.length; r++){
            for(int c = 0; c < expected[0].length; c++){
                Coordinate c1 = new Coordinate(r, c);
                assertEquals(expected[r][c],b.whatIsAtForSelf(c1));
            }
        }
    }

    @Test
    public void test_display_2by2() {
        BattleShipBoard<Character> b1 = new BattleShipBoard(2, 2,'X');
        Coordinate c1 = new Coordinate(0, 0);
        RectangleShip<Character>  s1 = new RectangleShip<Character> (c1,'s', '*');
        Coordinate c2 = new Coordinate(1, 1);
        RectangleShip<Character>  s2 = new RectangleShip<Character> (c2,'s', '*');
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
        RectangleShip<Character>  s1 = new RectangleShip<Character> (c1,'s', '*');
        Coordinate c2 = new Coordinate(1, 2);
        RectangleShip<Character>  s2 = new RectangleShip<Character> (c2,'s', '*');
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
        RectangleShip<Character>  s1 = new RectangleShip<Character> (c1,'s', '*');
        Coordinate c2 = new Coordinate(4, 1);
        RectangleShip<Character>  s2 = new RectangleShip<Character> (c2,'s', '*');
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

    private void BoardHelper(Board b, String expectedHeader, String body){
        BoardTextView view = new BoardTextView(b);
        assertEquals(expectedHeader, view.makeHeader());
        String expected = expectedHeader + body + expectedHeader;
        assertEquals(expected, view.displayMyOwnBoard());
    }



}








