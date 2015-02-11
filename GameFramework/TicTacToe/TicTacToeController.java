package TicTacToe;

import GameEngine.BoardGui;
import GameEngine.IGameController;
import GameEngine.GameController;

/**
 * 
 * @author thema 2.3 groep 4
 * @version 17-Apr-12
 * 
 */

public class TicTacToeController implements IGameController {
	
	AI ai;
	BoardGui view;
	GameController controller;
	
	/**
	 * Constructor of TicTacToeController class.
	 * @param controller
	 */
	public TicTacToeController (GameController controller) {
		this.controller = controller;
		ai = new AI(controller);
		controller.ai=ai;
		controller.board=new int[3][3];
		controller.boardSize=9;
		clearBoard();
		controller.gui.gamePanel= new BoardGui(controller);
		
	}
	
	/**
	 * This method clears the game board.
	 */
	private void clearBoard() {
		for (int i=0;i<9;i++) {
			controller.board[i/controller.board.length][ i%controller.board[0].length]=2;
		}
	}
	
	
	/**
	 * This method sets a legal move on the board.
	 * @param move
	 */
	@Override
	public void setMove(int move) {
		if(controller.myTurn) {
			controller.board[move/controller.board.length][ move%controller.board[0].length] = AI.PLAYER;
		}
		else {
			controller.board[move/controller.board.length][ move%controller.board[0].length] = AI.OPPONENT;
		}
		controller.repaintBoard();	
	}

	@Override
	public void setStartingPositions() {
		// TODO Auto-generated method stub
		
	}

}