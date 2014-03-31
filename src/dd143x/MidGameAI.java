package dd143x;

import java.util.LinkedList;

public class MidGameAI {

	public static void play(Hand hand, ScoreCard card) {

		LinkedList<Integer> freeScores = card.getEmptyIndexes();
		int[] tempScore = new int[15];
		boolean[] betOnThis = new boolean[15];

		for (int i = 0; i < betOnThis.length; i++) {
			betOnThis[i] = false;
		}

		// start with check if we have small/large straight or yatzy
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

		if ((weHaveYaatzy != 0) && (freeScores.contains(ScoreCard.yatzy))) {
			card.scoreValues[ScoreCard.yatzy] = weHaveYaatzy;
			return;
		}

		// -1 if the player is under par
		// 0 if the player is on par
		// else the player is over par
		if (card.onPar() == -1) {

		} else if (card.onPar() == 0) {
		} else {
		}
	}

	private static void underPar(ScoreCard card, Hand hand, Boolean[] betOnThis) {
		// if it is possible to get on par
		if (card.possibleToGetPar()) {
			// fill go for the top according to early game
			EarlyGameAI.play(hand, card);
			return;
		} else {
			LinkedList<Integer> freeScores = card.getEmptyIndexes();
			// the player need every point possible
			for (int i = 0; i < betOnThis.length; i++) {
				
				if (freeScores.contains(i)) {
					betOnThis[i] = true;
				}

				int[] evalScores = new int[card.scoreValues.length];
				AI.evalScores(hand.getValueArray(), evalScores);
				
				//fångar kåk direkt om vi ligger under par, kan inte få par
				if (evalScores[ScoreCard.fullHouse] != 0 && freeScores.contains(ScoreCard.fullHouse)){
					card.scoreValues[ScoreCard.fullHouse] = evalScores[ScoreCard.fullHouse];
				}
				
				if (evalScores[ScoreCard.twoPair] != 0){
					if(freeScores.contains(ScoreCard.fullHouse)){
						
					}
					
				}
				
			}
		}
	}
}