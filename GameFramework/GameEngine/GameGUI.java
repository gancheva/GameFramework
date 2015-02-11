package GameEngine;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author thema 2.3 group 4
 * @version 17-Apr-12
 *
 */
public class GameGUI implements ActionListener {
	int challengeNumber;
	
	//The frames for building the GUI
	JFrame challengeFrame;
	JFrame loginframe = new JFrame("Login");
	JFrame frame = new JFrame("GUI");
	JFrame initialize = new JFrame();
	boolean madePanel=false;

	JPanel lobbyPanel=new JPanel();
	JPanel initpanel = new JPanel(new GridLayout(3,3));
	public JPanel panel = new JPanel(new BorderLayout());
	public BoardGui gamePanel;
	JPanel summary = new JPanel(new GridLayout(6,1));
	JPanel loginpanel = new JPanel(new GridLayout(3,3));
	JMenuBar menubar = new JMenuBar();
	JMenu menu = new JMenu("Menu");
	JMenu games = new JMenu("Spellen");
	JMenuItem login = new JMenuItem("login");
	JMenuItem logout = new JMenuItem("logout");
	JLabel label = new JLabel("Loginnaam : ");
	public String selectedGame= "";
	JLabel turn = new JLabel("Wilt u als eerste of tweede?");
	JLabel xoro = new JLabel("Wilt u X zijn of O?");
	JLabel setIP = new JLabel("IP-adres : ");
	JTextField loginname; 
	public String playerName = "";
	JTextField ip = new JTextField("10.0.0.9");
	//JTextField ip = new JTextField("127.0.0.1");
	
	JButton submit = new JButton("submit");
	JButton initsubmit = new JButton("submit");
	JRadioButton first = new JRadioButton("1e");
	JRadioButton second = new JRadioButton("2e");
	boolean gameBusy;
	boolean loggedIn;
	String whatGame;
	String initString = "";
	JButton acceptChallenge=new JButton("Accepteren");
	JButton rejectChallenge=new JButton("Weigeren");
	JButton refresh = new JButton("Refresh");
	JLabel p1Label=new JLabel("Speler 1:");
	JLabel p2Label=new JLabel("Speler 2:");
	JLabel messageLabel=new JLabel("Label");
	GameController controller;
	private JPanel messagePanel=new JPanel();
	public String oppName;
	public int myChoice;
	JComboBox challengeGames = new JComboBox();
	public Checkbox AIButton;
	String name = "";
	JButton forfeit = new JButton("forfeit");
	
	/**
	 * Constructor of GameGUI class. 
	 * @param controller
	 */
	@SuppressWarnings("static-access")
	public GameGUI(GameController controller) {
		this.controller = controller;
		loginname= new JTextField(getRandomName());
		gameBusy = false;
		loggedIn = false;
		login.addActionListener(this);
		menu.add(login);
		logout.addActionListener(this);
		menu.add(logout);
		menubar.add(menu);
		menubar.add(games);
		games.setVisible(false);
		summary.add(p1Label);
		AIButton = new Checkbox("Play as AI");
		AIButton.setState(false);
		summary.add(AIButton);
		summary.add(p2Label);
		summary.setBorder(new TitledBorder("Summary"));
		panel.add(summary, BorderLayout.WEST);
		messagePanel.add(messageLabel);
		messagePanel.setBorder(new TitledBorder("MessagePanel"));
		panel.add(messagePanel, BorderLayout.SOUTH); 
		panel.validate();
		panel.setVisible(true);
		frame.setJMenuBar(menubar);
		frame.add(panel);
		frame.setSize(500, 500);
		frame.validate();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		submit.addActionListener(this);
		loginpanel.add(setIP);
		loginpanel.add(ip);
		loginpanel.add(label);
		loginpanel.add(loginname);
		loginframe.validate();
		loginpanel.add(submit, BorderLayout.SOUTH);
		
		loginframe.add(loginpanel);
		loginframe.setSize(300, 100);
		loginframe.setVisible(false);
		
	}

