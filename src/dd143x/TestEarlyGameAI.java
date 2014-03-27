package dd143x;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestEarlyGameAI {

	@Test
	public void test() {
		ScoreCard card = new ScoreCard();
		Hand hand = new Hand();
		int[] testcountedDices1 = {0, 0, 2, 3, 0,  0};
		int returnValue1 = EarlyGameAI.valueToKeep(card, testcountedDices1);
		assertEquals(4, returnValue1);
		
		int[] testcountedDices2 = {0, 0, 4, 1, 0, 0};
		int returnValue2 = EarlyGameAI.valueToKeep(card, testcountedDices2);
		assertEquals(3, returnValue2);
		

		int[] testcountedDices3 = {0, 0, 1, 1, 1, 2};
		int returnValue3 = EarlyGameAI.valueToKeep(card, testcountedDices3);
		assertEquals(6, returnValue3);

		int[] testcountedDices4 = {2, 1, 2, 0, 0, 0};
		int returnValue4 = EarlyGameAI.valueToKeep(card, testcountedDices4);
		assertEquals(3, returnValue4);
		
}
	
}
