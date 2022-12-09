package edu.duke.yl730.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {
    /**
     * Check if the position of ship is invalid in the board
     * @param theShip ship to check
     * @param theBoard board to check
     * @return information about the result
     */
    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        //check if the ship is outside of the board
        int h = theBoard.getHeight();
        int w = theBoard.getWidth();
        for(Coordinate c:theShip.getCoordinates()){
            if(c.getColumn()>=w ){
                return "That placement is invalid: the ship goes off the right of the board.\n";
            }
            if(c.getColumn() <0){
                return "That placement is invalid: the ship goes off the left of the board.\n";
            }
            if(c.getRow()>=h){
                return "That placement is invalid: the ship goes off the bottom of the board.\n"
;
            }
            if(c.getRow()<0){
                return "That placement is invalid: the ship goes off the top of the board.\n"
;
            }
        }
        return null;
    }

    /**
     * Constructor for checker
     * @param next is a pointer to next checker
     */
    public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }
}

