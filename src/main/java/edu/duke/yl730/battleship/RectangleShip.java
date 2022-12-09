package edu.duke.yl730.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {

    final String name;

    @Override
    public String getName() {
        return name;
    }

    /**
     * This function is to put squares in the ship to a hashset
     * @param upperLeft is upperleft square of the ship
     * @param width width of the ship
     * @param height heigh of the ship
     * @return a hashset contains all points of the ship
     */
    public static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
        HashSet<Coordinate> myCoords = new HashSet<Coordinate>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                Coordinate curr = new Coordinate(upperLeft.getRow()+row, upperLeft.getColumn()+column);
                myCoords.add(curr);
            }
        }
        return myCoords;
    }
    public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo,ShipDisplayInfo<T> enemyDisplayInfo) {
        super(makeCoords(upperLeft, width, height), myDisplayInfo,enemyDisplayInfo,width>height?'H':'V',upperLeft);
        this.name = name;
    }
    public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
        this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit),
                new SimpleShipDisplayInfo<T>(null, data));
    }

    public RectangleShip(Coordinate upperLeft, T data, T onHit) {
        this("testship", upperLeft, 1, 1, data, onHit);
    }


//    public RectangleShip(Coordinate upperLeft, int width, int height, T data, T onHit) {
//        this(upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit));
//    }
//    public RectangleShip(Coordinate upperLeft, T data, T onHit) {
//        this(upperLeft, 1, 1, data, onHit);
//    }
//
//    public RectangleShip(Coordinate upperLeft, int width, int height,ShipDisplayInfo<T> myDisplayInfo) {
//        super(makeCoords(upperLeft, width, height),myDisplayInfo);
//    }

}


