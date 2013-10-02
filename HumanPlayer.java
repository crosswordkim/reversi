package reversi;

import java.util.Scanner;

public class HumanPlayer extends Player {
  Scanner input;

  public HumanPlayer (int c) {
    super(c);
    input = new Scanner(System.in);
  }
  
  public Move getMove(Board b) {
    //TODO: allow the player to resign
	System.out.print("Please enter row then column: ");
    int row = input.nextInt() - 1;       
    String sColumn = input.next();
    
    char cColumn[] = sColumn.toCharArray();
    int column = ((int)cColumn[0] - 97);  //Translate char to int   
    
    return new Move(row, column, this.color);
  }
}

