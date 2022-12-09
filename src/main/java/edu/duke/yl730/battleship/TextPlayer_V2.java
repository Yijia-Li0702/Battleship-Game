package edu.duke.yl730.battleship;

import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer_V2 extends TextPlayer {


    public TextPlayer_V2(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
                         AbstractShipFactory<Character> shipFactory) {
        super(name,theBoard,inputSource,out,shipFactory);
    }

    /**
     * Do one time attack, fire at enemy's board
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

    /**
     * Do one turn for a version 2 game, including three options for fire, move and sonar scan
     * @param enemyBoard enemy's board
     * @param enemyView enemy's view
     * @throws IOException
     */
    @Override
    public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView) throws IOException {
        out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean", "Your enemy's ocean"));
        out.println(promptAction(this));
        while(true){
            try {
                String option = inputReader.readLine();
                if(option == null || option.length() != 1) {
                    out.println("Your input is invalid, please try again!\n");
                    continue;
                }
                char o = option.charAt(0);
                if (o == 'F') {
                    doOneAttack(enemyBoard);
                    break;
                } else if (o == 'M') {
                    if(timesMove == 0){
                        out.println("You can't move ship any more, please try again!\n");
                        continue;
                    }
                    doOneMove();
                    timesMove--;
                    break;
                } else if (o == 'S') {
                    if(timesScan == 0){
                        out.println("You can't do sonar scan any more, please try again!\n");
                        continue;
                    }
                    doOneSonarScan(enemyBoard);
                    timesScan--;
                    break;
                } else {
                    out.println("Your input is invalid, please try again!\n");
                }
            } catch (IllegalArgumentException e) {
                out.println("Your input is illegal, Please try again!\n");
            }
        }
    }

    /**
     * The information displayed on the screen
     * @param player player taking action at this turn
     * @return String to be displayed
     */
    private String promptAction(TextPlayer player){
        StringBuilder s = new StringBuilder();
        s.append("Possible actions for player").append(player.name).
                append(":\n\n").append(" F Fire at a square\n").
                append(" M Move a ship to another square (")
                .append(player.timesMove).append(" remaining)\n")
                .append(" S Sonar scan (").append(player.timesScan)
                .append(" remaining)\n\n").append(player.name).append(", what would you like to do?\n");
        return s.toString();
    }

    /**
     * Move required ship, given the ship to be removed and new placement
     *
     * @throws IOException if the input is illegal.
     */
    public void doOneMove() throws IOException {
        Ship<Character> old_ship = null;
        while (true) {
            out.println("Player " + name + " which ship do you want to move, please enter one coordinate\n");
            try {
                String msg = inputReader.readLine();
                Coordinate target = new Coordinate(msg);
                if (target.getRow() > theBoard.getHeight() || target.getColumn() > theBoard.getWidth()
                        || target.getColumn() < 0 || target.getRow() < 0) {
                    out.println("Invalid Coordinate, please try again!\n");
                    continue;
                }
                old_ship = theBoard.whatShipAt(target);
                if (old_ship == null) {
                    out.println("there is no ship at this coordinate, Please try again!\n");
                    continue;
                }
                break;
            } catch (Exception e) {
                out.println("Invalid input, please try again!\n");
            }
        }
        while (true) {
            try {
                Placement new_place = readPlacement("Player " + name + " where do you want to put your new ship?\n");
                Ship<Character> new_ship = ((V2ShipFactory) shipFactory).moveShip(old_ship, new_place);
                ((BattleShipBoard<Character>) theBoard).myShips.remove(old_ship);
                String error = theBoard.tryAddShip(new_ship);
                if (error != null) {
                    out.print(error);
                    continue;
                } else {
                    break;
                }
            } catch (Exception e) {
                out.println("Invalid input, please try again!\n");
            }

        }
    }


    /**
     * Do one time of sonar scan, given the central point of the scan
     *
     * @throws IOException if the input is illegal.
     */
    public void doOneSonarScan(Board<Character> enemyBoard) throws IOException{
        while (true) {
            out.println("Player " + name + " where do you want to sonar scan?");
            try {
                String msg = inputReader.readLine();
                Coordinate target = new Coordinate(msg);
                if (target.getRow() > enemyBoard.getHeight() || target.getColumn() > enemyBoard.getWidth()
                        || target.getColumn() < 0 || target.getRow() < 0) {
                    out.println("Invalid Coordinate, please try again!\n");
                    continue;
                }
                String ans = enemyBoard.sonarScan(target);
                out.println(ans);
                break;
            }
            catch (Exception e){
                out.println("Invalid Coordinate, please try again!\n");
            }
        }
    }
}


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

