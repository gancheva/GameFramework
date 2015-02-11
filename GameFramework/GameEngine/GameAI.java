package GameEngine;

/**
 * 
 * @author thema 2.3 group 4
 * @version 17-Apr-12
 *
 */
public class GameAI {
	public static final int PLAYER        = 0; 
	public static final int OPPONENT 	  = 1; 
	public  static final int EMPTY        = 2;
	
	public  static final int PLAYER_WIN   = 0;
	public  static final int DRAW         = 1;
	public  static final int UNCLEAR      = 2;
	public  static final int OPPONENT_WIN = 3;
	public String myPiece;
	public String opponentPiece;
	public char playerChar, opponentChar;
	
	/**
	 * Constructor without parameters.
	 */
	public GameAI () {}
	
	/**
	 * Inner class Best used by the added to the framework games.
	 */
	public class Best {
       public int row, column, val;
       
       /**
        * Constructor of the class Best with one parameter. 
        * @param v
        */
       public Best( int v ) { 
    	   this( v, 0, 0 ); 
       }
      
       /**
        * Constructor of the class Best with three parameter. 
        * @param v
        */
       public Best( int v, int r, int c ) { 
    	   val = v; row = r; column = c; 
       }
       
    }

	/**
	 * Sets the move on the board.
	 * @param move
	 */
	public void setMove(int move) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Handles the first move during a game. 
	 * @param parseInt
	 */
	public void processFirstMove(int parseInt) {}

	/**
	 * Lets the game's AI to chooses a move.  
	 * @return move
	 */
	public int chooseMove() {
		// TODO Auto-generated method stub
		return 5;
	}
	
	/**
	 * Lets the game's AI to play a move. 
	 * @param chooseMove
	 */
	public void playMove(int chooseMove) {
		// TODO Auto-generated method stud
	}
	
	/**
	 * Lets the game's AI to place a move on the board. 
	 */
	public void place() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Lets the game's AI to place a move on the board. 
	 * @param i
	 * @param j
	 * @param player2
	 * @param board
	 */
	public void place(int i, int j, int player2, int[][] board) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Lets the game's AI to place a move on the board. 
	 * @param i
	 * @param j
	 * @param player2
	 */
	public void place(int i, int j, int player2) {
		// TODO Auto-generated method stub
	}
	
}
