package TicTacToe;

import GameEngine.GameAI;
import GameEngine.GameController;

/**
 * 
 * @author thema 2.3 groep 4
 * @version 17-Apr-12
 * 
 */

public class AI extends GameAI{
	

	private int side = PLAYER; 
	private int position = UNCLEAR;
	GameController controller;
	public int tpm;

	/**
	 * Constructor of the TicTacToe game's AI.
	 * @param controller
	 */
	public AI(GameController controller) {
		super();
		this.controller = controller;		
	}
	
	/**
	 * This method chooses the best possible move for the current player.
	 * @return the best move as an int
	 * @see Best
	 */
	@Override
	public int chooseMove() {
	    Best best = chooseMove(PLAYER);
	    return best.row * 3 + best.column;
    }
	
	/**
	 * This method checks whether the game is finished and how by the making of a move. 
	 * @return the game's result at this moment
	 */
	public int positionValue() {
		if (isAWin(PLAYER)) return PLAYER_WIN; 
		else if (isAWin(OPPONENT)) return OPPONENT_WIN; 
		else if (boardIsFull()) return  DRAW;
		return UNCLEAR;
	}
    
    /**
     * Finds optimal move.
     * @param side
     * @return variable of the type Best
     * @see Best
     */
	private Best chooseMove( int side ) {
		int opp;              // The other side
		Best reply;           // Opponent's best reply
		int simpleEval;       // Result of an immediate evaluation
		int bestRow = 0;
		int bestColumn = 0;
		int value = OPPONENT_WIN; 	
		if( ( simpleEval = positionValue() ) != UNCLEAR )
			return new Best( simpleEval );
		
		if (side == OPPONENT) opp = PLAYER;
		else opp = OPPONENT;
		
		//implementeren m.b.v. recursie/backtracking
		for (int i = 0; i < 9; i ++) {
			if (squareIsEmpty( i/3, i%3 )) {
				
				place(i/3, i%3, side);
				
				if(positionValue() == UNCLEAR) reply = chooseMove(opp);
				else { 
					reply = new Best(positionValue(), i/3, i%3);
					place(i/3, i%3, EMPTY);
					return reply;
				}
				
				place(i/3, i%3, EMPTY);
				
				if (reply.val > value) { 
					value = reply.val;
					bestRow = i / 3;
					bestColumn = i % 3;
				}	
			}
		}
	    return new Best(value, bestRow, bestColumn);
    }
	
    /**
     * Checks if the move is possible.
     * @param move
     * @return boolean
     */
    public boolean moveOk(int move) {
    	if ( move>=0 && move <=8 && controller.board[move/3][move%3] == EMPTY )
    			return true;
    	else return false;
    }
    
    
    /**
     * Plays the move.
     * @param move 
     */
    public void playMove(int move) {
    	controller.board[move/3][ move%3] = this.side;
		int tmp=positionValue();
		if (tmp==0) {
			System.out.println("Je Hebt Gewonnen");
		}
		if (tmp==3) {
			System.out.println("Je Hebt Verloren");
		}
		if (tmp==1) {
			System.out.println("Remise");
		}
		controller.setMessage("move " + move);
	}
	
    /**
     * Checks if the board is full.
     * @return whether the board is full
     */
	public boolean boardIsFull( ) {
		for (int i = 0; i < 9; i ++)
			if (controller.board[i/3][i%3] == EMPTY) return false;
		return true;
	}

	/**
	 * Checks if the 'side' has won.
	 * @param side
	 * @return whether 'side' has won in this position
	 */
	public boolean isAWin( int side ) {
		for (int i = 0, j = 0; i < 3 && j < 3; i ++, j ++) {
			if ( controller.board[i][0] == controller.board[i][1] &&  controller.board[i][1] == controller.board[i][2] && controller.board[i][2] == side) return true;
			if ( controller.board[0][j] == controller.board[1][j] &&  controller.board[1][j] == controller.board[2][j] && controller.board[2][j] == side) return true;
		}
		
		if ((controller.board[0][0] == controller.board[1][1] && controller.board[1][1] == controller.board[2][2] && controller.board[2][2] == side) || (controller.board[0][2] == controller.board[1][1] && controller.board[1][1] == controller.board[2][0] && controller.board[2][0] == side)) return true;
	    
	    return false;
    }

	/**
	 * Play a move, possibly clearing a square.
	 * @param row
	 * @param column
	 * @param piece
	 */
	public void place( int row, int column, int piece ) {
		controller.board[ row ][ column ] = piece;
	}
	
	/**
	 * Checks if the position is free.
	 * @param row
	 * @param column
	 * @return whether the square is empty
	 */
	private boolean squareIsEmpty( int row, int column ) {
		return controller.board[ row ][ column ] == EMPTY;
	}
	
	/**
	 * @return returns the board with the pieces on it as a String
	 */
	public String toString() {	
		String boardView = "";
		
		for (int i = 0; i < 9; i ++ ) {
				if (controller.board[i/3][i%3] == OPPONENT) boardView = boardView + " " + opponentChar;
				else if (controller.board[i/3][i%3] == PLAYER) boardView = boardView + " " + playerChar;
				else boardView = boardView + " " + "_";
			
				if ((i == 2) || (i == 5)) boardView += "\n";
		}
		return boardView;   
	}  
	
	/**
	 * Checks whether the game is over.
	 * @return returns false if the game is not finished and true in the other case
	 */
	public boolean gameOver() {
	    this.position = positionValue();
	    return this.position != UNCLEAR;
    }
    
	/**
	 * Checks if the game has a winner.
	 * @return String
	 */
    public String winner() {
        if      (this.position == PLAYER_WIN) return "player";
        else if (this.position == OPPONENT_WIN   ) return "opponent";
        else                                  return "nobody";
    }
	
}
