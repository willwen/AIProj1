package referee;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Random;

import org.junit.Test;

public class testRandomPlayer {

	
	
	@Test
	public void testRandomPlayer() {
		fail("Not yet implemented");
	}

	@Test
	public void testProcessInput() {
		fail("Not yet implemented");
	}

	@Test
	public void testPointEvaluation() throws IOException {
		RandomPlayer rp= new RandomPlayer();
		rp.processInput();
		rp.processInput();
		assertEquals(rp.pointEvaluation(0,4), 0);
	}

	@Test
	public void testGetPossibleMoves() throws IOException{
		RandomPlayer rp = new RandomPlayer();
		rp.processInput();
		rp.processInput();
		rp.getPossibleMoves();
	}
	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

}
