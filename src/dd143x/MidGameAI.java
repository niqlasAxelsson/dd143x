package dd143x;

import java.util.LinkedList;

public class MidGameAI {

	public static void play(Hand hand, ScoreCard card) {

		
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
			underPar(card, hand);
		} else if (card.onPar() == 0) {
			onPar(card, hand);
		} else {
			overPar(card, hand);
		}
	}

	public static void onPar(ScoreCard card, Hand hand) {

		LinkedList<Integer> freeScores = card.getEmptyIndexes();
		
		if (AI.catchHand(hand, card)) {
			return;
		}

		if (stegCheck(hand)) {
			stegeKolla(card, hand, freeScores);
		
			
		}
		
		int betOn = uppCheck(card, hand);
		
		AIDiceRethrow.allOfAKind(hand, betOn);
		if (AI.catchHand(hand, card)) {
			return;
		}
		if (AI.fullHouse(card, hand)) {
			return;
		}

		AIDiceRethrow.allOfAKind(hand, betOn);
		
		allOfAKindDefensive(card, hand, betOn);

	}

	public static int uppCheck(ScoreCard card, Hand hand) {
		LinkedList<Integer> freeScores = card.getEmptyIndexes();
		int[] countedDices = new int[6];
		AI.countValues(hand.getValueArray(), countedDices);

		LinkedList<Integer> checkedAllready = new LinkedList<Integer>();

		int betOn = 1;
		int num = 0;
		for (int v = 0; v < 6; v++) {
			num = 0;
			for (int e = 5; e >= 0; e--) {
				if (countedDices[e] > num && !checkedAllready.contains(e +1)) {
					num = countedDices[e];
					betOn = e +1;
					System.out.println(betOn);
				}
			}

			if (freeScores.contains(betOn - 1)) {
				
				return betOn;
			}

			checkedAllready.add(betOn);
		}

		for (int e = 5; e >= 0; e--) {
			if (countedDices[e] > num) {
				num = countedDices[e];
				betOn = e;
			}
		}
		return betOn;
	}

	public static void overPar(ScoreCard card, Hand hand) {
		agressive(card, hand);
	}

	public static void underPar(ScoreCard card, Hand hand) {
		// if it is possible to get on par
		if (card.possibleToGetPar()) {
			// fill go for the top according to early game
			EarlyGameAI.play(hand, card);
			return;
		} else {
			agressive(card, hand);

		}
	}

	public static void agressive(ScoreCard card, Hand hand) {
		LinkedList<Integer> freeScores = card.getEmptyIndexes();
		// the player need every point possible

		int[] evalScores = new int[card.scoreValues.length];
		AI.evalScores(hand.getValueArray(), evalScores);

		// fångar kåk direkt om vi ligger under par, kan inte få par
		if (AI.fullHouse(card, hand)) {
			return;
		}

		if (evalScores[ScoreCard.twoPair] != 0
				&& (freeScores.contains(ScoreCard.twoPair) || freeScores
						.contains(ScoreCard.fullHouse))) {
			twoPairMid(card, hand, freeScores, evalScores);
			return;

		}

		if (stegCheck(hand)) {
			stegeKolla(card, hand, freeScores);
			return;
		}

		int keep = betOnInt(card, hand);

		AIDiceRethrow.allOfAKind(hand, keep);
		if (AI.catchHand(hand, card)) {
			return;
		}
		if (AI.fullHouse(card, hand)) {
			return;
		}

		AIDiceRethrow.allOfAKind(hand, keep);

		allOfAKindAgressive(card, hand, keep);
	}

	public static void twoPairMid(ScoreCard card, Hand hand,
			LinkedList<Integer> freeScores, int[] evalScores) {
		if (freeScores.contains(ScoreCard.fullHouse)) {
			// TODO vi har tva par, kak ledigt

			AIDiceRethrow.twoPairToHouse(hand);
			AI.evalScores(hand.getValueArray(), evalScores);

			// fångar kåk direkt om vi ligger under par, kan inte få par
			if (AI.fullHouse(card, hand)) {
				return;
			}

			AIDiceRethrow.twoPairToHouse(hand);

			AI.evalScores(hand.getValueArray(), evalScores);

			// fångar kåk direkt om vi ligger under par, kan inte få par
			if (AI.fullHouse(card, hand)) {
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

	public static int betOnInt(ScoreCard card, Hand hand) {
		LinkedList<Integer> freeScores = card.getEmptyIndexes();

		int[] countedDices = new int[6];
		AI.countValues(hand.getValueArray(), countedDices);
		int value = 1;

		// omedlbart satsa pa mer en fyra av en sort
		for (int l : countedDices) {
			if (l >= 4) {
				return value;
			}
			value++;
		}

		int highestSum = 0;
		int highestDice = 1;

		for (int h = 0; h < countedDices.length; h++) {
			if (countedDices[h] * (h + 1) > highestSum) {
				highestSum = countedDices[h] * (h + 1);
				highestDice = h + 1;
			}
		}

		boolean freeUpThere = freeScores.contains(highestDice - 1);
		if (freeUpThere) {
			return highestDice;
		}

		int highestCopy = highestDice;
		highestSum = 0;
		for (int h = 0; h < countedDices.length; h++) {
			if (countedDices[h] * (h + 1) > highestSum
					&& (h + 1) != highestCopy) {
				highestSum = countedDices[h] * (h + 1);
				highestDice = h + 1;
			}
		}

		freeUpThere = freeScores.contains(highestDice - 1);
		if (freeUpThere) {
			return highestDice;
		}

		return highestCopy;

	}
	
	
	
	public static void allOfAKindDefensive(ScoreCard card, Hand hand, int kept){
		LinkedList<Integer> freeScores = card.getEmptyIndexes();

		boolean checked = AI.catchHand(hand, card);
		if (checked) {
			return;
		}
		int[] evalScore = new int[15];
		AI.evalScores(hand.getValueArray(), evalScore);

		if (freeScores.contains(kept - 1)
				&& evalScore[ScoreCard.threeOfAKind] != 0) {
			card.scoreValues[kept - 1] = evalScore[kept - 1];
			return;
		}

		if (evalScore[ScoreCard.fourOfAKind] != 0
				&& freeScores.contains(ScoreCard.fourOfAKind)) {
			card.scoreValues[ScoreCard.fourOfAKind] = evalScore[ScoreCard.fourOfAKind];
			return;
		}

		if (AI.fullHouse(card, hand)) {
			return;
		}

		if (freeScores.contains(kept - 1)) {
			card.scoreValues[kept - 1] = evalScore[kept - 1];
			return;
		}

		if (evalScore[ScoreCard.threeOfAKind] != 0
				&& freeScores.contains(ScoreCard.threeOfAKind)) {
			card.scoreValues[ScoreCard.threeOfAKind] = evalScore[ScoreCard.threeOfAKind];
			return;
		}

		if (evalScore[ScoreCard.twoPair] != 0
				&& freeScores.contains(ScoreCard.twoPair)) {
			card.scoreValues[ScoreCard.twoPair] = evalScore[ScoreCard.twoPair];
			return;
		}

		if (evalScore[ScoreCard.pair] != 0
				&& freeScores.contains(ScoreCard.pair)) {
			card.scoreValues[ScoreCard.pair] = evalScore[ScoreCard.pair];
			return;
		}


		Nolla.nolla(card, hand);

	}
	

	public static void allOfAKindAgressive(ScoreCard card, Hand hand, int kept) {
		LinkedList<Integer> freeScores = card.getEmptyIndexes();

		boolean checked = AI.catchHand(hand, card);
		if (checked) {
			return;
		}
		int[] evalScore = new int[15];
		AI.evalScores(hand.getValueArray(), evalScore);

		if (evalScore[ScoreCard.fourOfAKind] != 0
				&& freeScores.contains(ScoreCard.fourOfAKind)) {
			card.scoreValues[ScoreCard.fourOfAKind] = evalScore[ScoreCard.fourOfAKind];
			return;
		}

		if (AI.fullHouse(card, hand)) {
			return;
		}

		if (freeScores.contains(kept - 1)
				&& evalScore[ScoreCard.threeOfAKind] != 0) {
			card.scoreValues[kept - 1] = evalScore[kept - 1];
			return;
		}

		if (evalScore[ScoreCard.threeOfAKind] != 0
				&& freeScores.contains(ScoreCard.threeOfAKind)) {
			card.scoreValues[ScoreCard.threeOfAKind] = evalScore[ScoreCard.threeOfAKind];
			return;
		}

		if (evalScore[ScoreCard.twoPair] != 0
				&& freeScores.contains(ScoreCard.twoPair)) {
			card.scoreValues[ScoreCard.twoPair] = evalScore[ScoreCard.twoPair];
			return;
		}

		if (evalScore[ScoreCard.pair] != 0
				&& freeScores.contains(ScoreCard.pair)) {
			card.scoreValues[ScoreCard.pair] = evalScore[ScoreCard.pair];
			return;
		}

		if (freeScores.contains(kept - 1)) {
			card.scoreValues[kept - 1] = evalScore[kept - 1];
			return;
		}

		Nolla.nolla(card, hand);

	}

	public static void stegeKolla(ScoreCard card, Hand hand,
			LinkedList<Integer> freeScores) {
		int[] countedDices = new int[6];
		boolean[] stegarKollade = stegCheckArray(hand);
		AI.countValues(hand.getValueArray(), countedDices);

		boolean hadeRedan = AI.catchHand(hand, card);
		if (hadeRedan) {
			return;
		}

		//Printer.printArraybOOL(stegarKollade);

		// vvi kan fa liten stege och den ar ledig
		if (freeScores.contains(ScoreCard.smallStraight)) {
			////System.out.println("small straight free");
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
			if (!s.contains("" + k)) {
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
			if (!s.contains("" + k)) {
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
