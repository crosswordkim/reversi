package reversi;

import java.util.ArrayList;

public class ComputerPlayer extends Player {
	public static final int DEPTHLIMIT = 6;
	public int otherColor;

	public ComputerPlayer(int c) {
		super(c);
	}

	public Move getMove(Board b) {		
		if (color == 1) {
			otherColor = 2;
		} else {
			otherColor = 1;
		}
		
		ArrayList<Move> moves = b.getValidMoves(color);		
		int depth = 0;
		int leafRating = 0;
		int returnRating;
		int bestMoveRating = -10000;
		int alpha = -10000;
		int beta = 10000;
		
		Move bestMove = new Move(-1,-1,-1);  //Initialize bestMove,  meant to be replace by real move
		bestMove.rating = -10000;  
		
		for (int element = 0; element < moves.size(); element++) {
			/*
			 * This calculates the new variables to pass to simulateMin
			 */			
			Board passingBoard = new Board();
			b.clone(passingBoard);
			passingBoard.makeMove(moves.get(element));
			leafRating = moves.get(element).rate(b); ; // Calculates new leaf rating to pass down the tree
						
			returnRating = simulateMin(passingBoard, leafRating, depth, alpha, beta); 
			if (returnRating > bestMoveRating) { // Keeps track of the best move 
				bestMove = moves.get(element);
				bestMoveRating = returnRating;
				alpha = returnRating;
			}
		}
		
		return bestMove;
	}

	private int simulateMax(Board b, int leafRating, int depth, int alpha, int beta) {

		if ((depth > DEPTHLIMIT) || (b.existValidMove(color) == false)) {
			return leafRating;
		}
		int bestMove = -10000;
		int temp;
		depth++;
		

		ArrayList<Move> moves = b.getValidMoves(color);
		for (int element = 0; element < moves.size(); element++) {
			
			 //This calculates the new board to pass to simulateMin			 
			Board passingBoard = new Board();
			b.clone(passingBoard);
			passingBoard.makeMove(moves.get(element));
			int newLeafRating = leafRating + moves.get(element).rate(b); ; // Calculates new leaf rating to pass down the tree

			temp = simulateMin(passingBoard, newLeafRating, depth, alpha, beta); 
			if (temp > bestMove) { // Keeps track of the best move for this Player
				bestMove = temp;
				alpha = temp;
			}
			
			if (alpha >= beta) {
				element = moves.size(); //break out of the loop
			}
		}
		return bestMove;

	}

	private int simulateMin(Board b, int leafRating, int depth, int alpha, int beta) {		
		if ((depth > DEPTHLIMIT) || (b.existValidMove(otherColor) == false)) {
			return leafRating;
		}
		int bestMove = 10000;
		int temp;
		depth++;
		

		ArrayList<Move> moves = b.getValidMoves(otherColor);
		for (int element = 0; element < moves.size(); element++) {
			
			 // This calculates the new board to pass to simulateMax			 
			Board passingBoard = new Board();
			b.clone(passingBoard);
			passingBoard.makeMove(moves.get(element));
			
			int newLeafRating = leafRating - moves.get(element).rate(b); // Calculates new leaf rating to pass down the tree

			temp = simulateMax(passingBoard, newLeafRating, depth, alpha, beta); 
			if (temp < bestMove) { // Keeps track of the best move for this Player
				bestMove = temp;
				beta = temp;
			}
			
			if (alpha >= beta) {
				element = moves.size();  //Break out of the loop
			}
		}
		return bestMove;
	}
}
