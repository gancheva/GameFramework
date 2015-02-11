package Othello;

import java.util.ArrayList;

/**
 * 
 * @author thema 2.3 groep 4
 * @version 17-Apr-12
 * 
 */
public class Node {
	Node parent;
	int move;
	int side;
	int value;
	ArrayList<Node> children = new ArrayList<Node>();
	int[][] boardStateAtNode;
	
	/**
	 * Constructor of the class Node.
	 * @param parent
	 * @param boardStateAtNode
	 * @param move
	 * @param side
	 */
	public Node(Node parent, int[][] boardStateAtNode, int move, int side) {
		this.parent = parent;
		this.boardStateAtNode = boardStateAtNode;
		this.move = move;
		this.side = side;
		if(parent!=null) parent.addChild(this);
	}
	
	/**
	 * Adding a child to the node.
	 * @param child
	 */
	public void addChild(Node child) {
		children.add(child);
	}
	
	/**
	 * Checks whether the node is a leaf.
	 * @return whether the node has children 
	 */
	public boolean isLeaf() {
		return (children.isEmpty());
	}
	
}
