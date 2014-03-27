package dd143x;

import java.util.LinkedList;

public class EarlyGameAI {

	public static void play(Hand hand, ScoreCard card) {
		LinkedList<Integer> freeScores = card.getEmptyIndexes();
		int[] tempScore = new int[15];

		// start with check if we have a straight.
		int smallStraightScore = AI.smallStraightScore(hand.getValueArray());
		int bigStraightScore = AI.largeStraightScore(hand.getValueArray());
		int weHaveYaatzy = AI.yatzyScore(hand.getValueArray());
		
		
		if ((smallStraightScore != 0)
				&& (freeScores.contains(ScoreCard.smallStraight))) {
			card.scoreValues[ScoreCard.smallStraight] = smallStraightScore;
			return;
		}
		if ((bigStraightScore != 0)
				&& (freeScores.contains(ScoreCard.largeStraight))) {
			card.scoreValues[ScoreCard.largeStraight] = bigStraightScore;
			return;
		}

		if ((weHaveYaatzy != 0)
				&& (freeScores.contains(ScoreCard.yatzy))) {
			card.scoreValues[ScoreCard.yatzy] = weHaveYaatzy;
			return;
		}
		
		int[] countedDices = new int[AI.diceMaxValue];

		AI.countValues(hand.getValueArray(), countedDices);

		int valueToKeep = -1;

		

		//find 3 or 4 of a kind and set so aim for that if not already filled
		for (int diceValueTemp = 6; diceValueTemp >= 3; diceValueTemp--) {
			for (int nrDiceOfAKind : countedDices) {
				if ((nrDiceOfAKind >= 3)&&(card.scoreValues[diceValueTemp-1] != -1 )) {
					valueToKeep = diceValueTemp;
					break;
				}
			}
		}
		
		//catch pair or broken straight and decide what to go for
		//starights already caught
		if (valueToKeep != -1){
			for (int isThisARealNumber = 6; isThisARealNumber >= 1; isThisARealNumber --){
				if((countedDices[isThisARealNumber-1] == 2)&&(card.scoreValues[isThisARealNumber-1] != -1)){
					valueToKeep = isThisARealNumber;
					break;
				}
			}
			for (int anotherFakkingInt = 6; anotherFakkingInt >=1; anotherFakkingInt --){
				if (card.scoreValues[anotherFakkingInt-1] != -1){
					valueToKeep = anotherFakkingInt;
					break;
				}
			}
		}

		// we start with 3 or 4 of a kind and we havnt filled that value
		//throws 2 more times to collect those and fills in the score card
		//if we happen upon a straight or yatze that is filled in.
		if (valueToKeep != -1) {
			AIDiceRethrow.allOfAKind(hand, valueToKeep);
			AI.evalScores(hand.getValueArray(), tempScore);

			if ((tempScore[ScoreCard.yatzy] != 0)
					&& (card.scoreValues[ScoreCard.yatzy] != -1)) {
				card.scoreValues[ScoreCard.yatzy] = 50;
				return;
			}

			if ((tempScore[ScoreCard.smallStraight] != 0)
					&& (card.scoreValues[ScoreCard.smallStraight] != -1)) {
				card.scoreValues[ScoreCard.smallStraight] = 15;
				return;
			}

			if ((tempScore[ScoreCard.largeStraight] != 0)
					&& (card.scoreValues[ScoreCard.largeStraight] != -1)) {
				card.scoreValues[ScoreCard.largeStraight] = 20;
				return;
			}

			//this is done 2 times, no more no less, so no own method
			AIDiceRethrow.allOfAKind(hand, valueToKeep);
			AI.evalScores(hand.getValueArray(), tempScore);

			if ((tempScore[ScoreCard.yatzy] != 0)
					&& (card.scoreValues[ScoreCard.yatzy] != -1)) {
				card.scoreValues[ScoreCard.yatzy] = 50;
				return;
			}

			if ((tempScore[ScoreCard.smallStraight] != 0)
					&& (card.scoreValues[ScoreCard.smallStraight] != -1)) {
				card.scoreValues[ScoreCard.smallStraight] = 15;
				return;
			}

			if ((tempScore[ScoreCard.largeStraight] != 0)
					&& (card.scoreValues[ScoreCard.largeStraight] != -1)) {
				card.scoreValues[ScoreCard.largeStraight] = 20;
				return;
			}

			//fill in the one that should now have 3, 4 or 5 of a kind
			card.scoreValues[valueToKeep-1] = tempScore[valueToKeep-1];
		}

	}

	
}
