package Othello;

import java.util.ArrayList;
import GameEngine.GameAI;
import GameEngine.GameController;

/**
 * 
 * @author thema 2.3 groep 4
 * @version 17-Apr-12
 * 
 */
public class AI extends GameAI {
	
	public static int DEPTH = 3;
	private int side = PLAYER; 
	private int position = UNCLEAR;
	private int white = 0;
	private int black = 0;
	public static final int WHITEPIECE = 0;
	public static final int BLACKPIECE = 1;
	public GameController controller;

	/**
	 * Constructor of the game's AI.
	 * @param controller
	 */
	public AI(GameController controller) {
		super();
		this.controller = controller;
		this.controller = controller;
		white = 0;
		black = 0;
		clearBoard();
	}
	
	/**
	 * For the given move and side it is checked if it is allowed according to the rules of Othello.
	 * @param move
	 * @param side
	 * @param board
	 * @return whether move is valid
	 */
    public boolean moveOk(int move, int side, int[][] board) {
    	int col=move/8;
    	int row=move%8;
    	if (col < 0 || col >= 8 || row < 0 || row >= 8 || board[col][row] != EMPTY) { 
    		return false;
    	}
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
    				while (x >= 0 && x < 8 && 
    	    				y >= 0 && y < 8  &&
    	    				board[x][y] != EMPTY) {
    					if (board[x][y] == side) return true;
    					x+=directionX;
        				y+=directionY;
    					
    				}
    			}
    		}
    	}
    	return false;
    }
		
	/**
	 * Clears the game board. 
	 */
	public void clearBoard( ) {
		for (int i = 0; i < 64; i ++) controller.board[i/8][i%8] = EMPTY;
	}
	
	/**
	 * If both of the sides don't have any moves left, then the game is over.
	 * @param board
	 * @return whether the game is over or not
	 */
	public boolean boardIsFull(int[][] board) {
		return possibleMoves(PLAYER, board).isEmpty()&&possibleMoves(OPPONENT, board).isEmpty();
	}
	
	
	/**
	 * Checks if the board is full and if there is a winner.
	 * @param side
	 * @param board
	 * @return boolean
	 */
	public boolean isAWin( int side, int[][] board) {
		if (boardIsFull(board)) {
			countColors();
			if (white != black) return true;
			else return false;
		}
		return false;
	}
	
	/**
	 * Counts the pieces on the board of each color. 
	 */
	private void countColors() {	
		white = 0;
		black = 0;
		for(int i = 0; i < 64; i++) {
			if(controller.board[i/8][i%8] == WHITEPIECE) {
				white ++;
			}
			else if(controller.board[i/8][i%8] == BLACKPIECE) {
				black ++;
			}
		}
	}
	
	/**
	 * Plays a move, possibly clearing a square.
	 * @param row
	 * @param column
	 * @param piece
	 */
	@Override
	public void place( int row, int column, int piece ) {
		controller.board[ row ][ column ] = piece;
	}
	
	/**
	 * The given move (split up in row and column) for the given side will be updated on the given board.
	 * It is assumed that the validity of the move has already been checked at this point
	 * @param row
	 * @param col
	 * @param piece
	 * @param board
	 */
	@Override
	public void place( int row, int col, int piece, int[][] board ) {
		//place a disc on the given row and column
		board[row][col]=piece;
		int temp = col;
		col = row;
		row = temp;
		//for all surrounding squares
		for (int colCheck=col-1;colCheck <=col+1; colCheck++) {
    		for (int rowCheck=row-1;rowCheck <=row+1; rowCheck++) {
    			if(colCheck < 0 || colCheck >= 8 || 
    				rowCheck < 0 || rowCheck >= 8 ) continue;
    			//check whether square is from opponent
    			if(board[colCheck][rowCheck] == getOppositeSide(piece)) {
    				//check in that direction to see if opponent is enclosed 
    				//by player's discs
    				int directionX = colCheck-col;
    				int directionY = rowCheck-row;
    				int x=colCheck+directionX;
    				int y=rowCheck+directionY;
    				boolean validDir = false;
    				while (x >= 0 && x < 8 && 
    	    				y >= 0 && y < 8  &&
    	    				board[x][y] != EMPTY) {
    					if (board[x][y] == piece) {validDir=true; break;}
    					x+=directionX;
    					y+=directionY;
    				}
    				// if direction was valid, flip the enclosed discs of opponent
    				if (validDir) {
    					x=colCheck;
        				y=rowCheck;
        				while (x >= 0 && x < 8 && 
        	    				y >= 0 && y < 8  &&
        	    				board[x][y] != EMPTY) {
        					if (board[x][y] == piece) break;
        					board[x][y] = piece;
        					x+=directionX;
        					y+=directionY;
        				}
    				}
    			}
    		}
		}
	}
    
	/**
	 * Checks who's the winner if there is one. 
	 * @param board
	 * @return the number symbolizing a player, an opponent or an empty square
	 */
    public int winner(int[][] board) {
    	int score = positionValue(board);
    	if(score==0) return EMPTY;
    	else if(score>0) return PLAYER;
    	else return OPPONENT;
    }
    
    /**
     * Getter for the end score.
     * @param board
     * @return the score
     */
    public int getEndScore(int[][] board) {
    	int score = positionValue(board);
    	if(score==0) { return 0; }
    	else if (score>0) { return 999; }
    	else { return -999; }
    }
   
    /**
     * For the given side and board, the possible moves are returned in an ArrayList
     * @param side
     * @param board
     * @return all the possible moves 
     */
    public ArrayList<Integer> possibleMoves(int side, int[][] board) {
    	ArrayList<Integer> moves = new ArrayList<Integer>();
    	for (int i = 0; i < 64; i++){
    		if (moveOk(i, side, board)) moves.add(i);
    	}
    	return moves;
    }
    
    /**
     * Ugly greedy algorithm check, compare board at enddepth with current board
     * to give a relative score based on the gained discs
     * 
     * @param board to be checked
     * @return the value of this position
     */
    public int positionValue(int[][] board) {
    	int score = 0;
    	int player = 0;
    	int opp = 0;
    	if (boardIsFull(board)) {
    		for(int[] i : board) {
        		for (int j : i) {
        			if (j==PLAYER) {
        				score++;
        				player++;
        			} else if (j==OPPONENT) { 
        				score--;
        				opp++;
        			}
        		}
        	}
    		if(score==0) {  return 0; }
        	else if (score>0) {  return 999; }
        	else { System.out.println("DRRDRR"+score+" "+player+" "+opp); return -999; }
    	}
    	
    	
    	for(int[] i : board) {
    		for (int j : i) {
    			if (j==PLAYER) { 
    				score++;
    			} else if (j==OPPONENT) { 
    				score--;
    			}
    		}
    	}
    	for(int[] i : controller.board) {
    		for (int j : i) {
    			if (j==PLAYER) { 
    				score--;
    			} else if (j==OPPONENT) { 
    				score++;
    			}
    		}
    	}
    	return score;
    }
    
    /**
     * This method is to be called from an external class and will return the best move possible according to the used algorithm
     * @return the best move possible
     */
    public int chooseMove() {
    	Node tree=new Node(null, controller.board, 0, PLAYER);
    	buildTree(tree, DEPTH);
    	return max(tree).move;
    }
    
    /**
     * The max-part of the minimax algorithm
     * @param parent
     * @return best node
     */
    private Node max(Node parent) {
    	if (!parent.isLeaf()) {
    		int maxVal = -9999;
    		Node maxNode = null;
    		for(Node child : parent.children) {
    			Node minChild = min(child);
    			if(maxNode==null||minChild.value>maxVal) {
    				maxNode=child;
    				maxVal=minChild.value;
    			}
    		}
    		maxNode.value=maxVal;
    		return maxNode;
    	} else {
    		parent.value=positionValue(parent.boardStateAtNode);
    		return parent;
    	}
    }
    
    /**
     * The mini-part of the minimax algorithm
     * @param parent
     * @return worst node
     */
    private Node min(Node parent) {
    	if (!parent.isLeaf()) {
    		int minVal = 9999;
    		Node minNode = null;
    		for(Node child : parent.children) {
    			Node maxChild = max(child);
    			if(minNode==null||maxChild.value<minVal) {
    				minNode=child;
    				minVal=maxChild.value;
    			}
    		}
    		return minNode;
    	} else {
    		parent.value=positionValue(parent.boardStateAtNode);
    		return parent;
    	}
    }
    
    /**
     * Creates a tree and keeps recursively adding levels to it until the enddepth is reached.
     * @param p
     * @param depth
     */
    private void buildTree(Node p, int depth) {
    	ArrayList<Integer> possibleMoves=possibleMoves(p.side, p.boardStateAtNode);
    	for(int move : possibleMoves) {
    		int[][] childBoard = cloneBoard(p.boardStateAtNode);
    		OthelloController othelloController = (OthelloController) controller.iGame;
    		othelloController.setMove(move, side, childBoard);
    		Node n = new Node(p, childBoard, move, getOppositeSide(p.side));
    		p.addChild(n);
    	}
    	depth--;
    	if(depth>0) {
    		for(Node child : p.children) {
    			buildTree(child, depth);
    		}
    	}
    }
    
    /**
     * Method to create a full clone of the given board, as opposed to a regular shallow one.
     * @param board
     * @return cloned board
     */
    private int[][] cloneBoard(int[][] board){
    	int [][] cloneBoard = new int[board.length][];
		for(int i = 0; i < board.length; i++)
		  cloneBoard[i] = board[i].clone();
		return cloneBoard;
    }
    
    /**
     * Returns the side opposite of the given side
     * @param side
     * @return opposite side
     */
    private int getOppositeSide(int side){
    	//simple math, a+b-b=a AND a+b-a=b
    	return PLAYER+OPPONENT-side;
    }
    
}


	
