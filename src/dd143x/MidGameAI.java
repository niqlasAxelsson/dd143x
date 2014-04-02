package dd143x;

import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.text.StyledEditorKit.BoldAction;

import com.sun.org.apache.regexp.internal.recompile;
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
		if (checked) {
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

			if (stegCheck(hand)) {
				stegeKolla(card, hand, freeScores);
				return;
			}
			
			
			

		}
	}

	public static void stegeKolla(ScoreCard card, Hand hand,
			LinkedList<Integer> freeScores) {
		int[] countedDices = new int[6];
		boolean[] stegarKollade = stegCheckArray(hand);
		AI.countValues(hand.getValueArray(), countedDices);

		boolean hadeRedan = AI.catchHand(hand, card);
		if(hadeRedan){
			return;
		}
		
		Printer.printArraybOOL(stegarKollade);
		
		// vvi kan fa liten stege och den ar ledig
		if (freeScores.contains(ScoreCard.smallStraight)) {
			System.out.println("small straight free");
			if (stegarKollade[0] || stegarKollade[1]) {
				AIDiceRethrow.smallStraight(hand);
				boolean caught = AI.catchHand(hand, card);
				if (caught) {
					return;
				}
				AIDiceRethrow.smallStraight(hand);
				caught = AI.catchHand(hand, card);
				if (caught) {
					return;
				}

				// ingen liten stege
			}

		}

		if (freeScores.contains(ScoreCard.largeStraight) && hand.getRoll() == 1) {
			if (stegarKollade[1] || stegarKollade[2]) {
				AIDiceRethrow.largeStraight(hand);
				boolean caught = AI.catchHand(hand, card);
				if (caught) {
					return;
				}
				AIDiceRethrow.largeStraight(hand);
				caught = AI.catchHand(hand, card);
				if (caught) {
					return;
				}

				// ingen stor stege
			}
		}

		int[] evalScore = new int[15];
		AI.evalScores(hand.getValueArray(), evalScore);

		if (freeScores.contains(ScoreCard.twoPair)
				&& evalScore[ScoreCard.twoPair] != 0) {
			card.scoreValues[ScoreCard.twoPair] = evalScore[ScoreCard.twoPair];
			return;
		}
		if (freeScores.contains(ScoreCard.pair)
				&& evalScore[ScoreCard.pair] != 0) {
			card.scoreValues[ScoreCard.pair] = evalScore[ScoreCard.pair];
			return;
		}

		if (freeScores.contains(ScoreCard.threeOfAKind)
				&& evalScore[ScoreCard.threeOfAKind] != 0) {
			card.scoreValues[ScoreCard.threeOfAKind] = evalScore[ScoreCard.threeOfAKind];
			return;
		}

		boolean satteUppe = fillUpper(card, hand);
		if (!satteUppe) {
			Nolla.nollaUppe(card);
		}

	}

	public static boolean stegCheck(Hand hand) {
		String s = new String();
		for (int k : hand.getValueArray()) {
			if (!s.contains(""+k)){
				s += k;
				}
		}
		
		boolean first = s.contains("1234");
		boolean second = s.contains("2345");
		boolean third = s.contains("3456");

		if (first || second || third) {
			return true;
		}
		return false;
	}

	public static boolean[] stegCheckArray(Hand hand) {
		
		String s = new String();
		for (int k : hand.getValueArray()) {
			if (!s.contains(""+k)){
			s += k;
			}
		}
		
		boolean[] returning = new boolean[3];
		returning[0] = s.contains("1234");
		returning[1] = s.contains("2345");
		returning[2] = s.contains("3456");

		return returning;
	}

	public static boolean fillUpper(ScoreCard card, Hand hand) {
		for (int i = 0; i < 6; i++) {
			if (card.scoreValues[i] == -1) {
				card.scoreValues[i] = AI.numberScore(hand.getValueArray(),
						i + 1);
				return true;
			}
		}
		return false;
	}

}
