package edu.duke.yl730.battleship;

public abstract class PlacementRuleChecker<T> {
  /**
   * This class is to do placement rule check,
   * check if the ship in the board is valid
   */
  private final PlacementRuleChecker<T> next;

  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }
  protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);
  public String checkPlacement (Ship<T> theShip, Board<T> theBoard) {
    //if we fail our own rule: stop the placement is not legal
    if (checkMyRule(theShip, theBoard)!= null) {
      return checkMyRule(theShip, theBoard);
    }
    //other wise, ask the rest of the chain.
    if (next != null) {
      return next.checkPlacement(theShip, theBoard); 
    }
    //if there are no more rules, then the placement is legal
    return null;
  }


}
