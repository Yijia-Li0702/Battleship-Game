package edu.duke.yl730.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  public final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  final HashSet<Coordinate> enemyMisses;
  /**
   * one hashmap to record all squares that have been hitted, used to display for enemy
   */
  final HashMap<Coordinate,T> hitSquare;
  final T missInfo;



  /**
   * Constructs a BattleShipBoard with the specified width
   * and height
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or equal to zero.
   */
  public BattleShipBoard(int w, int h,T missInfo) {
    this(w, h, new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<>(null)),missInfo);
  }
  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> placementChecker, T missInfo) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is t" + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
    myShips = new ArrayList<>();
    this.placementChecker = placementChecker;
    enemyMisses = new HashSet<>();
    this.missInfo = missInfo;
    this.hitSquare = new HashMap<>();
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  /**
   * Display current status of coordinate where for my self, * for square hit before
   * @param where the coordinate to be displayed
   * @return display of the coordinate
   */
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  /**
   * Display my current board for enemy
   * @param where the coordinate to be displayed
   * @return display of the coordinate
   */
  public T whatIsAtForEnemy(Coordinate where) {
    if(hitSquare.containsKey(where)){
      return hitSquare.get(where);
    }
    if(enemyMisses.contains(where)){
      return missInfo;
    } else{
      return null;
    }
//    return whatIsAt(where, false);
  }

  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s: myShips) {
      if (s.occupiesCoordinates(where)){
        return s.getDisplayInfoAt(where,isSelf);
      }
    }
    if(enemyMisses.contains(where)) {
      return missInfo;
    }
    return null;

  }

  /**
   * Add one ship to the board, check if the ship is invalid
   * @param toAdd ship to be added
   * @return a message about if ship has been added successfully
   */
  public String tryAddShip(Ship<T> toAdd){
    if(placementChecker.checkPlacement(toAdd, this)!=null){
      return placementChecker.checkPlacement(toAdd, this);
    }
    myShips.add(toAdd);
    return null;
  }

  /**
   * Enemy fire at coordinate c, record it and put it into hitSquare
   * @param c coordinate hit
   * @return ship being hit
   */
  public Ship<T> fireAt(Coordinate c) {
    for(Ship<T> s: myShips){
      if (s.occupiesCoordinates(c)){
        s.recordHitAt(c);
        T show = ((BasicShip<T>) s).enemyDisplayInfo.getInfo(c,true);
        hitSquare.put(c,show);
        return s;
      }
    }
    enemyMisses.add(c);
    return null;
  }

  /**
   * check if all ships in the board are sunk
   * @return false if one ship isn't sunk
   */
  public boolean ifAllSunk(){
    for(Ship<T> s: myShips){
      if(!s.isSunk()){
        return false;
      }
    }
    return true;
  }

  /**
   * return the ship  at c
   * @param c coordinate required
   * @return ship that occupies c
   */
  public Ship<T> whatShipAt(Coordinate c){
    for(Ship<T> s: myShips){
      if (s.occupiesCoordinates(c)){
        return s;
      }
    }
    return null;
  }

  /**
   * Do one sonar scan
   * @param target central point of the sonar scan
   * @return result of the scan
   */
  public String sonarScan(Coordinate target){
    HashMap<String, Integer> res = new HashMap<>();
    res.put("Submarine",0);
    res.put("Destroyer",0);
    res.put("Battleship",0);
    res.put("Carrier",0);
    for(int i = 0; i<4;i++){
      for(int j = 0; j < 4-i;j++){
        if(i == 0 && j != 0){
          Coordinate c1 = new Coordinate(target.getRow(),target.getColumn()+j);
          res = updateMap(res,c1);
          Coordinate c2 = new Coordinate(target.getRow(),target.getColumn()-j);
          res = updateMap(res,c2);
        } else if(i != 0 && j ==0) {
          Coordinate c1 = new Coordinate(target.getRow()+i,target.getColumn());
          res = updateMap(res,c1);
          Coordinate c3 = new Coordinate(target.getRow()-i,target.getColumn());
          res = updateMap(res,c3);
        } else if(i == 0 && j ==0){
          Coordinate c1 = new Coordinate(target.getRow(),target.getColumn());
          res = updateMap(res,c1);
        } else {
          Coordinate c1 = new Coordinate(target.getRow() + i, target.getColumn() + j);
          res = updateMap(res, c1);
          Coordinate c2 = new Coordinate(target.getRow() + i, target.getColumn() - j);
          res = updateMap(res, c2);
          Coordinate c3 = new Coordinate(target.getRow() - i, target.getColumn() - j);
          res = updateMap(res, c3);
          Coordinate c4 = new Coordinate(target.getRow() - i, target.getColumn() + j);
          res = updateMap(res, c4);
        }
      }
    }

    StringBuilder ans = new StringBuilder();
    ans.append("Submarines occupy ").append(res.get("Submarine")).append(" squares\n")
            .append("Destroyers occupy ").append(res.get("Destroyer")).append(" squares\n")
            .append("Battleships occupy ").append(res.get("Battleship")).append(" squares\n")
            .append("Carriers occupy ").append(res.get("Carrier")).append(" squares\n");
    return ans.toString();
  }

  /**
   * Update sonar scan information after one scan
   * @param res result of the scan
   * @param c coordinate being detected
   * @return result of this scan
   */
  protected HashMap<String, Integer> updateMap(HashMap<String, Integer> res, Coordinate c){
    //System.out.println(c.getRow()+","+c.getColumn()+"\n");

    if(whatKindofShip(c)!=null){
      String shipName =whatKindofShip(c);
      //System.out.println(shipName);
      res.put(shipName,res.get(shipName)+1);
    }
    return res;
  }

  /**
   * Check what kind of ship occupies this coordinate
   * @param where coordinate to be checked
   * @return symbol of the ship
   */
  protected String whatKindofShip(Coordinate where){
    for (Ship<T> s: myShips) {
      if (s.occupiesCoordinates(where)){
        return s.getName();
      }
    }
    return null;
  }
}



