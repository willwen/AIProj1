package referee;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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
	boolean isMyTurn;
	int myPlayerNum;
	
	//The current search depth
	int depth = 1;
	
	//Arrays of Nodes for each depth of the search
	Node[] depth1;
	Node[] depth3;
	Node[] depth5;
	
	//Top level of the search tree
	Node root;
	
	RandomPlayer(){
//		timerThread = new Thread(new ThreadTimer(new Double(timeLimit*.9).intValue()*1000, new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					// TODO Auto-generated method stub
//					System.out.println(bestAnswer);
//				}
//			}));
//		timerThread.start();
	}
	
	public void maxMove(Node n){
		int currentChildValue;
		Point currentChildPoint;
		int bestSoFar = 0;
		Point bestPointSoFar = new Point(0,0);
		
		for (int i = 0; i <width ; i++){
			currentChildValue = n.children[i].value;
			currentChildPoint = n.children[i].nodePoint;
			if(bestSoFar < currentChildValue){
				bestSoFar = currentChildValue;
				//System.out.println("Heur Value: " + bestSoFar);
				bestPointSoFar = currentChildPoint;
				//System.out.println("Point: " + bestPointSoFar.height + " " + bestPointSoFar.width);
			}
		}
		n.value = bestSoFar;
		n.nodePoint = bestPointSoFar;
	}
	
	public void minMove(Node n){
		int currentChildValue;
		Point currentChildPoint;
		int bestSoFar = 100;
		Point bestPointSoFar = new Point(0,0);
		
		for (int i = 0; i <width ; i++){
			currentChildValue = n.children[i].value;
			currentChildPoint = n.children[i].nodePoint;
			if(bestSoFar > currentChildValue){
				bestSoFar = currentChildValue;
				bestPointSoFar = currentChildPoint;
			}
		}
		n.value = bestSoFar;
		n.nodePoint = bestPointSoFar;
	}
	
	public void minimax (int depth){
		switch(depth){
			case 1:
				maxMove(root);
				//Move will be root's 'nodePoint'
			case 3:
				for (int i = 0; i <width ; i++){
					
				}
		}
		
	}
	
	
	public void processInput() throws IOException{	
	
    	String s=input.readLine();	
		List<String> ls=Arrays.asList(s.split(" "));
		if(ls.size()==2){
			//every move except first :D
			
//			   sendGameInfo(column+" "+operation);
			updateBoardWithOpponentMove(Integer.parseInt(ls.get(0)),Integer.parseInt(ls.get(1)) );
			
			int currentNode = 0;
			switch(depth){
				case 1:
					for (Point p : getPossibleMoves()){
						//pointEvaluation(p.height, p.width);
						depth1[currentNode].value = pointEvaluation(p.height, p.width);
//						System.out.println(depth1[currentNode].value);
						currentNode++;
					}
					root.children = depth1;
					
				/*case 3:
					depth3[currentNode].value = pointEvaluation(p.height, p.width);
					break;
				case 5:
					depth5[currentNode].value = pointEvaluation(p.height, p.width);
					break;*/
			}
			
			currentNode = 0;
			
			
			
			
			
			moveNum++;
			//debug

			maxMove(root);
			
			String move = root.nodePoint.width + " 1";
			updateSelfMove(root.nodePoint.height, root.nodePoint.width) ;
			System.out.println(move);
			
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
			this.depth1 = new Node[width];
			for(int i=0; i<width; i++){
				this.depth1[i] = new Node(new Point(0,0), 0, null);
			}
			
			
//			Node[] depth3 = new Node[width];
//			Node[] depth5 = new Node[width];
//				
			root = new Node(new Point(0,0), 0, depth1);
			
			
			if( myPlayerNum == Integer.parseInt(ls.get(3))){
				isMyTurn = true;
			}
			else 
				isMyTurn = false;
			
			
			if(isMyTurn)
				System.out.println("3 1");
			
			else;

//			System.out.println(bestAnswer);  //first move
		}
		else if(ls.size()==4){		//player1: aa player2: bb
			//TODO combine this information with game information to decide who is the first player
			
			//    String s = "player1: " + this.player1_name + " player2: " + this.player2_name;
			if(ls.get(1).equals(this.playerName))
				myPlayerNum = 1;
			else 
				myPlayerNum = 2;

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
		
		
		
		
		
		
		
		
		
		
//		System.out.println("At " + height + " "+ width + " the value is " + points);
		
		//reset placement back to normal
		gameBoard[height][width] = 0;
		return points;
	}

	public int surroundingPoints(int height, int width) {
		int surrounding= 0;
		if (height == 0){
			if (width == 0){
				if (gameBoard[height+1][width+1] == 1)
					surrounding ++;
				if (gameBoard[height][width+1] == 1)
					surrounding ++;
				if (gameBoard[height+1][width] == 1)
					surrounding ++;
				
				return surrounding;
			}
			
			else if (width == this.width-1){
				if (gameBoard[height+1][width-1] == 1)
					surrounding ++;
				if (gameBoard[height][width-1] == 1)
					surrounding ++;
				if (gameBoard[height+1][width] == 1)
					surrounding ++;
				return surrounding;
			}
			
			else{
				if (gameBoard[height+1][width] == 1)
					surrounding ++;
				if (gameBoard[height][width+1] == 1)
					surrounding ++;
				if (gameBoard[height][width-1] == 1)
					surrounding ++;
				if (gameBoard[height+1][width+1] == 1)
					surrounding ++;
				if (gameBoard[height+1][width-1] == 1)
					surrounding ++;
				return surrounding;
			}
		}
		else{ //height isnt the lowest level
			if (width == 0){
				if (gameBoard[height+1][width+1] == 1)
					surrounding ++;
				if (gameBoard[height][width+1] == 1)
					surrounding ++;
				if (gameBoard[height+1][width] == 1)
					surrounding ++;
				if (gameBoard[height-1][width] == 1)
					surrounding ++;
				if (gameBoard[height-1][width+1] == 1)
					surrounding ++;
				return surrounding;
			}
			
			else if (width == this.width-1){
				if (gameBoard[height+1][width-1] == 1)
					surrounding ++;
				if (gameBoard[height][width-1] == 1)
					surrounding ++;
				if (gameBoard[height+1][width] == 1)
					surrounding ++;
				if (gameBoard[height-1][width] == 1)
					surrounding ++;
				if (gameBoard[height-1][width-1] == 1)
					surrounding ++;
				return surrounding;
			}
			
			else{
				if (gameBoard[height+1][width] == 1)
					surrounding ++;
				if (gameBoard[height][width+1] == 1)
					surrounding ++;
				if (gameBoard[height][width-1] == 1)
					surrounding ++;
				if (gameBoard[height+1][width+1] == 1)
					surrounding ++;
				if (gameBoard[height+1][width-1] == 1)
					surrounding ++;
				if (gameBoard[height-1][width-1] == 1)
					surrounding ++;
				if (gameBoard[height-1][width+1] == 1)
					surrounding ++;
				if (gameBoard[height-1][width] == 1)
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
					switch(depth){
						case 1:
							depth1[k].nodePoint = new Point(j, i);
//							System.out.println("in get possible moves" + depth1[k].nodePoint.height +" with width: " +depth1[k].nodePoint.height );
							break;
						/*case 3:
							depth3[k].nodePoint = new Point(j, i);
							break;
						case 5:
							depth5[k].nodePoint = new Point(j, i);
							break;*/
					}
					k++;
					break;
				}
			}
		}
		return possibleMoves;
	}
	
	private void updateBoardWithOpponentMove(int w, int popOut){
		if(popOut == 0){
			
			return;
		}
		int h = 0;
		for (int i = 0 ; i < height; i ++){
			if (gameBoard[i][w]==0){
				h = i;
				break;
			}
		}
		gameBoard[h][w] = 2;
	}
	private void updateSelfMove(int h, int w){
		gameBoard [h][w] = 1;
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