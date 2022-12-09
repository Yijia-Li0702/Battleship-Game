package edu.duke.yl730.battleship;

import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer {
    //private final Board<Character> theBoard;
    final Board<Character> theBoard;
    final BoardTextView view;
    final BufferedReader inputReader;
    final PrintStream out;
    final AbstractShipFactory<Character> shipFactory;
    final String name;
    final ArrayList<String> shipsToPlace;
    final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
    public int timesMove;
    public int timesScan;


    public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
                      AbstractShipFactory<Character> shipFactory) {
        this.theBoard = theBoard;
        this.view = new BoardTextView(theBoard);
        //this.inputReader = new BufferedReader(inputSource);
        this.inputReader = inputSource;
        this.out = out;
        this.shipFactory = shipFactory;
        this.name = name;
        this.shipsToPlace = new ArrayList<>();
        this.shipCreationFns = new HashMap<>();
        this.setupShipCreationList();
        this.setupShipCreationMap();
        this.timesMove = 3;
        this.timesScan = 3;
    }

    protected void setupShipCreationMap() {
        shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
        shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
        shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
        shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    }

    protected void setupShipCreationList() {
        shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
        shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
        shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
        shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
    }

    /**
     * This function is to interact with user, read input from keyboard
     *
     * @param prompt the promt massege shows on th screen
     * @throws IOException if the input is illegal.
     */
    public Placement readPlacement(String prompt) throws IOException {
        out.println(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException("inputReader is null!");
        }

        return new Placement(s);
    }

    /**
     * Constructs a BoardView, given the board that it will display.
     *
     * @param shipName the name of placed ship
     * @param createFn which kind of ship will be placed
     * @throws IOException if the input is illegal.
     */
    public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
        while (true) {
            try {
                Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
                Ship<Character> s = createFn.apply(p);
                String error = theBoard.tryAddShip(s);
                if (error != null) {
                    out.print(error);
                    continue;
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                out.print("Your placement is illegal, please try again!\n");
            }
        }
        out.print(view.displayMyOwnBoard());
        return;
    }

    /**
     * Place all ships on the board
     *
     * @throws IOException if the input is illegal.
     */
    public void doPlacementPhase() throws IOException {
        out.println(view.displayMyOwnBoard());
//        out.println("Player "+name+": you are going to place the following ships (which are all rectangular). " +
//                "For each ship, type the coordinate of the upper left side of the ship, " +
//                "followed by either H (for horizontal) or V (for vertical).  For example M4H would place a ship horizontally starting at " +
//                "M4 and going to the right.  You have\n 2 \"Submarines\" ships that are 1x2\n 3 \"Destroyers\" that are 1x3\n 3 \"Battleships\" that are 1x4\n 2 \"Carriers\" that are 1x6");
        out.println("Player " + name + ": you are going to place the following ships. " +
                "Submarine and Destroyer are rectangular with two orientations H (for horizontal) " +
                "and V (for vertical). BattleShip and Carrier have four orientations, U (for up)," +
                "R (for right), D (for down), L (for left) " +
                "For each ship, type the coordinate of the upper left side of the ship, " +
                "followed its orientation.  For example M4H would place a ship horizontally starting at " +
                "M4 and going to the right.  You have\n 2 \"Submarines\" ships that are 1x2\n 3 \"Destroyers\" that are 1x3\n 3 \"Battleships\" that are 1x4\n 2 \"Carriers\" that are 1x6");
        for (int i = 0; i < shipsToPlace.size(); i++) {
            String name = shipsToPlace.get(i);
            doOnePlacement(name, shipCreationFns.get(name));
        }
    }

    /**
     * Check if the player is lost, if so, return false, else, return true
     */
    public boolean ifLose() {
        return theBoard.ifAllSunk();
    }

    /**
     * Play one turn for version 1
     *
     * @param enemyBoard enemy's Board
     * @param enemyView  display enemy's board from my view
     * @throws IllegalArgumentException if the input is illegal.
     */
    public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView) throws IOException {
        out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean", "Your enemy's ocean"));
        doOneAttack(enemyBoard);
    }

    /**
     * Do one time attack, fire at enemy's board
     *
     * @param enemyBoard
     * @throws IOException
     */
    public void doOneAttack(Board<Character> enemyBoard) throws IOException {
        out.println("Player " + name + " where do you want to fire at?");
        while (true) {
            try {
                String fireAt = inputReader.readLine();
                Coordinate target = new Coordinate(fireAt);
                Ship<Character> s = enemyBoard.fireAt(target);
                if (s == null) {
                    out.println("You missed!");
                } else {
                    out.println("You hit a " + s.getName() + "!");
                }
                break;
                //out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean","Player B's ocean"));
            } catch (IllegalArgumentException e) {
                out.println("Coordinate is illegal, Please try again!\n");
            }

        }
    }
}
//
//    /**
//     * Do one turn for a version 2 game, including three options for fire, move and sonar scan
//     * @param enemyBoard enemy's board
//     * @param enemyView enemy's view
//     * @throws IOException
//     */
//    public void playOneTurn_V2(Board<Character> enemyBoard, BoardTextView enemyView) throws IOException {
//        out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean", "Your enemy's ocean"));
//        out.println(promptAction(this));
//        while(true){
//            try {
//                String option = inputReader.readLine();
//                if(option == null || option.length() != 1) {
//                    out.println("Your input is invalid, please try again!\n");
//                    continue;
//                }
//                char o = option.charAt(0);
//                if (o == 'F') {
//                    doOneAttack(enemyBoard);
//                    break;
//                } else if (o == 'M') {
//                    if(timesMove == 0){
//                        out.println("You can't move ship any more, please try again!\n");
//                        continue;
//                    }
//                    doOneMove();
//                    timesMove--;
//                    break;
//                } else if (o == 'S') {
//                    if(timesScan == 0){
//                        out.println("You can't do sonar scan any more, please try again!\n");
//                        continue;
//                    }
//                    doOneSonarScan(enemyBoard);
//                    timesScan--;
//                    break;
//                } else {
//                    out.println("Your input is invalid, please try again!\n");
//                }
//            } catch (IllegalArgumentException e) {
//                out.println("Your input is illegal, Please try again!\n");
//            }
//        }
//    }
//
//    /**
//     * The information displayed on the screen
//     * @param player player taking action at this turn
//     * @return String to be displayed
//     */
//    private String promptAction(TextPlayer player){
//        StringBuilder s = new StringBuilder();
//        s.append("Possible actions for player").append(player.name).
//                append(":\n\n").append(" F Fire at a square\n").
//                append(" M Move a ship to another square (")
//                .append(player.timesMove).append(" remaining)\n")
//                .append(" S Sonar scan (").append(player.timesScan)
//                .append(" remaining)\n\n").append(player.name).append(", what would you like to do?\n");
//        return s.toString();
//    }
//
//    /**
//     * Move required ship, given the ship to be removed and new placement
//     *
//     * @throws IOException if the input is illegal.
//     */
//    public void doOneMove() throws IOException {
//        Ship<Character> old_ship = null;
//        while (true) {
//            out.println("Player " + name + " which ship do you want to move, please enter one coordinate\n");
//            try {
//                String msg = inputReader.readLine();
//                Coordinate target = new Coordinate(msg);
//                if (target.getRow() > theBoard.getHeight() || target.getColumn() > theBoard.getWidth()
//                        || target.getColumn() < 0 || target.getRow() < 0) {
//                    out.println("Invalid Coordinate, please try again!\n");
//                    continue;
//                }
//                old_ship = theBoard.whatShipAt(target);
//                if (old_ship == null) {
//                    out.println("there is no ship at this coordinate, Please try again!\n");
//                    continue;
//                }
//                break;
//            } catch (Exception e) {
//                out.println("Invalid input, please try again!\n");
//            }
//        }
//        while (true) {
//            try {
//                Placement new_place = readPlacement("Player " + name + " where do you want to put your new ship?\n");
//                Ship<Character> new_ship = ((V2ShipFactory) shipFactory).moveShip(old_ship, new_place);
//                ((BattleShipBoard<Character>) theBoard).myShips.remove(old_ship);
//                String error = theBoard.tryAddShip(new_ship);
//                if (error != null) {
//                    out.print(error);
//                    continue;
//                } else {
//                    break;
//                }
//            } catch (Exception e) {
//                out.println("Invalid input, please try again!\n");
//            }
//
//        }
//    }
//
//
//    /**
//     * Do one time of sonar scan, given the central point of the scan
//     *
//     * @throws IOException if the input is illegal.
//     */
//    public void doOneSonarScan(Board<Character> enemyBoard) throws IOException{
//        while (true) {
//            out.println("Player " + name + " where do you want to sonar scan?");
//            try {
//                String msg = inputReader.readLine();
//                Coordinate target = new Coordinate(msg);
//                if (target.getRow() > enemyBoard.getHeight() || target.getColumn() > enemyBoard.getWidth()
//                        || target.getColumn() < 0 || target.getRow() < 0) {
//                    out.println("Invalid Coordinate, please try again!\n");
//                    continue;
//                }
//                String ans = enemyBoard.sonarScan(target);
//                out.println(ans);
//                break;
//            }
//            catch (Exception e){
//                out.println("Invalid Coordinate, please try again!\n");
//            }
//        }
//    }
//}


