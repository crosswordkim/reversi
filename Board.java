package reversi;

import java.util.ArrayList;

public class Board {
  public static final int SIZE = 8;
  
  public static final char WHITE = 'O';
  public static final char BLACK = 'X';
  public static final char EMPTY = '.';
  
  public static final int REGION_1_BIAS = 1;
  public static final int REGION_2_BIAS = 1;
  public static final int REGION_3_BIAS = 1;
  public static final int REGION_4_BIAS = 1;
  public static final int REGION_5_BIAS = 1;
  
  int[][] board;
  int[][] biased;

  /**
   * Set up a blank board with four pieces in the center:
   *    a b c d e f g h
   * 1. 0 0 0 0 0 0 0 0
   * 2. 0 0 0 0 0 0 0 0
   * 3. 0 0 0 0 0 0 0 0
   * 4. 0 0 0 1 2 0 0 0
   * 5. 0 0 0 2 1 0 0 0
   * 6. 0 0 0 0 0 0 0 0
   * 7. 0 0 0 0 0 0 0 0
   * 8. 0 0 0 0 0 0 0 0
   * 
   */
  public Board () {
    board = new int[SIZE][SIZE];
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        board[i][j] = Game.EMPTY;
      }
    }

    int center = SIZE / 2;
    board[center][center] = Game.WHITE;
    board[center - 1][center - 1] = Game.WHITE;
    board[center - 1][center] = Game.BLACK;
    board[center][center - 1] = Game.BLACK;
    
    /**
     * Region locations:
     *    a b c d e f g h
     * 1. 5 4 3 3 3 3 4 5
     * 2. 4 4 2 2 2 2 4 4
     * 3. 3 2 1 1 1 1 2 3
     * 4. 3 2 1 1 1 1 2 3
     * 5. 3 2 1 1 1 1 2 3
     * 6. 3 2 1 1 1 1 2 3
     * 7. 4 4 2 2 2 2 4 4
     * 8. 5 4 3 3 3 3 4 5
     * 
     * @param row
     * @param column
     * @return
     */
    
    if (SIZE == 8) {
    	biased = new int[SIZE][SIZE];
    	biased [0][0] = 5; biased [0][1] = 1; biased [0][2] = 4; biased [0][3] = 4; biased [0][4] = 4; biased [0][5] = 4; biased [0][6] = 1; biased [0][7] = 5;
    	biased [1][0] = 1; biased [1][1] = 1; biased [1][2] = 2; biased [1][3] = 2; biased [1][4] = 2; biased [1][5] = 2; biased [1][6] = 1; biased [1][7] = 1;
    	biased [2][0] = 4; biased [2][1] = 2; biased [2][2] = 3; biased [2][3] = 3; biased [2][4] = 3; biased [2][5] = 3; biased [2][6] = 2; biased [2][0] = 4;
    	biased [3][0] = 4; biased [3][1] = 2; biased [3][2] = 3; biased [3][3] = 3; biased [3][4] = 3; biased [3][5] = 3; biased [3][6] = 2; biased [3][0] = 4;
    	biased [4][0] = 4; biased [4][1] = 2; biased [4][2] = 3; biased [4][3] = 3; biased [4][4] = 3; biased [4][5] = 3; biased [4][6] = 2; biased [4][0] = 4;
    	biased [5][0] = 4; biased [5][1] = 2; biased [5][2] = 3; biased [5][3] = 3; biased [5][4] = 3; biased [5][5] = 3; biased [5][6] = 2; biased [5][0] = 4;
    	biased [6][0] = 1; biased [6][1] = 1; biased [6][2] = 2; biased [6][3] = 2; biased [6][4] = 2; biased [6][5] = 2; biased [6][6] = 1; biased [6][7] = 1;
    	biased [7][0] = 5; biased [7][1] = 1; biased [7][2] = 4; biased [7][3] = 4; biased [7][4] = 4; biased [7][5] = 4; biased [7][6] = 1; biased [7][7] = 5;
    }
    
  }
  
  boolean set(Move m) {
    return set(m.row, m.column, m.color);
  }
  
  boolean set(int row, int col, int val) {
    if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) return false;
    board[row][col] = val;
    flip(new Move(row, col, val));
    return true;
  }
  
  public int flip(Move m) {
	  int flips = 0;
	  
	  if (traverse(m, -1, 0)) {
		  flips += flipLine(m, -1, 0);
	  }
	  
	  if (traverse(m, -1, 1)) {
		  flips += flipLine(m, -1, 1);
	  }
	  
	  if (traverse(m, 0, 1)) {
		  flips += flipLine(m, 0, 1);
	  }
	  
	  if (traverse(m, 1, 1)) {
		  flips += flipLine(m, 1, 1);
	  }
	  
	  if (traverse(m, 1, 0)) {
		  flips += flipLine(m, 1, 0);
	  }
	  
	  if (traverse(m, 1, -1)) {
		  flips += flipLine(m, 1, -1);
	  }
	  
	  if (traverse(m, 0, -1)) {
		  flips += flipLine(m, 0, -1);
	  }
	  
	  if (traverse(m, -1, -1)) {
		  flips += flipLine(m, -1, -1);
	  }
	  return flips;
  }
  
  public int flipLine (Move m, int rowDir, int colDir) {
	  int flips = 0;
	  int column = m.column + colDir;
	  int row = m.row + rowDir;	  
	  while (board[row][column] != m.color) {
		  board[row][column] = m.color;
		  column = column + colDir;
		  row = row + rowDir;
		  flips++;
	  }
	  return flips;
  }
  
  int get(int row, int col) {
    if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) return Game.OUT_OF_BOUNDS;
    return board[row][col];
  }
  
  public ArrayList<Move> getValidMoves(int color) {
    ArrayList<Move> moves = new ArrayList<Move>();
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        Move m = new Move(i, j, color);
        if (isValid(m)) {
          moves.add(m);
        }
      }
    }
    return moves;
  }
  
  /**
   * Determines whether a player has any moves left
   * @param player
   * @return
   */
  public boolean existValidMove(int color) {
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        Move m = new Move(i, j, color);
        if (isValid(m)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Determines whether a move is valid
   * @param m
   * @return
   */
  public boolean isValid(Move m) {
	if (this.board[m.row][m.column] != 0) {
		return false;
	}
	
    // Traverse in eight directions
    // +y |
    //    v
    // +x -->
    boolean n = traverse(m, -1, 0);
    boolean nw = traverse(m, -1, 1);
    boolean w = traverse(m, 0, 1);
    boolean sw = traverse(m, 1, 1);
    boolean s = traverse(m, 1, 0);
    boolean se = traverse(m, 1, -1);
    boolean e = traverse(m, 0, -1);
    boolean ne = traverse(m, -1, -1);
    return n || nw || w || sw || s || se || e || ne;
  }
  
  /**
   * Starting from the point defined in the move, move in the direction
   * defined by dirRow, dirCol to verify if there is a flip
   * @param m
   * @param dirRow
   * @param dirCol
   * @return 
   */
  public boolean traverse(Move m, int dirRow, int dirCol) {
    int otherColor = (m.color == Game.BLACK) ? Game.WHITE : Game.BLACK; 
    int currRow = m.row;
    int currCol = m.column;
    boolean lookingForOtherColor = true;
    while (get(currRow, currCol) != Game.OUT_OF_BOUNDS) {
      currRow += dirRow;
      currCol += dirCol;
      
      // Two states: looking for tiles of the opposite color, then
      //             looking for one tile of the same color
      if (lookingForOtherColor) {
        if (get(currRow, currCol) == otherColor) {
          lookingForOtherColor = false;
        } else {
          return false;
        }
      } else {
        if (get(currRow, currCol) == m.color) return true;
        if (get(currRow, currCol) == Game.EMPTY) return false;
      }
    }
    return false;
  }

  /**
   * Executes a move on the board
   * @param m
   * @return
   */
  public boolean makeMove(Move m) {
    if (isValid(m)) {
      set(m);
      return true;
    }
    return false;
  }


  
  public int getScore(int color) {
    int score = 0;
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        if (board[i][j] == color) {
          score++;
        }
      }
    }
    return score;
  }
  
  /**
   * Returns the region bias for a certain square
   * 
   * Region locations:
   *    a b c d e f g h
   * 1. 5 4 3 3 3 3 4 5
   * 2. 4 4 2 2 2 2 4 4
   * 3. 3 2 1 1 1 1 2 3
   * 4. 3 2 1 1 1 1 2 3
   * 5. 3 2 1 1 1 1 2 3
   * 6. 3 2 1 1 1 1 2 3
   * 7. 4 4 2 2 2 2 4 4
   * 8. 5 4 3 3 3 3 4 5
   * 
   * @param row
   * @param column
   * @return
   */
  public int regionBias(int row, int column) {    
    return biased[row][column];
  }

  /**
   * Creates the string representation of the board
   */
  @Override
  public String toString() {
    char columnLetter = 'a';
    String n = "   ";
    for (int i = 0; i < SIZE; i++) {
      n += columnLetter + " ";
      columnLetter++;
    }
    n += "\n";
    for (int i = 0; i < SIZE; i++) {
      int row_number = (i + 1) % 10;
      n += row_number + ". ";
      for (int j = 0; j < SIZE; j++) {
        int piece = board[i][j];
        char picture = EMPTY;
        if (piece == Game.BLACK) picture = BLACK;
        if (piece == Game.WHITE) picture = WHITE;
        n += picture + " ";
      }
      n += "\n";
    }
    return n;
  }

public void clone(Board otherBoard) {
	for (int i = 0; i < SIZE; i++) {
		for (int j = 0; j < SIZE; j++) {
			otherBoard.board[i][j] = this.board[i][j];
		}
	}
	
}
}
