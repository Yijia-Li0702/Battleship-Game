package edu.duke.yl730.battleship;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ComputerPlayerTest {

    @Test
    void playOneTurn_V2() throws IOException{
        String input = "B2V\nf1H\na4v\na0v\n";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ComputerPlayer c = createTextPlayer(10,20,input,bytes);
        ComputerPlayer c2 = createTextPlayer(10,20,input,bytes);
        App app = new App(c,c2);
        //c.doPlacementPhase();
        c.doPlacementPhase();
        c2.doPlacementPhase();
        app.doAttackingPhase(c2.theBoard, c2.view);
//        c.playOneTurn_V2(c2.theBoard,c2.view);
//        c.playOneTurn_V2(c2.theBoard,c2.view);
//        c.playOneTurn_V2(c2.theBoard,c2.view);
//        c.playOneTurn_V2(c2.theBoard,c2.view);
        c.doOneAttack(c2.theBoard);
        bytes.reset();
        c.doOneMove();
        assertEquals("Player A used a special action\n"+"\n", bytes.toString());
        bytes.reset();
        c.doOneSonarScan(c2.theBoard);
        //assertEquals("Player A used a special action\n", bytes);
        bytes.reset();
    }

    private ComputerPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {

        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<Character>(w, h,'X');
        V2ShipFactory shipFactory = new V2ShipFactory();
        return new ComputerPlayer("A", board, input, output, shipFactory);
    }
}
