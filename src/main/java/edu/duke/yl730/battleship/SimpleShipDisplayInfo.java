package edu.duke.yl730.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
    final T myData;
    final T onHit;

    public SimpleShipDisplayInfo(T myData, T onHit) {
        this.myData = myData;
        this.onHit = onHit;
    }


    public T getInfo(Coordinate where, boolean hit){
        return hit?onHit:myData;
    }
}