	/**
	 * Builds the lobby. 
	 * @param line
	 */
	public void buildLobbyPanel(String line) {
		lobbyPanel.removeAll();
		//lobbyPanel=new JPanel();
		lobbyPanel.setBorder(new TitledBorder("Game Lobby"));
		lobbyPanel.setLayout(new GridBagLayout());
		GridBagConstraints lobbyConstraints=new GridBagConstraints();
		lobbyConstraints.gridx=0;
		lobbyConstraints.gridy=0;
		lobbyConstraints.fill=GridBagConstraints.HORIZONTAL;
		lobbyPanel.add(refresh, lobbyConstraints);
		refresh.addActionListener(this);
		lobbyConstraints.gridx++;
		
		challengeGames.removeAllItems();
		for(int j = 0; j < controller.gaming.size(); j++) {
            challengeGames.addItem(controller.gaming.get(j).getText());
            challengeGames.addActionListener(this);
		}
		lobbyPanel.add(challengeGames, lobbyConstraints);
		lobbyConstraints.gridy++;
		
		lobbyConstraints.gridx=0;
			
		String players = line.substring(line.indexOf("[")+1, line.indexOf("]"));
		String[] player = players.split(", ");
		
		for(int i = 0; i < player.length; i++) {
			String plr = player[i].substring(1, player[i].length()-1);
			if(!plr.equals(name)){
				JLabel plyr=new JLabel(plr);
				controller.playing.add(plyr);
				lobbyPanel.add(plyr,lobbyConstraints);
				lobbyConstraints.gridx++;
				JButton chal=new JButton("Challenge");				
				lobbyPanel.add(chal,lobbyConstraints);
				lobbyConstraints.gridy++;
				lobbyConstraints.gridx=0;
				controller.challengeButtons.add(chal);
				chal.addActionListener(this);
			}
		}
		lobbyPanel.validate();
		lobbyPanel.revalidate();
		panel.add(lobbyPanel, BorderLayout.CENTER);
		panel.validate();
	}
	
	/**
	 * Getter for the game's type.
	 * @return
	 */
	public String getGame() {
		return whatGame;
	}
	
	/**
	 * Getter for the frame.
	 * @return frame
	 */
	public Component getFrame() {
		return frame;
	}
	
	/**
	 * The implemented method of Java's ActionListener class. 
	 * @param e 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == login) {
			if(loggedIn) {
				messageLabel.setText("U bent al ingelogd");
			}
			else {
				loginframe.setVisible(true);
			}
		}
		else if(e.getSource() == logout) {
			if(loggedIn) {
				controller.setMessage("logout");
				loggedIn = false;
				games.setVisible(false);
				//gamePanel.setVisible(false);
			}
			else {
				messageLabel.setText("U bent al uitgelogd");
			}
		}
		else if(e.getSource() == submit) {
			String ipAdres = ip.getText();
			controller.setIP(ipAdres);
			name = loginname.getText();
			playerName = name;
			controller.setMessage("login "+ name);
			controller.setMessage("get gamelist");
			loggedIn = true;
			games.setVisible(true);
			loginframe.dispose();
			p1Label.setText("Speler1: "+name);
			frame.setTitle("Welkom "+name);
			controller.setMessage("get playerlist");
		}
		else if(e.getSource() == initsubmit) {
			//controller.firstToMove=false;
			initString = initString.substring(0,1);
			controller.setMessage("move " + initString);

			myChoice = Integer.parseInt(initString);
			if (myChoice == 1) {
				controller.PLAYER1 = controller.ai.PLAYER;
				controller.PLAYER2 = controller.ai.OPPONENT;
			}
			else {
				controller.PLAYER2 = controller.ai.PLAYER;
				controller.PLAYER1 = controller.ai.OPPONENT;
				
			}
			
			
            gamePanel.setVisible(true);
            controller.iGame.setStartingPositions();
            controller.repaintBoard();
			initialize.dispose();
		}
		else if(e.getSource() == first) {
			initString += first.getText();
		}
		else if(e.getSource() == second) {
			initString += second.getText();
		}
		else if(e.getSource() == acceptChallenge) {
			controller.setMessage("challenge accept "+challengeNumber);
			//panel.add(gamePanel,BorderLayout.CENTER);
			//madePanel=true;
			challengeFrame.dispose();
		}
		else if(e.getSource() == rejectChallenge) {
			initpanel.setVisible(false);
			challengeFrame.dispose();
			//controller.setMessage("");
		}
		else if(e.getSource() == refresh) {
			controller.setMessage("get playerlist");
		}
		else if(e.getSource() == forfeit) {
			controller.setMessage("forfeit");
		}
		
		for(int i = 0; i < controller.gaming.size(); i++) {
			if(e.getSource() == controller.gaming.get(i)) {
				String game = controller.gaming.get(i).getText();
				selectedGame=game;
				controller.setMessage("subscribe " + game);
				whatGame = game;
				gameBusy = true;
			}
		}
		
		for(int i = 0; i < controller.challengeButtons.size(); i++) {
			if(e.getSource() == controller.challengeButtons.get(i)) {
				String player = controller.playing.get(i).getText();
				selectedGame = (String) challengeGames.getSelectedItem();
				controller.setMessage("challenge \"" + player+"\" \""+ selectedGame + "\"");
			}
		}
		
	}
	
	/**
	 * Makes the window for choosing the player's turn in the beginning. 
	 * @param game
	 */
	public void initializeFrame(String game) {
		selectedGame=game;
		initpanel.setLayout(new GridBagLayout());
		GridBagConstraints initConstraints=new GridBagConstraints();
		initConstraints.fill=GridBagConstraints.HORIZONTAL;
		initConstraints.gridx=0;
		initConstraints.gridy=0;
		initConstraints.gridwidth=2;
		initpanel.add(turn,initConstraints);
		initConstraints.gridy++;
		initConstraints.gridwidth=1;
		initpanel.add(first,initConstraints);
		initConstraints.gridx++;
		initpanel.add(second,initConstraints);
		
		ButtonGroup group = new ButtonGroup();
		group.add(first);
		group.add(second);
		
		first.addActionListener(this);
		second.addActionListener(this);
		
		initsubmit.addActionListener(this);
		
		initConstraints.gridx=0;
		initConstraints.gridy++;
		initConstraints.gridwidth=2;
		initpanel.add(initsubmit,initConstraints);
		initialize.add(initpanel);
		initialize.validate();
		initialize.setSize(500, 100);
		initialize.setVisible(true);
		
		if(selectedGame.equals("TicTacToe")) {
			controller.iGame = new TicTacToe.TicTacToeController(controller);
		}
		if(selectedGame.equals("Othello")) {
			controller.iGame = new Othello.OthelloController(controller); 
		}
		panel.add(gamePanel,BorderLayout.CENTER);

		gamePanel.setVisible(false);
		panel.validate();
	}

