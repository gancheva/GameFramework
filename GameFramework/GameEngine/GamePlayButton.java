package GameEngine;

import java.awt.Graphics; 
import java.awt.Image;
import java.io.File; 
import java.io.IOException; 
import javax.imageio.ImageIO; 
import javax.swing.JButton;

/**
 *  
 * @author thema 2.3 group 4
 * @version 17-Apr-12
 *
 */
@SuppressWarnings("serial")
public class GamePlayButton extends JButton{ 
	public int id;
	public int value;
	public Image image;
	GameController controller;
	public boolean blocked=false;
	private String player="p1";
	
	/**
	 * Constructor of GamePlayButton. It makes a new button with own values and unique id.
	 * @param value
	 * @param id
	 * @param controller
	 */
	public GamePlayButton(int value,int id,GameController controller) { 
    	super();
    	this.value = value;
    	this.controller = controller;
    	this.id = id;
    	
    	//Get the image for the button
    	if (value == controller.ai.PLAYER) {
    		if(controller.PLAYER1 == controller.ai.PLAYER) {
    			player="p1";
    		}
    		if(controller.PLAYER2 == controller.ai.PLAYER) {
    			player="p2";
    		}
    		try {      
        		image = ImageIO.read(new File("src//"+controller.gui.selectedGame+player+".png"));
        		//image = ImageIO.read(new File("x.png"));
        		repaint();
        	} catch (IOException ex) { 
        		System.out.println("Applicatie kan de afbeeldingen niet vinden");
        	} 		
    	}
    	else if (value == controller.ai.OPPONENT) {
    		if(controller.PLAYER1 == controller.ai.OPPONENT) {
    			player="p1";
    		}
    		if(controller.PLAYER2 == controller.ai.OPPONENT) {
    			player="p2";
    		}
    		try {      
        		image = ImageIO.read(new File("src//"+controller.gui.selectedGame+player+".png"));
        		repaint();
        	} catch (IOException ex) { 
                System.out.println("Applicatie kan de afbeeldingen niet vinden");
        	} 
    	}
    	else{
        	try {      
        		image = ImageIO.read(new File("src//leeg.png"));
        		//image = ImageIO.read(new File("leeg.png"));
        		repaint();
        	} catch (IOException ex) { 
        		System.out.println("Applicatie kan de afbeeldingen niet vinden");
        	}
        	addActionListener(new ButtonListener(this));
        }
 	
	} 
	
	/**
	 * Paints the button. 
	 * @param g
	 */
	@Override 
	public void paintComponent(Graphics g) { 
		// Stretch button to match size of button
		Image scaledImage = image.getScaledInstance(getSize().width, getSize().height, Image.SCALE_DEFAULT);  
		g.drawImage(scaledImage, 0, 0, null); 
     } 
	
}