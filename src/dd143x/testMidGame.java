package dd143x;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import sun.awt.image.ImageWatched.Link;

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
		
		
//		System.out.print("[");
//		for (int j : card.scoreValues){
//			System.out.print(j + ", ");
//		}
//	System.out.println("]");		
		
		hand.setDices(testHand1);
		MidGameAI.underPar(card, hand);
		int[] evalued = new int[15];
		AI.evalScores(hand.getValueArray(), evalued);
//		Printer.printArray(evalued);
//		Printer.printArray(hand.getValueArray());
//		Printer.printArray(scores);
		
		
		
		
		int[] testHand2 = {2,3,4,5,6};
		card = new ScoreCard();
		hand.setDices(testHand2);
		LinkedList<Integer> freeScores = card.getEmptyIndexes();
		MidGameAI.stegeKolla(card, hand, freeScores);
		Boolean caught = AI.catchHand(hand, card);
		assert(caught);
//		Printer.printArray(card.scoreValues);
		assertEquals(20, card.scoreValues[ScoreCard.largeStraight]);
		card.scoreValues[ScoreCard.largeStraight] = -1;
		
		card = new ScoreCard();
		int[] testhand3 = {2,3,4,4,5};
		hand.setDices(testhand3);
		MidGameAI.stegeKolla(card, hand, freeScores);
//		Printer.printArray(card.scoreValues);
		
		freeScores = card.getEmptyIndexes();
		int[] testhand4 = {2,3,6,4,1};
		hand.setDices(testhand4);
		MidGameAI.stegeKolla(card, hand, freeScores);
//		Printer.printArray(card.scoreValues);
		
		int[] newScores1 = {2,2,2,2,2,2,2,2,-1,-1,2,2,2,-1,-1};
		card.setScores(newScores1);
		hand.setDices(testhand4);
		freeScores = card.getEmptyIndexes();
		MidGameAI.stegeKolla(card, hand, freeScores);
//		System.out.println();
//		Printer.printArray(card.scoreValues);
		
		
		int[] newScores2 = {2,2,-1,2,2,-1,2,2,-1,-1,2,2,2,-1,-1};
		card.setScores(newScores2);
		int[] testHand5 = {6,6,6,2,3};
		hand.setDices(testHand5);
		int betOn1 = MidGameAI.uppCheck(card, hand);
		assertEquals(6, betOn1);
		
		
	}

}
