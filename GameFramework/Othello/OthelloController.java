package Othello;

import GameEngine.BoardGui;
import GameEngine.IGameController;
import GameEngine.GameController;

/**
 * 
 * @author thema 2.3 groep 4
 * @version 17-Apr-12
 * 
 */

public class OthelloController implements IGameController {
	
	AI ai;
	BoardGui view;
	GameController controller;
	public int boardSize;
	
	/**
	 * Constructor of OthelloController.
	 * @param controller
	 */
	public OthelloController (GameController controller) {
		this.controller = controller;
		controller.board=new int[8][8];
		controller.boardSize=64;
		ai = new AI(controller);
		controller.ai = ai;
		setStartingPositions();	
		controller.gui.gamePanel= new BoardGui(controller);
		controller.repaintBoard();
	}
	
	/**
	 * Initializing the game's board at the beginning of the game. 
	 */
	@Override
	public void setStartingPositions() {
		for (int i=0;i<controller.boardSize;i++) {
			controller.board[i/controller.board.length][ i%controller.board[0].length]=2;
		}
		
		System.out.println("Player: 1"+controller.PLAYER1);
		System.out.println("Player: 2"+controller.PLAYER2);
		
		controller.board[3][3]=controller.PLAYER1;
		controller.board[4][3]=controller.PLAYER2;
		controller.board[3][4]=controller.PLAYER2;
		controller.board[4][4]=controller.PLAYER1;
	}
	
	/**
	 * Places the move on the board.
	 * @param move 
	 */
	@Override
	public void setMove(int move) {
		if(controller.myTurn) {
			setMove(move, AI.PLAYER);
		}
		else {
			setMove(move, AI.OPPONENT);
		}
		controller.repaintBoard();
	}
	
	/**
	 * Places the move on the board after checking whether it is an illegal move.
	 * @param move
	 * @param side
	 */
	public void setMove(int move, int side) {
		setMove(move, side, controller.board);
	}
	
	public void setMove(int move, int side, int[][] board) {
		int col=move/8;
		int row=move%8;
		board[col][row]=side;
		for (int colCheck=col-1;colCheck <=col+1; colCheck++) {
			for (int rowCheck=row-1;rowCheck <=row+1; rowCheck++) {
				if(colCheck < 0 || colCheck >= 8 || 
					rowCheck < 0 || rowCheck >= 8 ) continue;
				if(board[colCheck][rowCheck] == getOppositeSide(side)) {
					//check in that direction
					int directionX = colCheck-col;
					int directionY = rowCheck-row;
					int x=colCheck+directionX;
					int y=rowCheck+directionY;
					boolean validDir = false;
					while (x >= 0 && x < 8 && 
		    				y >= 0 && y < 8  &&
		    				board[x][y] != AI.EMPTY) {
						if (board[x][y] == side) {validDir=true; break;}
						x+=directionX;
						y+=directionY;
					}
					//TODO clean-up
					if (validDir) {
						x=colCheck;
	    				y=rowCheck;
	    				while (x >= 0 && x < 8 && 
	    	    				y >= 0 && y < 8  &&
	    	    				board[x][y] != AI.EMPTY) {
	    					if (board[x][y] == side) break;
	    					board[x][y] = side;
	    					x+=directionX;
	    					y+=directionY;
	    				}
					}
				}
			}
		}
	}
	
	/**
	 * Exchanging the players. 
	 * @param side
	 * @return the player or the opponent depending on who's not representing the side  
	 */
	private int getOppositeSide(int side){
    	//simple math, a+b-b=a AND a+b-a=b
    	return AI.PLAYER+AI.OPPONENT-side;
    }

	
}
