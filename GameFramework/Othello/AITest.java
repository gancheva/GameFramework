package Othello;

/*
import GameEngine.GameGUI;
import GameEngine.GameController;
import org.junit.*;
import static org.junit.Assert.*;
*/

/**
 * 
 * @author thema 2.3 groep 4
 * @version 17-Apr-12
 * 
 */
public class AITest {
	AI ai = new AI(null);
	int[][] cloneBoard;

	/*
		//@Test
		public void testPossibleMoves(){
			
		ai.board = new int[8][8];
		ai.board[0][0] = AI.PLAYER1;
		ai.board[1][1] = AI.OPPONENT1;
		ai.board[2][2] = AI.OPPONENT1;
		ai.board[3][3] = AI.OPPONENT1;
		//ai.board[4][4] = AI.OPPONENT1;
		ai.board[0][1] = AI.OPPONENT1;
		
		cloneBoard = new int[ai.board.length][];
		for(int i = 0; i < ai.board.length; i++)
		  cloneBoard[i] = ai.board[i].clone();
		
		cloneBoard[1][1] = AI.PLAYER1;
		
		//Field looks like:
		//*-----
		//oo----
		//--o---
		//---o--
		//------
		
		
		doTest();
		}
		
		
		
		private void doTest(){
			
			// 2 possible moves for the player
			assertTrue( ai.possibleMoves(AI.PLAYER1, ai.board).size() == 2 );
			
			//36 (board[4][4]) should give 4 extra stones (flip 3 add 1)
			assertTrue(ai.discsMoveValue(36)==4);
			//16 (board[0][2]) should add 2 extra stones (flip 1 add 1)
			assertTrue(ai.discsMoveValue(16)==2);
			//Player gains one disc  and opponent loses one, value should be 2
			assertTrue(ai.positionValue(cloneBoard) == 2);
			
			System.out.println(ai.choosethMove());
			
			//assertTrue(ai.board[1][1] == AI.PLAYER1 &&
					//ai.board[2][2] == AI.PLAYER1 &&
					//ai.board[3][3] == AI.PLAYER1 &&
					//ai.board[4][4] == AI.PLAYER1);
		}
		
		
		
		@Test
		public void depthTest(){
			System.out.println("start depthtest");
			ai.board = new int[8][8];
			ai.board[1][1] = AI.PLAYER1;
			ai.board[2][2] = AI.OPPONENT1;
			ai.board[3][3] = AI.OPPONENT1;
			ai.board[4][4] = AI.OPPONENT1;
			ai.board[6][6] = AI.OPPONENT1;
			ai.board[1][2] = AI.OPPONENT1;
			ai.board[1][3] = AI.OPPONENT1;
			
			//System.out.println("chosen move: "+ai.choosethMove());
			assertTrue(ai.choosethMove() == 33);
		}
		
		//@Test
		public void hereWeGo() {
			assertTrue(true);
		}
		
		@Test
		public void endGameValidation() {
			for(int i=0;i<ai.boardx;i++) {
				for(int j=0;j<ai.boardy;j++) {
					ai.board[i][j]=AI.PLAYER1;
				}
			}
		
			ai.board[1][0]=AI.EMPTY1;
			ai.board[2][0]=AI.OPPONENT1;
			ai.board[6][6]=AI.OPPONENT1;
			ai.board[7][7]=AI.OPPONENT1;
			
			System.out.println(ai.choosethMove());
			assertTrue(ai.choosethMove()==1);
		}
		*/

}
