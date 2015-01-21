package referee;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class RandomPlayer {

	String playerName="aa";
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	boolean first_move=false;
	
	public void processInput() throws IOException{	
	
    	String s=input.readLine();	
		List<String> ls=Arrays.asList(s.split(" "));
		if(ls.size()==2){
			System.out.println(ls.get(0)+" "+ls.get(1));
		}
		else if(ls.size()==1){
			System.out.println("game over!!!");
		}
		//		String s=this.board.height+" "+this.board.width+" "+this.N+" "+this.currentPlayer+" "+this.moveTimeLimit;
		//can just go readLine();

		else if(ls.size()==5){          //ls contains game info
			System.out.println("0 1");  //first move
		}
		else if(ls.size()==4){		//player1: aa player2: bb
			//TODO combine this information with game information to decide who is the first player
		}
		else
			System.out.println("not what I want");
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
