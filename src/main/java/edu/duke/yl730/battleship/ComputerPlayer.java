package edu.duke.yl730.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.function.Function;

public class ComputerPlayer extends TextPlayer{
    public ComputerPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
                          AbstractShipFactory<Character> shipFactory) {
        super(name,theBoard,inputSource,out,shipFactory);
    }
    /**
     * This function is for computer player to play one turn game.
     * The action for each turn is random
     */
    @Override
    public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView) {
        while(true){
            char act = randomAction();

                if (act == 'F') {
                    doOneAttack(enemyBoard);
                    break;
                } else if (act == 'M') {
                    if(timesMove == 0){ ;
                        continue;
                    }
                    doOneMove();
                    timesMove--;
                    break;
                } else if (act == 'S') {
                    if(timesScan == 0){
                        continue;
                    }
                    doOneSonarScan(enemyBoard);
                    timesScan--;
                    break;
                }
        }
    }
    @Override
    public void doPlacementPhase(){
        for(int i = 0; i < shipsToPlace.size();i++){
            String name = shipsToPlace.get(i);
            doOnePlacement(name, shipCreationFns.get(name));
        }
    }
    /**
     * This function is for computer player to do one placement.
     * The placement for each ship is random
     */
    @Override
    public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn){
        //out.println(shipName);
        while(true) {
            Coordinate target = randomCoordinate();
            Placement p = randomPlacement(target);
            try {
                Ship<Character> s = createFn.apply(p);
                String error = theBoard.tryAddShip(s);
                if (error != null) {
                    continue;
                } else {
                    break;
                }
            } catch(IllegalArgumentException e){
                continue;
            }
        }
    }

    /**
     * This function is for computer player to do one attack.
     * The attack coordinate is random, and the outcome after attacking
     * will be printed out.
     */
    @Override
    public void doOneAttack(Board<Character> enemyBoard){
        //out.println("Attack");
        while (true) {
            Coordinate target = randomCoordinate();
                Ship<Character> s = enemyBoard.fireAt(target);
                if (s == null) {
                    out.println("Player "+name +" missed!");
                } else {
                    out.println("Player "+name +" hit your " + s.getName() + "!");
                }
                break;
        }
    }
    /**
     * This function is for computer player to do one move.
     * The new placement is generated randomly
     * If there's no ship at new placement, generate another placement
     */

    public void doOneMove(){
        out.println("Player "+name +" used a special action\n");
        Ship<Character> old_ship = null;
        while (true) {
            /*
            coordinate inside ship to move
             */
            Coordinate target = randomCoordinate();
            old_ship = theBoard.whatShipAt(target);
            if (old_ship == null) {
                continue;
            }
            break;
        }
        while(true) {
                /*
                coordinate and placement for new ship
                 */
            try {
                Coordinate new_coor = randomCoordinate();
                Placement new_place = randomPlacement(new_coor);
                Ship<Character> new_ship = ((V2ShipFactory) shipFactory).moveShip(old_ship, new_place);
                ((BattleShipBoard<Character>) theBoard).myShips.remove(old_ship);
                String error = theBoard.tryAddShip(new_ship);
                if (error != null) {
                    continue;
                } else {
                    break;
                }
            } catch (Exception e){
                continue;

            }
        }
    }
    /**
     * This function is for computer player to do one sonar scan.
     * The central point is generated randomly
     * But there's no any information shown
     */

    public void doOneSonarScan(Board<Character> enemyBoard){
        out.println("Player "+name +" used a special action\n");
        while (true) {
            Coordinate target = randomCoordinate();
                String ans = enemyBoard.sonarScan(target);
                //out.println(ans);
                break;
        }
    }

    /**
     * This function is to generate a random coordinate
     * @return the generated coordinate
     */
    private Coordinate randomCoordinate(){
        int colNum = (int)(Math.random()*10);
        int rowNum = (int)(Math.random()*20);
        Coordinate ans = new Coordinate(rowNum,colNum);
        return ans;
    }

    /**
     * This function is to generate a placement randomly base on given coordinate
     * @param c is given coordinate
     * @return a placement with random orientation
     */
    private Placement randomPlacement(Coordinate c ){
        HashMap<Integer, Character> m = new HashMap<>();
        m.put(0,'U');
        m.put(1,'R');
        m.put(2,'D');
        m.put(3,'L');
        m.put(4,'H');
        m.put(5,'V');
        int num = (int) ((Math.random() * 6) % 6);
        Placement res = new Placement(c,m.get(num));
        return res;
    }

    /**
     * generate random action
     * @return corresponding character for the action
     */
    private Character randomAction(){
        HashMap<Integer, Character> m = new HashMap<>();
        m.put(0,'F');
        m.put(1,'M');
        m.put(2,'S');
        int num = (int) ((Math.random() * 3) % 3);
        return m.get(num);
    }


}
