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
		int[] countDiceTest5 = new int[6];
		AI.countValues(testHandDices5, countDiceTest5);
		for (int i = 0; i<6; i++){
			assertEquals(countDiceTest5[i], countDiceTest5[i]);
		}
		
		int[] testHandDices6 = {2, 2 ,2, 2,2};
		hand.setDices(testHandDices6);
		EarlyGameAI.play(hand, card);
		assertEquals(50 , card.scoreValues[ScoreCard.yatzy]);
		assertEquals(10, card.scoreValues[ScoreCard.twos]);
		
		
		
		hand.setDices(testHandDices5);
		int returnValue5 = EarlyGameAI.valueToKeep(card, testCountedDices5);
		assertEquals(4, returnValue5);
		EarlyGameAI.play(hand, card);
		assertNotEquals(-1, card.scoreValues[ScoreCard.fours]);
		
		hand.setDices(testHandDices5);
		int returnValue6 = EarlyGameAI.valueToKeep(card, testCountedDices5);
		assertEquals(1, returnValue6);
		EarlyGameAI.play(hand, card);
		assertNotEquals(-1, card.scoreValues[ScoreCard.ones]);
		
		
		
		card = new ScoreCard();
		card.scoreValues[1] = 23;
		card.scoreValues[3] = 343;
		
		int[] testHandDices7 = {2,2,2,4,1};
		hand.setDices(testHandDices7);
		int[] countedDicesAgain = new int[6];
		AI.countValues(hand.getValueArray(), countedDicesAgain);
		int returnValue7 = EarlyGameAI.valueToKeep(card, countedDicesAgain); 
		assertEquals(1, returnValue7);
		card.scoreValues[0] = 432;
		returnValue7 = EarlyGameAI.valueToKeep(card, countedDicesAgain); 
		assertEquals(3, returnValue7);
		
		card = new ScoreCard();
		
		card.scoreValues[ScoreCard.fives] = 20;
		card.scoreValues[ScoreCard.yatzy] = 50;
		Printer.printArray(card.scoreValues);
		int[] testHand8 = {5,5,5,5,4};
		hand.setDices(testHand8);
		EarlyGameAI.play(hand, card);
		Printer.printArray(card.scoreValues);
		assertEquals(20, card.scoreValues[ScoreCard.fourOfAKind]);
		
	}
}
