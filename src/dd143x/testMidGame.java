package dd143x;

import static org.junit.Assert.*;

import org.junit.Test;

public class testMidGame {

	@Test
	public void test() {
		
		ScoreCard card = new ScoreCard();
		Hand hand = new Hand();
		int[] testHand1 = {2,5,2,4,4};
		int[] scores = card.scoreValues;
		scores[0] = 1;
		scores[5] = 1;
		scores[4] = 1;
		scores[2] = 1;
		
		assertEquals(8, AI.numberScore(testHand1, 4));
		
		
		System.out.print("[");
		for (int j : card.scoreValues){
			System.out.print(j + ", ");
		}
	System.out.println("]");		
		
		hand.setDices(testHand1);
		MidGameAI.underPar(card, hand);
		int[] evalued = new int[15];
		AI.evalScores(hand.getValueArray(), evalued);
		Printer.printArray(evalued);
		Printer.printArray(hand.getValueArray());
		Printer.printArray(scores);
		
		
	}

}
