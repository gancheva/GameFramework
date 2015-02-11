package GameEngine;

import TicTacToe.AI;

/**
 * 
 * @author thema 2.3 group 4
 * @version 17-Apr-12
 * 
 */
public interface IGameController {
	
	AI ai = null;
/**
 * Sets the move on the board. This method is implemented in 
 * OthelloController and TicTacToeController classes. 
 * @param move
 */
	public void setMove(int move);
public void setStartingPositions();
	
}
