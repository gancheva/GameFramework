package GameEngine;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * 
 * @author thema 2.3 group 4
 * @version 17-Apr-12
 *
 */
@SuppressWarnings("serial")
public class BoardGui extends JPanel{
	int boardX;
	int boardY;
	GameController controller;
	public ArrayList<GamePlayButton> gameButtons= new ArrayList<GamePlayButton>();

	/**
	 * Constructor of BoardGui class. 
	 * @param controller
	 */
	public BoardGui(GameController controller) {
		this.controller=controller;
		//Get the sizes of the board
		boardX=controller.board.length;
		boardY=controller.board[0].length;
		
		//Use the sizes to set the layout
		setLayout(new GridLayout(boardX,boardY));
		
		//Add a button for every field on the game board
		for (int i=0;i<boardX*boardY;i++) {
			gameButtons.add(new GamePlayButton(controller.board[i/boardX][ i%boardY],gameButtons.size(), controller));			
		}
		
		//Add all buttons to this panel
		for (int i=0;i<gameButtons.size();i++) {
			add(gameButtons.get(i));
		}
		
		validate();
	}

}