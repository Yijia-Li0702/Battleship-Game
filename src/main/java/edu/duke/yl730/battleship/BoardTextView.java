package edu.duke.yl730.battleship;

import java.util.function.Function;

public class BoardTextView {
/**
 * This class handles textual display of
 * a Board (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the
 * enemy's board.
 */

  /**
   * The Board to display
   */
  private final Board<Character> toDisplay;
  /**
   * Constructs a BoardView, given the board it will display.
   *
   * @param toDisplay is the Board to display
   * @throws IllegalArgumentException if the board is larger than 10x26.
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
              "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }

  /**
   * display my board to my own
   * @return the display string
   */
  public String displayMyOwnBoard() {
    return displayAnyBoard((c)->toDisplay.whatIsAtForSelf(c));
  }

  /**
   * display my board to enemy
   * @return the display string
   */
  public String displayEnemyBoard() {
    return displayAnyBoard((c)->toDisplay.whatIsAtForEnemy(c));
  }

  /**
   * A helper function to display any kind of board
   * @param getSquareFn the funcion of way to display the board
   * @return the string to display
   */
  protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn){
    StringBuilder ans = new StringBuilder("");
    ans.append(this.makeHeader());
    char c = 65;
    for(int row = 0; row < toDisplay.getHeight();row++) {
      for (int column = 0; column < toDisplay.getWidth();column++) {
        Coordinate pivot = new Coordinate(row, column);
        //when there's no ship on the board
        if(getSquareFn.apply (pivot)== null) {
          if (column == 0) {
            ans.append(c);
            ans.append("  |");
          } else if (column == toDisplay.getWidth() - 1) {
            ans.append("  ");
            ans.append(c);
            ans.append("\n");
          } else {
            ans.append(" |");
          }
        }  else{
          char letter = getSquareFn.apply (pivot);
          if (column == 0) {
            ans.append(c);
            //ans.append(" s|");
            ans.append(" "+letter+"|");
          } else if (column == toDisplay.getWidth() - 1) {
            //ans.append("s ");
            ans.append(letter+" ");
            ans.append(c);
            ans.append("\n");
          } else {
            //ans.append("s|");
            ans.append(letter+"|");
          }
        }
      }
      c++;
    }
    ans.append(this.makeHeader());
    return ans.toString(); //this is a placeholder for the moment
  }

  /**
   * Concatenated the board for me and the enemyHeaderView
   * @param enemyView     enemy's View
   * @param myHeader      header of my board
   * @param enemyHeader   header of enemy's board
   * @return              A string showing my board
   */
  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader,
                                                String enemyHeader) {
    int w = toDisplay.getWidth();
    String myViewString = displayMyOwnBoard();
    String enemyViewString = enemyView.displayEnemyBoard();
    String [] mylines = myViewString.split("\n");
    String [] enemylines = enemyViewString.split("\n");
    StringBuilder Distwo = new StringBuilder();
    StringBuilder firstHeader = new StringBuilder("     ");

    StringBuilder Header = firstHeader.append(myHeader).append("                      ").append(enemyHeader).append("\n");
    Distwo.append(Header);
    //Distwo.append(mylines[0]).append("                           ").append(enemylines[0]).append("\n");
    Distwo.append(mylines[0]).append("              ").append(enemylines[0]).append("\n");

    for(int i = 1; i < mylines.length-1;i++){
      StringBuilder Start = new StringBuilder(2*w+32-mylines[i].length());
      //Distwo.append(mylines[i]).append("                         ").append(enemylines[i]).append("\n");
      Distwo.append(mylines[i]).append("            ").append(enemylines[i]).append("\n");

    }
    StringBuilder Start = new StringBuilder(2*w+32-mylines[mylines.length-1].length()-2);
    Distwo.append(mylines[mylines.length-1]).append("              ").append(enemylines[mylines.length-1]).append("\n");
    return Distwo.toString();

  }





    /**
     * This makes the header line, e.g. 0|1|2|3|4\n
     *
     * @return the String that is the header line for the given board
     */
  String makeHeader() {
    StringBuilder ans = new StringBuilder("  "); // README shows two spaces at
    String sep=""; //start with nothing to separate, then switch to | to separate
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append("\n");

    return ans.toString();
  }


}
