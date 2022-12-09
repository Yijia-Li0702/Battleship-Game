package edu.duke.yl730.battleship;

public class Coordinate {
   private final int row;
  private final int column;
  
  public Coordinate(int r,int c){
    this.row = r;
    this.column = c;
  }

  /**
   * Constructor of coordinate from a string, also check if the string is invalid
   * @param descr is the string to transfer to coordinate
   */
  public Coordinate(String descr){
    if(descr.length()!=2){
      throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.\n");
    }
    descr = descr.toUpperCase();
    if(descr.charAt(0)< 'A' || descr.charAt(0) > 'Z'){
      throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.\n");
    }
    if(descr.charAt(1) < '0' || descr.charAt(1) > '9'){
      throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.\n");
    }
    row = descr.charAt(0) -  'A';
    column = descr.charAt(1) - '0';
  }
  
  int getRow(){
    return row;
  }

  int getColumn(){
    return column;
  }
  
  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }
  
  @Override
  public String toString() {
    return "("+row+", " + column+")";
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
  
}
