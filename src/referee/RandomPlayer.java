package referee;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import javax.swing.Timer;

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
	boolean isTimeUp = false;
	String bestAnswer;
	Timer timerRunning;
	ActionListener timeUp = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println(bestAnswer);
			timerRunning.stop();
			isTimeUp = true;
			updateSelfMove(Character.getNumericValue(bestAnswer.charAt(0)) , Character.getNumericValue(bestAnswer.charAt(2)), 1);
			//might be  a bug where this spits out answer, but then our minimax func also spits out answer.
		}
	};
	boolean isMyTurn;
	int myPlayerNum;
	
	//The current search depth
	int depth = 1;
	
	//Arrays of Nodes for each depth of the search
	Node[] depth1;
	//Node[] depth3;
	//Node[] depth5;
	
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
		isTimeUp = false;
    	String s=input.readLine();	
		List<String> ls=Arrays.asList(s.split(" "));
		if(ls.size()==2){
			//every move except first :D
			
//This is sent to us:	 sendGameInfo(column+" "+operation);
			updateBoardWithOpponentMove(Integer.parseInt(ls.get(0)),Integer.parseInt(ls.get(1)) );
			//start the timer!
			timerRunning.restart();
			int currentNode = 0;
			switch(depth){
				case 1:
					for (Point p : getPossibleMoves()){
						//TODO Remember to update the best so far String.
						//===============================================
						depth1[currentNode].value = pointEvaluation(p.height, p.width);
						currentNode++;
					}
					currentNode = 0;
					root.children = depth1;
					
					maxMove(root);
					bestAnswer = root.nodePoint.width + " 1";
					updateSelfMove(root.nodePoint.height, root.nodePoint.width, 1);
					depth = 2;
					//break;
					
				case 2:
					int currentDepth1Node;
					for (currentDepth1Node = 0; currentDepth1Node < width; currentDepth1Node++){
						//Assign the children of each node at depth1
						for (int i = 0; i < width; i++){
							root.children[currentDepth1Node].children[i] = new Node(new Point(0,0), 0, null);
						}
						//TODO Make getPossibleMoves have hypothetical boardstate based on best answer
						for (Point p : getPossibleMoves()){
							root.children[currentDepth1Node].children[currentNode].value = pointEvaluation(p.height, p.width);
							currentNode++;
						}
						currentNode = 0;
						
						minMove(root.children[currentDepth1Node]);
						
					}
					currentDepth1Node = 0;
					
					maxMove(root);
					bestAnswer = root.nodePoint.width + " 1";
					updateSelfMove(root.nodePoint.height, root.nodePoint.width, 1);
					//depth = 3;
					break;
				/*case 3:
					int currentDepth2Node;
					for (currentDepth1Node = 0; currentDepth1Node < width; currentDepth1Node++){
						for (currentDepth2Node = 0; currentDepth2Node < width; currentDepth2Node++){
							//Assign the children of each node at depth1
							for (int i = 0; i < width; i++){
								root.children[currentDepth1Node].children[currentDepth2Node].children[i] = new Node(new Point(0,0), 0, null);
							}
							//TODO Make getPossibleMoves have hypothetical boardstate based on best answer
							for (Point p : getPossibleMoves()){
								root.children[currentDepth1Node].children[currentDepth2Node].children[currentNode].value = pointEvaluation(p.height, p.width);
								currentNode++;
							}
							currentNode = 0;
							
							maxMove(root.children[currentDepth1Node].children[currentDepth2Node]);
						}
						currentDepth2Node = 0;
						
						minMove(root.children[currentDepth1Node]);
					}
					currentDepth1Node = 0;
					
					maxMove(root);
					bestAnswer = root.nodePoint.width + " 1";
					updateSelfMove(root.nodePoint.height, root.nodePoint.width, 1);
					depth = 1;
					break;*/
			}
			
			depth = 1;
			
			
			
			
			
			moveNum++;
			//maxMove(root);
			

			if(isTimeUp){
				//String move = root.nodePoint.width + " 1";
				//assuming all our moves are not popout
				//updateSelfMove(root.nodePoint.height, root.nodePoint.width, 1);
				//System.out.println(move);
				System.out.println(bestAnswer);
			}

			timerRunning.stop();
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
			timerRunning = new Timer (timeLimit * 1000 ,timeUp);
			
			
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
			timerRunning.stop();
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
		//is it a popout
		if(popOut == 0){
			for (int i = 0; i <height-1 ; i ++){
				gameBoard[i][w]= gameBoard [i+1][w];
			}
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
	private void updateSelfMove(int h, int w, int popOut){
		if(popOut == 0){
			for (int i = 0; i <height-1 ; i ++){
				gameBoard[i][w]= gameBoard [i+1][w];
			}
			return;
		}
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