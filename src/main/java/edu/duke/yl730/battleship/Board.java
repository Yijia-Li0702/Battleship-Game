package edu.duke.yl730.battleship;

public interface Board<T> {
  public int getWidth();

  public int getHeight();
  public String tryAddShip(Ship<T> toAdd);
  public T whatIsAtForSelf(Coordinate where);
  public T whatIsAtForEnemy(Coordinate where);
  public boolean ifAllSunk();
  public Ship<T> fireAt(Coordinate c);
  public String sonarScan(Coordinate target);
  public Ship<T> whatShipAt(Coordinate c);
}






