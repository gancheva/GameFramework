package GameEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

/**
 * 
 * @author thema 2.3 group 4
 * @version 17-Apr-12
 *
 */
public class ServerCommunication extends Observable {

	private Socket socket;
	private PrintWriter out;
    private BufferedReader in;
    public boolean userConnected = false;
    private String messageToServer = "";
    public MyThread listener = null;
	GameController controller;
	
	/**
	 * Constructor of ServerCommunication class.
	 * @param controller
	 */
	public ServerCommunication (GameController controller) {
		this.controller=controller;
	}

	/**
	 * Connects the game framework with the server with the help of socket.  
	 * @param ip
	 */
	public void connect (String ip) {
		try {	
			//making a socket to connect with the server
			socket = new Socket(ip, 7789);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			userConnected = true;     
			listener = new MyThread();
			listener.start();  
		} catch (UnknownHostException e) {
			controller.gui.messageLabel.setText("Er draait geen server op dit ip adres");
		}catch(IOException ioe){ 
    	   	System.out.println (ioe.getCause());
    	    System.out.println (ioe.getMessage());
		}
	}

	/**
	 * Sends a string to the server with the socket's output stream. 
	 */
	private synchronized void send() {
		if (messageToServer != null) {
			//sending a message to the server
			out.println(messageToServer);
			out.flush();
			messageToServer = null;
		}
	}
	
	/**
	 * Closes the socket and breaks the connection. 
	 */
	public synchronized void closeConnection () {
		try {
			//this boolean variable is made to control the running of 'MyThread' thread
			userConnected = false;
			out.close();
			in.close();
			socket.close(); 
			controller.gui.messageLabel.setText("The connection was closed!");
		} catch (IOException ioe) {}
	}
	
	/**
	 * Setter for the message which will be send to the server.  
	 * @param newMessage
	 */
	public synchronized void setMessageToServer(String newMessage) {
		messageToServer = newMessage;
		send();
	}

	/**
	 * Inner class MyThread which only purpose is to listen to the server's response.
	 * The class extends Java Thread class. 
	 */
	public class MyThread extends Thread{
		
		/**
		 * The run method of the thread. 
		 * It runs while the user is connected to the server. 
		 */
		public void run() {
			// while user is connected communicate with the server
			while (userConnected) {
				try {
					//reading the answer in a String
					String line;
					while ((line = in.readLine()) != null && line.length() != 0) {
						//notifying the GameController for the change/the new data send from the server 
						setChanged();
						notifyObservers(line);								
					} 
				} catch (IOException ioe) {}		
			}
	
		}
	}
	
}


						


