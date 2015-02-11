package GameEngine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author thema 2.3 group 4
 * @version 17-Apr-12
 *
 */
public class ButtonListener implements ActionListener{
	GamePlayButton button;
	String player;
	
	/**
	 * Constructor of ButtonListener.
	 * @param button
	 */
	public ButtonListener(GamePlayButton button) {
		this.button=button;	
	}

	/**
	 * The implemented method of Java's ActionListener class. 
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if((!button.blocked)&&button.controller.myTurn) {
    		button.controller.server_comm.setMessageToServer("move "+button.id);
    		button.controller.iGame.setMove(button.id);
			//after sending our last move to the server it's the opponent's turn 
			button.controller.myTurn = false;			
		}
	}
	
}

