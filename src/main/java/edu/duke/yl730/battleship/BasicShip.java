package edu.duke.yl730.battleship;

import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T> {
  
    //private final Coordinate myLocation;
    public HashMap<Coordinate, Boolean> myPieces;
    protected ShipDisplayInfo<T> myDisplayInfo;
    public ShipDisplayInfo<T> enemyDisplayInfo;
    protected char orientation;
    protected Coordinate upperLeft;



    public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo,ShipDisplayInfo<T> enemyDisplayInfo,char orientation,Coordinate upperLeft){
        this.myDisplayInfo = myDisplayInfo;
        myPieces = new HashMap<Coordinate, Boolean>();
        for (Coordinate c: where) {
            myPieces.put(c,false);
        }
        this.enemyDisplayInfo = enemyDisplayInfo;
        this.orientation = orientation;
        this.upperLeft = upperLeft;
    }
    public BasicShip(ShipDisplayInfo<T> myDisplayInfo,ShipDisplayInfo<T> enemyDisplayInfo,char orientation,Coordinate upperLeft){
        this.myDisplayInfo = myDisplayInfo;
        myPieces = new HashMap<Coordinate, Boolean>();
        this.enemyDisplayInfo = enemyDisplayInfo;
        this.orientation = orientation;
        this.upperLeft = upperLeft;
    }

    /**
     * Get all coordinates of the ship
     * @return iterator of coordinate
     */
    @Override
    public Iterable<Coordinate> getCoordinates(){
        return myPieces.keySet();
    }

    /**
     * Get ship's orientation
     * @return its orientation
     */
    @Override
    public char getOrientation(){
        return orientation;
    }

    /**
     * Check if the ship occupies the coordinate
     * @param where is the Coordinate to check if this Ship occupies
     * @return false if it doesn't occupy
     */
    @Override
    public boolean occupiesCoordinates(Coordinate where) {
        return myPieces.containsKey(where);
    }

    /**
     * check if the ship is sunk
     * @return true if the ship is sunk
     */
    @Override
    public boolean isSunk() {
        if(myPieces.containsValue(false)){
            return false;
        }else {
            return true;
        }

    }

    /**
     * record the coordinate that was hit
     * @param where specifies the coordinates that were hit.
     */
    @Override
    public void recordHitAt(Coordinate where) {
        checkCoordinateInThisShip(where);
        myPieces.put(where, true);

    }

    /**
     * check if this coordinate was hit
     * @param where is the coordinates to check.
     * @return true if it was hit
     */
    @Override
    public boolean wasHitAt(Coordinate where) {
        checkCoordinateInThisShip(where);
        return myPieces.get(where);
    }

    /**
     * get display of the coordinate
     * @param where is the coordinate to return information for, myship
     * decide whether to use myDisplayInfo or enemyDisplayInfo
     * to call getInfo on.
     * @param isSelf
     * @return its signal
     */
    @Override
    public T getDisplayInfoAt(Coordinate where, boolean isSelf) {

        //look up the hit status of this coordinate
        checkCoordinateInThisShip(where);
        if(isSelf) {
            return myDisplayInfo.getInfo(where, myPieces.get(where));
        }else{
            return enemyDisplayInfo.getInfo(where,myPieces.get(where));
        }

    }

    /**
     * check if the coordinate belongs to the ship
     * @param c coordinate to check
     */
    protected void checkCoordinateInThisShip(Coordinate c){
        if(!myPieces.containsKey(c)){
            throw new IllegalArgumentException("this coordinate isn't part of the ship");
        }
    }


//    @Override
//    public boolean equals(Object o) {
//        if (o.getClass().equals(getClass())) {
//            BasicShip<T> c = (BasicShip<T>) o;
//            return myPieces.equals(c.myPieces) && orientation == c.orientation && upperLeft == c.upperLeft;
//        }
//        return false;
//    }


}
