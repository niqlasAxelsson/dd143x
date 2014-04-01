package dd143x;

import java.util.Arrays;
import java.util.LinkedList;

import com.sun.swing.internal.plaf.basic.resources.basic;

public class MidGameAI {

	
	public static void play(Hand hand, ScoreCard card) {

		LinkedList<Integer> freeScores = card.getEmptyIndexes();
		int[] tempScore = new int[15];
		boolean[] betOnThis = new boolean[15];

		for (int i = 0; i < betOnThis.length; i++) {
			betOnThis[i] = false;
		}

		// start with check if we have small/large straight or yatzy
		boolean checked = AI.catchHand(hand, card);
		if (checked){
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

	public static void underPar(ScoreCard card, Hand hand) {
		// if it is possible to get on par
		if (card.possibleToGetPar()) {
			// fill go for the top according to early game
			EarlyGameAI.play(hand, card);
			return;
		} else {
			LinkedList<Integer> freeScores = card.getEmptyIndexes();
			// the player need every point possible
			

			int[] evalScores = new int[card.scoreValues.length];
			AI.evalScores(hand.getValueArray(), evalScores);

			// fångar kåk direkt om vi ligger under par, kan inte få par
			if (evalScores[ScoreCard.fullHouse] != 0
					&& freeScores.contains(ScoreCard.fullHouse)) {
				card.scoreValues[ScoreCard.fullHouse] = evalScores[ScoreCard.fullHouse];
				return;
			}

			if (evalScores[ScoreCard.twoPair] != 0) {
				if (freeScores.contains(ScoreCard.fullHouse)) {
					// TODO vi har tva par, kak ledigt

					AIDiceRethrow.twoPairToHouse(hand);
					AI.evalScores(hand.getValueArray(), evalScores);

					// fångar kåk direkt om vi ligger under par, kan inte få par
					if (evalScores[ScoreCard.fullHouse] != 0
							&& freeScores.contains(ScoreCard.fullHouse)) {
						card.scoreValues[ScoreCard.fullHouse] = evalScores[ScoreCard.fullHouse];
						return;
					}

					AIDiceRethrow.twoPairToHouse(hand);

					AI.evalScores(hand.getValueArray(), evalScores);

					// fångar kåk direkt om vi ligger under par, kan inte få par
					if (evalScores[ScoreCard.fullHouse] != 0
							&& freeScores.contains(ScoreCard.fullHouse)) {
						card.scoreValues[ScoreCard.fullHouse] = evalScores[ScoreCard.fullHouse];
						return;
					}

					// vi har kak men kak ar upptagen.dvs fyller triss
					if (evalScores[ScoreCard.threeOfAKind] != 0
							&& freeScores.contains(ScoreCard.threeOfAKind)) {
						card.scoreValues[ScoreCard.threeOfAKind] = evalScores[ScoreCard.threeOfAKind];
						return;
					}

					// vi har tva par och den platsen ar ledig
					if (card.scoreValues[ScoreCard.twoPair] == -1) {
						card.scoreValues[ScoreCard.twoPair] = evalScores[ScoreCard.twoPair];
						return;
					}

					if (evalScores[ScoreCard.pair] >= 8
							&& freeScores.contains(ScoreCard.pair)) {
						card.scoreValues[ScoreCard.pair] = evalScores[ScoreCard.pair];
						return;
					}

					for (int d = 0; d < 6; d++) {
						if (evalScores[d] != 0 && freeScores.contains(d)) {
							card.scoreValues[d] = evalScores[d];
							return;
						}
					}

					if (freeScores.contains(ScoreCard.pair)) {
						card.scoreValues[ScoreCard.pair] = evalScores[ScoreCard.pair];
						return;
					}

					if (evalScores[ScoreCard.chance] >= 19
							&& freeScores.contains(ScoreCard.chance)) {
						card.scoreValues[ScoreCard.chance] = evalScores[ScoreCard.chance];
						return;
					}

					// nolla
					Nolla.nollaUppe(card);
					return;
					
				}
				
			}
			
			if (stegCheck(hand)){
				int[] countedDices = new int[6];
				
			}

		}
	}
	
	
	public static boolean stegCheck(Hand hand){
		Arrays.sort(hand.getValueArray());
		String s = new String();
		for (int k : hand.getValueArray()){
			s += k;
		}
		
		boolean first = s.contains("1234");
		boolean second = s.contains("2345");
		boolean third = s.contains("3456");
		
		if(first || second || third){
			return true;
		}
		
		
		
		
		return false;
	}
	
	
	
}
