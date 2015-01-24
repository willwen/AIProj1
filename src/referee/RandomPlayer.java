package referee;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class RandomPlayer {

	String playerName="RyanAndWill";
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	boolean first_move=false;
	int gameBoard[][]; // 1 is our piece, 2 is the opponent, 0 is no one.
	int height;
	int width;
	int middleSlot;
	int connectLength;
	int timeLimit;
	int moveNum= 0;
	String bestAnswer;
	Thread timerThread;
	
	
	RandomPlayer(){
		timerThread = new Thread(new ThreadTimer(new Double(timeLimit*.9).intValue()*1000, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println(bestAnswer);
				}
			}));
		timerThread.start();
	}
	
	
	public void processInput() throws IOException{	
	
    	String s=input.readLine();	
		List<String> ls=Arrays.asList(s.split(" "));
		if(ls.size()==2){
//			   sendGameInfo(column+" "+operation);
			updateBoardWithOpponentMove(Integer.parseInt(ls.get(0)),Integer.parseInt(ls.get(1)) );
			for (Point p : getPossibleMoves()){
				pointEvaluation(p.height, p.width);
			}
			
			
			
			
			
			moveNum++;
			//debug
			System.out.println(ls);

			
			System.out.println(bestAnswer);

		}
		else if(ls.size()==1){
			System.out.println("game over!!!");
		}


		else if(ls.size()==5){          //ls contains game info
			
			//		String s=this.board.height+" "+this.board.width+" "+this.N+" "+this.currentPlayer+" "+this.moveTimeLimit;
			/**
			 * Initialize local board statistics.
			 */
			height = Integer.parseInt(ls.get(0));
			width = Integer.parseInt(ls.get(1));
			connectLength = Integer.parseInt(ls.get(2));
			gameBoard = new int[height][width];
			timeLimit = Integer.parseInt(ls.get(4));
			middleSlot = width /2;
			
			
			
			
			
			
			
			
			
			
			moveNum++;

			System.out.println(bestAnswer);  //first move
		}
		else if(ls.size()==4){		//player1: aa player2: bb
			//TODO combine this information with game information to decide who is the first player
		}
		else
			System.out.println("not what I want");
	}
	

	
	
	/**
	 * Heuristic Evaluation Function
	 * 
	 * range from 100 to -100
	 * 
	 * 100 is victory for us, -100 is victory for them
	 * 
	 * @param height
	 * @param width
	 * 
	 * @return the amount of points for such height and width
	 */
	
	public int pointEvaluation(int height, int width){
		int points = 0;
		//victory?
		gameBoard[height][width] = 1;
		
		switch (surroundingPoints(height,width)){
			case 0:
				break;
			case 1:
				points += 10;
				break;
			case 2:
				points += 20;
				break;
			case 3:
				points += 30;
				break;
			case 4:
				points += 40;
				break;
			case 5:
				points += 50;
				break;
			case 6:
				points += 60;
				break;
			case 7:
				points += 70;
				break;
			case 8:
				points += 80;
				break;

				
		}
			
		
		//if move is in the middle, gain 10 points
		if (width == middleSlot){
			points += 10;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		//reset placement back to normal
		gameBoard[height][width] = 0;
		return points;
	}

	public int surroundingPoints(int height, int width) {
		int surrounding= 0;
		if (height == 0){
			if (width == 0){
				if (gameBoard[height++][width++] == 1)
					surrounding ++;
				if (gameBoard[height][width++] == 1)
					surrounding ++;
				if (gameBoard[height++][width] == 1)
					surrounding ++;
				
				return surrounding;
			}
			
			else if (width == this.width-1){
				if (gameBoard[height++][width--] == 1)
					surrounding ++;
				if (gameBoard[height][width--] == 1)
					surrounding ++;
				if (gameBoard[height++][width] == 1)
					surrounding ++;
				return surrounding;
			}
			
			else{
				if (gameBoard[height++][width] == 1)
					surrounding ++;
				if (gameBoard[height][width++] == 1)
					surrounding ++;
				if (gameBoard[height][width--] == 1)
					surrounding ++;
				if (gameBoard[height++][width++] == 1)
					surrounding ++;
				if (gameBoard[height++][width--] == 1)
					surrounding ++;
				return surrounding;
			}
		}
		else{ //height isnt the lowest level
			if (width == 0){
				if (gameBoard[height++][width++] == 1)
					surrounding ++;
				if (gameBoard[height][width++] == 1)
					surrounding ++;
				if (gameBoard[height++][width] == 1)
					surrounding ++;
				if (gameBoard[height--][width] == 1)
					surrounding ++;
				if (gameBoard[height--][width++] == 1)
					surrounding ++;
				return surrounding;
			}
			
			else if (width == this.width-1){
				if (gameBoard[height++][width--] == 1)
					surrounding ++;
				if (gameBoard[height][width--] == 1)
					surrounding ++;
				if (gameBoard[height++][width] == 1)
					surrounding ++;
				if (gameBoard[height--][width] == 1)
					surrounding ++;
				if (gameBoard[height--][width--] == 1)
					surrounding ++;
				return surrounding;
			}
			
			else{
				if (gameBoard[height++][width] == 1)
					surrounding ++;
				if (gameBoard[height][width++] == 1)
					surrounding ++;
				if (gameBoard[height][width--] == 1)
					surrounding ++;
				if (gameBoard[height++][width++] == 1)
					surrounding ++;
				if (gameBoard[height++][width--] == 1)
					surrounding ++;
				if (gameBoard[height--][width--] == 1)
					surrounding ++;
				if (gameBoard[height--][width++] == 1)
					surrounding ++;
				if (gameBoard[height--][width] == 1)
					surrounding ++;
			}
		}
		
		return 0;
	}

	/**
	 * returns an array of all possible moves to make for a certain move
	 * @return
	 */
	public Point[] getPossibleMoves(){
		Point [] possibleMoves = new Point[width];
		int k = 0;
		for (int i = 0; i <width ; i++){
			for (int j = 0; j <height ; j++){
				if(gameBoard[j] [i] == 0){
					possibleMoves[k] = new Point(j, i);
					k++;
					break;
				}
			}
		}
		return possibleMoves;
	}
	
	private void updateBoardWithOpponentMove(int h, int w){
		gameBoard[h][w] = 2;
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		RandomPlayer rp=new RandomPlayer();
		System.out.println(rp.playerName);
		rp.processInput();
		rp.processInput();
		rp.processInput();
		rp.processInput();
		rp.processInput();
		rp.processInput();
		rp.processInput();
	}
}
