package dd143x;

import java.util.LinkedList;

public class AI {

	public static void ai(ScoreCard scoreCard, Hand hand){
		LinkedList<Integer> emptyIndex = scoreCard.getEmptyIndexes();
		int firstIndex = emptyIndex.poll();
		Dice[] dices = hand.getHand();
		int score = 0;
		for (Dice dice : dices){
			score += dice.getValue();
		}
		scoreCard.scoreValues[firstIndex] =score ;
		
		
	}
	
}
