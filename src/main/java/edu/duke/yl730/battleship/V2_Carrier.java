package edu.duke.yl730.battleship;

import java.util.HashMap;
import java.util.HashSet;

public class V2_Carrier<T> extends BasicShip<T> {

    final String name;
    public HashMap<Integer,Coordinate> piecesIndex;

    public V2_Carrier(String name, Coordinate upperLeft, char orientation, ShipDisplayInfo<T> myDisplayInfo,ShipDisplayInfo<T> enemyDisplayInfo) {
        super(myDisplayInfo,enemyDisplayInfo,orientation,upperLeft);
        myPieces = new HashMap<Coordinate, Boolean>();
        this.piecesIndex = new HashMap<>();
        Iterable<Coordinate> where = makeCoords(upperLeft,orientation);
        for (Coordinate c: where) {
            myPieces.put(c,false);
        }
        this.name = name;
    }
    public V2_Carrier(String name, Coordinate upperLeft, char orientation, T data, T onHit) {
        this(name, upperLeft, orientation, new SimpleShipDisplayInfo<T>(data, onHit),
                new SimpleShipDisplayInfo<T>(null, data));
    }

    @Override
    public String getName() {
        return name;
    }

    public HashSet<Coordinate> makeCoords(Coordinate upperLeft, char orientation) {
        HashSet<Coordinate> myCoords = new HashSet<Coordinate>();
        int r = upperLeft.getRow();
        int c = upperLeft.getColumn();
        orientation = Character.toUpperCase(orientation);
        if(orientation == 'U'){
            Coordinate c1 = new Coordinate(r,c);
            myCoords.add(c1);
            this.piecesIndex.put(1,c1);

            Coordinate c2 = new Coordinate(r+1,c);
            myCoords.add(c2);
            this.piecesIndex.put(2,c2);

            myCoords.add(new Coordinate(r+2,c));
            this.piecesIndex.put(3,new Coordinate(r+2,c));

            myCoords.add(new Coordinate(r+2,c+1));
            this.piecesIndex.put(4,new Coordinate(r+2,c+1));

            myCoords.add(new Coordinate(r+3,c+1));
            this.piecesIndex.put(5,new Coordinate(r+3,c+1));

            myCoords.add(new Coordinate(r+4,c+1));
            this.piecesIndex.put(6,new Coordinate(r+4,c+1));


        } else if(orientation == 'R'){
            myCoords.add(new Coordinate(r,c+2));
            this.piecesIndex.put(4,new Coordinate(r,c+2));

            myCoords.add(new Coordinate(r,c+3));
            this.piecesIndex.put(5,new Coordinate(r,c+3));

            myCoords.add(new Coordinate(r,c+4));
            this.piecesIndex.put(6,new Coordinate(r,c+4));

            myCoords.add(new Coordinate(r+1,c));
            this.piecesIndex.put(1,new Coordinate(r+1,c));

            myCoords.add(new Coordinate(r+1,c+1));
            this.piecesIndex.put(2,new Coordinate(r+1,c+1));

            myCoords.add(new Coordinate(r+1,c+2));
            this.piecesIndex.put(3,new Coordinate(r+1,c+2));

        } else if(orientation == 'D'){
            myCoords.add(new Coordinate(r,c+1));
            this.piecesIndex.put(6,new Coordinate(r,c+1));

            myCoords.add(new Coordinate(r+1,c+1));
            this.piecesIndex.put(5,new Coordinate(r+1,c+1));

            myCoords.add(new Coordinate(r+2,c+1));
            this.piecesIndex.put(4,new Coordinate(r+2,c+1));

            myCoords.add(new Coordinate(r+2,c));
            this.piecesIndex.put(3,new Coordinate(r+2,c));

            myCoords.add(new Coordinate(r+3,c));
            this.piecesIndex.put(2,new Coordinate(r+3,c));

            myCoords.add(new Coordinate(r+4,c));
            this.piecesIndex.put(1,new Coordinate(r+4,c));


        } else  if(orientation == 'L'){
            myCoords.add(new Coordinate(r,c+1));
            this.piecesIndex.put(5,new Coordinate(r,c+1));

            myCoords.add(new Coordinate(r,c+2));
            this.piecesIndex.put(4,new Coordinate(r,c+2));

            myCoords.add(new Coordinate(r,c));
            this.piecesIndex.put(6,new Coordinate(r,c));

            myCoords.add(new Coordinate(r+1,c+2));
            this.piecesIndex.put(3,new Coordinate(r+1,c+2));

            myCoords.add(new Coordinate(r+1,c+3));
            this.piecesIndex.put(2,new Coordinate(r+1,c+3));

            myCoords.add(new Coordinate(r+1,c+4));
            this.piecesIndex.put(1,new Coordinate(r+1,c+4));
        }  else {
            throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.\n");
        }
        return myCoords;
    }
//    public V2_Carrier(String name, Coordinate upperLeft, char orientation, ShipDisplayInfo<T> myDisplayInfo,ShipDisplayInfo<T> enemyDisplayInfo) {
//        super(makeCoords(upperLeft, orientation), myDisplayInfo,enemyDisplayInfo,orientation,upperLeft);
//        this.name = name;
//    }
//    public V2_Carrier(String name, Coordinate upperLeft, char orientation, T data, T onHit) {
//        this(name, upperLeft, orientation, new SimpleShipDisplayInfo<T>(data, onHit),
//                new SimpleShipDisplayInfo<T>(null, data));
//    }
    //this constructor is for test

}