	/**
	 * Showing the challenge panel. 
	 * @param line
	 */
	public void showChallengePanel(String line) {
		challengeFrame=new JFrame();
		challengeFrame.setVisible(true);
		JPanel challengePanel=new JPanel();
		challengePanel.setLayout(new GridBagLayout());
		GridBagConstraints challengeConstraints=new GridBagConstraints();
		String challenger=line.substring(line.indexOf("CHALLENGER")+13,line.indexOf("GAMETYPE")-3);
		
		String game=line.substring(line.indexOf("GAMETYPE")+11,line.indexOf("CHALLENGENUMBER")-3);
		selectedGame=game;
		challengeNumber=Integer.parseInt(line.substring(line.indexOf("CHALLENGENUMBER")+18,line.indexOf("}")-1));
		challengeConstraints.gridx=0;
		challengeConstraints.gridy=0;
		challengeConstraints.gridwidth=2;
		challengePanel.add(new JLabel(challenger  +" daagt je uit voor een spelletje " + game),challengeConstraints);
		challengeConstraints.gridy++;
		challengeConstraints.gridwidth=1;
		acceptChallenge.addActionListener(this);
		rejectChallenge.addActionListener(this);
		challengePanel.add(acceptChallenge,challengeConstraints);
		challengeConstraints.gridx++;
		challengePanel.add(rejectChallenge,challengeConstraints);
		challengeFrame.add(challengePanel);
		challengeFrame.pack();
		challengeFrame.validate();
	}
	
	/**
	 * Making the game's panel. 
	 */
	public void makeGamePanel() {
		lobbyPanel.setVisible(false);
		madePanel=true;
		summary.add(forfeit);
		forfeit.addActionListener(this);
		frame.validate();
	}
	
	/**
	 * Method for choosing a random user's name. 
	 * @return random user's name
	 */
	public String getRandomName() {
		String[] names={"Jan","Henk","Wilco","Bart","Sinterklaas","Jozef","Klaas","Dennis Bergkamp","Gerard Joling","Anonymous"};
		Random r=new Random();
		int i=r.nextInt(names.length);
		return names[i];
	}
	
}
