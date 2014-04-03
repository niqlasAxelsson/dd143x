package dd143x;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestLateGame {

	@Test
	public void test() {
		Hand hand = new Hand();
		int[] testHand1 = {3,3,6,5,3};
		hand.setDices(testHand1);
		AIDiceRethrow.getTwoPair(hand);
		
		
	}

}
