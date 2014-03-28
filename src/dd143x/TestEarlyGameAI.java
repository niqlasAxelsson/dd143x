package dd143x;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestEarlyGameAI {

	@Test
	public void test() {
		ScoreCard card = new ScoreCard();
		Hand hand = new Hand();
		int[] testcountedDices1 = { 0, 0, 2, 3, 0, 0 };
		int returnValue1 = EarlyGameAI.valueToKeep(card, testcountedDices1);
		assertEquals(4, returnValue1);

		int[] testcountedDices2 = { 0, 0, 4, 1, 0, 0 };
		int returnValue2 = EarlyGameAI.valueToKeep(card, testcountedDices2);
		assertEquals(3, returnValue2);

		int[] testcountedDices3 = { 0, 0, 1, 1, 1, 2 };
		int returnValue3 = EarlyGameAI.valueToKeep(card, testcountedDices3);
		assertEquals(6, returnValue3);

		int[] testcountedDices4 = { 2, 1, 2, 0, 0, 0 };
		int returnValue4 = EarlyGameAI.valueToKeep(card, testcountedDices4);
		assertEquals(3, returnValue4);

		
		int[] testHandDices1 = {5, 5 ,5, 5,5};
		hand.setDices(testHandDices1);
		EarlyGameAI.play(hand, card);
		assertEquals(50 , card.scoreValues[ScoreCard.yatzy]);
		
		int[] testHandDices2 = {1,2,5,4,3};
		hand.setDices(testHandDices2);
		EarlyGameAI.play(hand, card);
		int tempScore1 = AI.smallStraightScore(testHandDices2);
		assertEquals(15, tempScore1);
		assertEquals(15, card.scoreValues[ScoreCard.smallStraight]);
		
		
		int[] testHandDices3 = {2, 3,4,5,6};
		hand.setDices(testHandDices3);
		EarlyGameAI.play(hand, card);
		int tempScore2 = AI.largeStraightScore(testHandDices3);
		assertEquals(20, tempScore2);
		assertEquals(20, card.scoreValues[ScoreCard.largeStraight]);
		
		int[] testHandDices4 = {3, 3,3, 4,5};
		hand.setDices(testHandDices4);
		EarlyGameAI.play(hand, card);
		assertNotEquals(-1, card.scoreValues[ScoreCard.threes]);
		
		int[] testHandDices5 = {1,1,4,4,6};
		int[] testCountedDices5 = {2,0,0,2,0,1};
		hand.setDices(testHandDices5);
		int returnValue5 = EarlyGameAI.valueToKeep(card, testCountedDices5);
		assertEquals(4, returnValue5);
		EarlyGameAI.play(hand, card);
		assertNotEquals(-1, card.scoreValues[ScoreCard.fours]);
		
		
	}
}
