package dd143x;

import java.util.LinkedList;

public class Nolla {

	final static int[] nolla = { ScoreCard.smallStraight,
			ScoreCard.largeStraight, ScoreCard.fullHouse, ScoreCard.yatzy,
			ScoreCard.fourOfAKind, ScoreCard.twoPair, ScoreCard.threeOfAKind,
			ScoreCard.pair, 0, 1, 2, 3, 4, 5 };

	final static int[] nollaUppe = { 0, 1, 2, 3, 4, 5, ScoreCard.smallStraight,
			ScoreCard.largeStraight, ScoreCard.fullHouse, ScoreCard.yatzy,
			ScoreCard.fourOfAKind, ScoreCard.twoPair, ScoreCard.threeOfAKind,
			ScoreCard.pair };

	public static void nollaUppe(ScoreCard card) {
		LinkedList<Integer> freeSCores= card.getEmptyIndexes();
		for (int i : nollaUppe){
			if (freeSCores.contains(i)){
				card.scoreValues[i] = 0;
			}
		}
		
	}

	public static void nollaNere(ScoreCard card) {
		LinkedList<Integer> freeSCores= card.getEmptyIndexes();
		for (int i : nolla){
			if (freeSCores.contains(i)){
				card.scoreValues[i] = 0;
			}
		}
		
	}
	
	
}
