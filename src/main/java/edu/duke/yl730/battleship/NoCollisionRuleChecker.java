package edu.duke.yl730.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
    /**
     * check if the ship has collision with ships in the board
     * @param theShip ship to check
     * @param theBoard board to check
     * @return message about results
     */
    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        BattleShipBoard<T> myBoard = (BattleShipBoard<T>) theBoard;
        //for each coordinate of ship, check if this coordinate is occupied in the board
        for(Coordinate c:theShip.getCoordinates()){
            for(Ship<T> s:myBoard.myShips){
                if(s.occupiesCoordinates(c)){
                    return "That placement is invalid: the ship overlaps another ship.\n";
                }
            }

        }
        return null;
    }

    /**
     * constructor for the checker
     * @param next next checker
     */
    public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }
}
//if(theBoard.whatIsAtForSelf(c)!=null) {
//                return "That placement is invalid: the ship overlaps another ship.\n";
//            }
