package reversi;

public class Move {
  int row;
  int column;
  int color;
  int rating;
  
  public Move(int row, int column, int color) {
    this.row = row;
    this.column = column;
    this.color = color;
  }
  
  /**
   * Gets the value of a move
   * @return
   */
  int rate(Board b) {
    Board temp = new Board();
    b.clone(temp);    
    int rawRating = temp.flip(this) + 1;  //Plus one because of the piece that is placed down.
    if (b.SIZE == 8) {
    	this.rating = rawRating * b.regionBias(row, column);
    } else {
    	this.rating = rawRating;
    }
    
    return this.rating;
  }
  
  @Override
  public String toString() {
    char colChar = (char) ('a' + column);
    String colorStr = (color == Game.BLACK) ? "BLACK" : "WHITE";
    return colorStr + " " + Integer.toString(row + 1) + "" + colChar;
  }
}