//    public void doOneMove() throws IOException{
//        while (true) {
//            out.println("Player " + name + " which ship do you want to move, please enter one coordinate\n");
//            try {
//                String msg = inputReader.readLine();
//                Coordinate target = new Coordinate(msg);
//                if (target.getRow() > theBoard.getHeight() || target.getColumn() > theBoard.getWidth()
//                        || target.getColumn() < 0 || target.getRow() < 0) {
//                    out.println("Invalid Coordinate, please try again!\n");
//                    continue;
//                }
//                Ship<Character> old_ship =theBoard.whatShipAt(target);
//                if(old_ship == null){
//                    out.println("there is no ship at this coordinate, Please try again!\n");
//                    continue;
//                }
//                Placement new_place = readPlacement("Player " + name + " where do you want to put your new ship?\n");
//                Ship<Character> new_ship = ((V2ShipFactory)shipFactory).moveShip(old_ship,new_place);
//                ((BattleShipBoard<Character>)theBoard).myShips.remove(old_ship);
//                String error = theBoard.tryAddShip(new_ship);
//                if (error != null) {
//                    out.print(error);
//                    continue;
//                } else {
//                    break;
//                }
//            }
//            catch (Exception e){
//                out.println("Invalid input, please try again!\n");
//            }
//        }
//    }

