package GameEngine;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

/**
 * 
 * @author thema 2.3 group 4
 * @version 17-Apr-12
 *
 */
public class GameController implements Observer {
	public String player;
	public GameGUI gui;
	public GameAI ai;
	int i = 0;
	public ServerCommunication server_comm;
	public IGameController iGame;
	public boolean myTurn = false;
	public int[][] board;
	ArrayList<JMenuItem> gaming = new ArrayList<JMenuItem>();
	ArrayList<JLabel> playing = new ArrayList<JLabel>();
	ArrayList<JButton> challengeButtons = new ArrayList<JButton>();
	String tmp;
	public boolean firstToMove = false;
	public boolean opponentFirstToMove = false;
	private String gameType = "";
	public int PLAYER1; 
	public int PLAYER2;
	public int boardSize; 
	
	/**
	 * Constructor for GameController where GameGUI, GameAI and ServerCommunication classes are initialized.
	 */
	public GameController() {
		server_comm = new ServerCommunication(this);
		server_comm.addObserver(this);
		gui = new GameGUI(this);
		ai = new GameAI();
	}	

	/**
	 * Overrides the method of Java's Observer class update(Observable o, Object arg). 
	 * This method is called every time when the GameController is notified for a new received message from the server. 
	 */
	@Override
	public void update(Observable o, Object arg) {
		String line = (String) arg;

		if(i>2) gui.messageLabel.setText(line);
		i++;
		
		if (line.indexOf("SVR HELP") > -1) { 
			gui.messageLabel.setText(line);
		}
		else if (line.indexOf("OK") > -1) { 
			gui.messageLabel.setText(line);
		}
		else if (line.indexOf("ERR") > -1) { 
			gui.messageLabel.setText(line);
		}
		else if (line.indexOf("DRAW") > -1) {
		 	gui.messageLabel.setText("Remise!");
		}
		else if (line.indexOf("WIN") > -1) {
			gui.messageLabel.setText("Je hebt gewonnen! "+line.substring(line.indexOf("COMMENT")+7,line.indexOf("PLAYERONESCORE") -2));	
		}
		else if (line.indexOf("LOSS") > -1) {
			gui.messageLabel.setText("Je hebt verloren! "+line.substring(line.indexOf("COMMENT")+7,line.indexOf("PLAYERONESCORE") -2));
		}
		else if (line.indexOf("MATCH") > -1)
		{
			System.out.println("");
			gui.messageLabel.setText("Opponent gevonden!");
			gui.makeGamePanel();
			String playerToStart = line.substring(line.indexOf("PLAYERTOMOVE: \"") + 15, line.indexOf("OPPONENT") - 3);	
			String opponent = line.substring(line.indexOf("OPPONENT: \"") + 11, line.indexOf("}") - 1);	
			gameType = line.substring(line.indexOf("GAMETYPE: \"") + 11, line.indexOf("PLAYERTOMOVE") - 3);
			
			if (gui.playerName.equals(playerToStart)) { 
				firstToMove = true; //our player begin the game 
				gui.p2Label.setText("Speler2: "+ opponent);	
				gui.oppName = opponent;
			}
			else {
				opponentFirstToMove = true;
				gui.p2Label.setText("Speler2: "+ playerToStart);	
				gui.oppName = playerToStart;
				myTurn=true;
			}
	
			/* Example of a server's message:
			 * Match aangeboden krijgen, bericht naar beide spelers:
			 * S: SVR GAME MATCH {GAMTYPE: "<speltype>", PLAYERTOMOVE: "<naam speler1>", OPPONENT: "<naam tegenstander>"}
			 * ->Nu bezig met een match, de inschrijving voor een speltype is vervallen.
			 */
		}
		else if (line.indexOf("YOURTURN") > -1) {
			gui.messageLabel.setText("Je bent aan de beurt!");
			myTurn = true;
			
			if (firstToMove) {	
				gui.initializeFrame(gameType);
				firstToMove = false;
				myTurn = false;
			}
			
			//with "!firstToMove" we insure that the AI will never had to make the fist move with which the turn would be chosen
			if(gui.AIButton.getState() == true && !firstToMove&&myTurn) {
				int tmp=ai.chooseMove();
				ai.playMove(tmp);
				ai.setMove(tmp);
				server_comm.setMessageToServer("move "+tmp);
				myTurn = false;
			}
		}
		//Handle the opponents input move 
		else if (line.indexOf("MOVE") > -1 && line.substring(line.indexOf("PLAYER")+9,(line.indexOf("DETAILS")-3)).equals(gui.oppName)) {		
			tmp=line.substring(line.indexOf("MOVE: \"") +7, line.indexOf("}")-1);	
			if (opponentFirstToMove) {
				gui.messageLabel.setText("The opponent chose to start as player " + tmp + "!"); 
				if (Integer.parseInt(tmp) == 1) { 
					PLAYER1 = ai.OPPONENT;
					PLAYER2 = ai.PLAYER;
				
				}
				else {
					PLAYER2 = ai.OPPONENT;
					PLAYER1 = ai.PLAYER;
					
				}
				opponentFirstToMove = false;
				myTurn=false;
				
				if(gui.selectedGame.equals("TicTacToe"))
					iGame = new TicTacToe.TicTacToeController(this);
				else
					iGame = new Othello.OthelloController(this);
				
				
				gui.panel.add(gui.gamePanel,BorderLayout.CENTER);
				
				gui.gamePanel.setVisible(true);
				repaintBoard();
				gui.frame.validate();
			} 
			else  processOpponentMove(Integer.parseInt(tmp));
			
			/*
			 * The server sends a message to the both players as the following:
			 * S: SVR GAME MOVE {PLAYER: "<speler>", DETAILS: "<reactie spel op zet>", MOVE: "<zet>"}
			 * ->Er is een zet gedaan, dit bericht geeft aan wie deze gezet heeft, wat de reactie van het spel erop is
			 */
		}
		else if (line.indexOf("CHALLENGER") > -1) { 
			gui.showChallengePanel(line);
			gui.messageLabel.setText("Je bent uitgedaagd! Details: " + line.substring(line.indexOf("{CHALLENGER") + 1, line.indexOf("}")-1));
			// example: SVR GAME CHALLENGE {CHALLENGER: "Sjors", GAMETYPE: "Guess Game", CHALLENGENUMBER: "1"}
		}
		else if (line.indexOf("CANCELLED") > -1) { 
			gui.messageLabel.setText("Uitdaging geannuleerd!");
		}
		else if (line.indexOf("PLAYERLIST") > -1) {
			gui.buildLobbyPanel(line);
		}
		else if (line.indexOf("GAMELIST") > -1) {
				String games = line.substring(line.indexOf("[")+1, line.indexOf("]"));
				String[] game = games.split(", ");
				for(int i = 0; i < game.length; i++){
					String gam = game[i].substring(1, game[i].length()-1);
					gaming.add(new JMenuItem(gam));
					gaming.get(i).addActionListener(gui);
					gui.games.add(gaming.get(i));
				}
		}
		
	}
	
