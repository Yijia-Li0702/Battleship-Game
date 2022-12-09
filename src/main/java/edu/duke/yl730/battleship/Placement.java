package edu.duke.yl730.battleship;

public class Placement {
  private final Coordinate where;
  private final char orientation;
  
  public Placement(Coordinate w, char o){
    o = Character.toUpperCase(o);
    where = w;
    orientation = checkOrientation(o);
    }
  
  public Placement(String descr) {
    if (descr.length() != 3) {
      throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.\n");
    }
    where = new Coordinate(descr.substring(0,2));
    descr=descr.toUpperCase();
    orientation = checkOrientation(descr.charAt(2));
  }

  /**
   * To check if the orientation is illegal
   * @param o orientation to check
   * @return illegal orientation
   */
  protected char checkOrientation(char o){
    o = Character.toUpperCase(o);
    if(o!='H' && o !='V'&& o!='U'&& o!='D' && o!='L' && o!='R'){
      throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.\n");
    }
    return o;
  }

  /**
   * Get orientation
   * @return orientation
   */
  public char getOrientation() {
    return orientation;
  }
  
  public Coordinate getWhere() {
    return where;
  }
  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Placement c = (Placement) o;
      return where.equals(c.where) && orientation == c.orientation;
    }
    return false;
  }
  @Override
  public String toString() {
    return where.toString() + " "+ orientation;
  }
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
  
}

//    if(descr.charAt(2) != 'H' && descr.charAt(2)!='V'){
//      throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.\n");
//    } else{
//      orientation = descr.charAt(2);
//    }

