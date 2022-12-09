/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.yl730.battleship;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
    void test_main_1() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        InputStream input = getClass().getClassLoader().getResourceAsStream("input4.txt");
        assertNotNull(input);
        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output4.txt");
        assertNotNull(expectedStream);
        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;
        try {
            System.setIn(input);
            System.setOut(out);
            App.main(new String[0]);
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }
        String expected = new String(expectedStream.readAllBytes());
        String actual = bytes.toString();
        assertEquals(expected, actual);
    }

    @Test
    @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
    void test_main_2() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        InputStream input = getClass().getClassLoader().getResourceAsStream("input5.txt");
        assertNotNull(input);
        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output5.txt");
        assertNotNull(expectedStream);
        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;
        try {
            System.setIn(input);
            System.setOut(out);
            App.main(new String[0]);
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }
        String expected = new String(expectedStream.readAllBytes());
        String actual = bytes.toString();
        assertEquals(expected, actual);
    }

//    @Test
//    public void doAttackingPhase() throws IOException{
//        String input = "B2V\nf1H\na4v\na0v\n";
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        ComputerPlayer c = createTextPlayer(10, 20, input, bytes);
//        ComputerPlayer c2 = createTextPlayer(10, 20, input, bytes);
//        App app = new App(c,c2);
//        app.doAttackingPhase(c2.theBoard, c2.view);
//    }


}

//    @Test
//    void test_read_placement() throws IOException{
//        String prompt = " Please enter a location for a ship:";
//
//        StringReader sr = new StringReader("B2V\nC8H\na4v\n");
//       // StringReader sr = new StringReader("B2V\nf1H\na4v\n");
//
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream ps = new PrintStream(bytes, true);
//        Board<Character> b = new BattleShipBoard<Character>(10, 20);
//        App app = new App(b, sr, ps);
//
//        Placement[] expected = new Placement[3];
//        expected[0] = new Placement(new Coordinate(1, 2), 'V');
//        expected[1] = new Placement(new Coordinate(2, 8), 'H');
//        expected[2] = new Placement(new Coordinate(0, 4), 'V');
//
//        for (int i = 0; i < expected.length; i++) {
//            Placement p = app.readPlacement(prompt);
//            assertEquals(p, expected[i]); //did we get the right Placement back
//            assertEquals(prompt + "\n", bytes.toString()); //should have printed prompt and newline
//            bytes.reset(); //clear out bytes for next time around
//        }
//
//    }
//    @Test
//    void test_doOnePlacementO() throws IOException{
//        String prompt = " Please enter a location for a ship:";

//        //StringReader sr = new StringReader("B2V\nC4H\na4v\n");
//        StringReader sr = new StringReader("B2V\nf1H\na4v\n");
//
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream ps = new PrintStream(bytes, true);
//        Board<Character> b = new BattleShipBoard<Character>(5, 6);
//        App app = new App(b, sr, ps);
//
//        //for (int i = 0; i < 2; i++) {
//        app.doOnePlacement();
//        String s1 = prompt + "\n" +
//                "  0|1|2|3|4\n" +
//                "A  | | | |  A\n"+
//                "B  | |d| |  B\n"+
//                "C  | |d| |  C\n"+
//                "D  | |d| |  D\n"+
//                "E  | | | |  E\n"+
//                "F  | | | |  F\n"+
//                "  0|1|2|3|4\n\n";
//        assertEquals(s1, bytes.toString());
//        bytes.reset();
//        app.doOnePlacement();
//        String s2 = prompt + "\n" +
//                "  0|1|2|3|4\n" +
//                "A  | | | |  A\n"+
//                "B  | |d| |  B\n"+
//                "C  | |d| |  C\n"+
//                "D  | |d| |  D\n"+
//                "E  | | | |  E\n"+
//                "F  |d|d|d|  F\n"+
//                "  0|1|2|3|4\n\n";
//        assertEquals(s2, bytes.toString());
//        bytes.reset();
//
//        app.doOnePlacement();
//        String s3 = prompt + "\n" +
//                "  0|1|2|3|4\n" +
//                "A  | | | |d A\n"+
//                "B  | |d| |d B\n"+
//                "C  | |d| |d C\n"+
//                "D  | |d| |  D\n"+
//                "E  | | | |  E\n"+
//                "F  |d|d|d|  F\n"+
//                "  0|1|2|3|4\n\n";
//        assertEquals(s3, bytes.toString());
//        bytes.reset();
//    }