	/**
	 * Handles the opponent's move.
	 * @param move
	 */
	public void processOpponentMove(int move) {
			iGame.setMove(move);				
	}

	/**
	 * This method's purpose is to send a new message to the ServerCommunication.
	 * @param mess
	 */
	public void setMessage(String mess) {
		if (mess.indexOf("subscribe") > -1) {
			if (mess.indexOf("TicTacToe") > -1) {
				iGame = new TicTacToe.TicTacToeController(this); 
			}
			if (mess.indexOf("Othello") > -1) { 
				//iGame = new Othello.OthelloController(this);	
			}
		}
		System.out.println(mess + "\n");
		server_comm.setMessageToServer(mess);	
		myTurn = false;
	}
	
	/**
	 * Method for closing the current connection with the server.
	 */
	public void closeConnection() {
		server_comm.closeConnection();
	}
	
	/**
	 * The main method of the Game Framework.  
	 * @param args
	 */
	public static void main(String[] args) {
		new GameController();
	}
	
	/**
	 * Setter for the new IP address of the server. 
	 * @param ip
	 */
	public void setIP(String ip) {
		server_comm.connect(ip);
	}
	
	/**
	 * Repaints the game's board.
	 */
	public void repaintBoard() {
		for (int i=0;i<boardSize;i++) {			
			if (board[i/board.length][i%board[0].length]==PLAYER1) {	
				try {      		 	   
					gui.gamePanel.gameButtons.get(i).image = ImageIO.read(new File("src//"+gui.selectedGame+"p1.png"));
					gui.gamePanel.gameButtons.get(i).repaint();
				} catch (IOException ex) { 
				         // handle exception... 
				} 
				gui.gamePanel.gameButtons.get(i).blocked=true;
			}
			
			if (board[i/board.length][i%board[0].length]==PLAYER2) {
				try {      		 	   
					gui.gamePanel.gameButtons.get(i).image = ImageIO.read(new File("src//"+gui.selectedGame+"p2.png"));
					gui.gamePanel.gameButtons.get(i).repaint();
				} catch (IOException ex) { 
				         // handle exception... 
				} 
				gui.gamePanel.gameButtons.get(i).blocked=true;
			}
		}
	}

}