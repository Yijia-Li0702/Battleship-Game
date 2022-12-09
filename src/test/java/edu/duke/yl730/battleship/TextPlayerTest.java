package edu.duke.yl730.battleship;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class TextPlayerTest {

    @Test
    void test_read_placement() throws IOException {
        String prompt = "Player A where do you want to place a Destroyer?";
//        TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr), ps, new V1ShipFactory());
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);


        Placement[] expected = new Placement[3];
        expected[0] = new Placement(new Coordinate(1, 2), 'V');
        expected[1] = new Placement(new Coordinate(2, 8), 'H');
        expected[2] = new Placement(new Coordinate(0, 4), 'V');

        for (int i = 0; i < expected.length; i++) {
            Placement p = player.readPlacement(prompt);
            assertEquals(p, expected[i]); //did we get the right Placement back
            assertEquals(prompt + "\n", bytes.toString()); //should have printed prompt and newline
            bytes.reset(); //clear out bytes for next time around
        }
        //Placement p = player.readPlacement(prompt);
        assertThrows(EOFException.class, ()->player.readPlacement(prompt));
        bytes.reset();

    }
    @Test
    public void test_playOneTurn() throws IOException{
        String input = "B2V\nf1H\na4v\na0v\na0\nb2";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer c = createTextPlayer(10, 20, input, bytes);
        TextPlayer c2 = createTextPlayer(10, 20, input, bytes);
        c.playOneTurn(c2.theBoard, c2.view);

    }


    @Test
    void test_doOnePlacement() throws IOException{
        String prompt = "Player A where do you want to place a Destroyer?";

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(5, 6, "B2V\nf1H\na4v\naov\n", bytes);
        AbstractShipFactory<Character> shipFactory = new V1ShipFactory();

        player.doOnePlacement("Destroyer", (p) -> player.shipFactory.makeDestroyer(p));
        String s1 = prompt + "\n" +
                "  0|1|2|3|4\n" +
                "A  | | | |  A\n"+
                "B  | |d| |  B\n"+
                "C  | |d| |  C\n"+
                "D  | |d| |  D\n"+
                "E  | | | |  E\n"+
                "F  | | | |  F\n"+
                "  0|1|2|3|4\n";
        assertEquals(s1, bytes.toString());
        bytes.reset();
        player.doOnePlacement("Destroyer", (p) -> shipFactory.makeDestroyer(p));
        String s2 = prompt + "\n" +
                "  0|1|2|3|4\n" +
                "A  | | | |  A\n"+
                "B  | |d| |  B\n"+
                "C  | |d| |  C\n"+
                "D  | |d| |  D\n"+
                "E  | | | |  E\n"+
                "F  |d|d|d|  F\n"+
                "  0|1|2|3|4\n";
        assertEquals(s2, bytes.toString());
        bytes.reset();
        player.doOnePlacement("Destroyer", (p) -> shipFactory.makeDestroyer(p));
        String s3 = prompt + "\n" +
                "  0|1|2|3|4\n" +
                "A  | | | |d A\n"+
                "B  | |d| |d B\n"+
                "C  | |d| |d C\n"+
                "D  | |d| |  D\n"+
                "E  | | | |  E\n"+
                "F  |d|d|d|  F\n"+
                "  0|1|2|3|4\n";
        assertEquals(s3, bytes.toString());
        bytes.reset();

//        player.doOnePlacement("Destroyer", (p) -> shipFactory.makeDestroyer(p));
//        String s4 = "Your placement is illegal, please try again!";
//        assertEquals(s4, bytes.toString());
//        bytes.reset();
    }


    @Test
    public void test_ifLose() throws IOException{

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(5, 6, "B2V\n", bytes);
        AbstractShipFactory<Character> shipFactory = new V1ShipFactory();

        player.doOnePlacement("Destroyer", (p) -> player.shipFactory.makeDestroyer(p));
        player.theBoard.fireAt(new Coordinate(1,2));
        player.theBoard.fireAt(new Coordinate(2,2));
        assert (!player.ifLose());
        player.theBoard.fireAt(new Coordinate(3,2));
        assert (player.ifLose());


    }


//    private TextPlayer createTextPlayer(int w, int h, String inputData, ByteArrayOutputStream bytes) {
    private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {

        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<Character>(w, h,'X');
        V1ShipFactory shipFactory = new V1ShipFactory();
        return new TextPlayer("A", board, input, output, shipFactory);
    }

}
