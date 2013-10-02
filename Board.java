package reversi;

import java.util.ArrayList;

public class Board {
  public static final int SIZE = 4;
  
  public static final char WHITE = 'O';
  public static final char BLACK = 'X';
  public static final char EMPTY = '.';
  
  public static final int REGION_1_BIAS = 1;
  public static final int REGION_2_BIAS = 1;
  public static final int REGION_3_BIAS = 1;
  public static final int REGION_4_BIAS = 1;
  public static final int REGION_5_BIAS = 1;
  
  int[][] board;

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
  
  public void flip(Move m) {
    // TODO
  }
  
  int get(int row, int col) {
    if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) return Game.OUT_OF_BOUNDS;
    return board[row][col];
  }
  
  public Move[] getValidMoves(int color) {
    ArrayList<Move> moves = new ArrayList<Move>();
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        Move m = new Move(i, j, color);
        if (isValid(m)) {
          moves.add(m);
        }
      }
    }
    return (Move[]) moves.toArray();
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
    // TODO
    return 1;
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

}

