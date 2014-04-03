package dd143x;

import java.util.LinkedList;

public class LateGameAI {

	public static void play(ScoreCard card, Hand hand) {
		agressive(card, hand);

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
			System.out.println("tva par poang: " + evalScores[ScoreCard.twoPair]);
			MidGameAI.twoPairMid(card, hand, freeScores, evalScores);
			System.out.println("111");
			return;

		}

		boolean wentForStraight = false;

		int stege = stegCheck(card, hand);
		if (stege == 1) {
			System.out.println("222");
			AIDiceRethrow.smallStraight(hand);
			AIDiceRethrow.smallStraight(hand);
			if (AI.catchHand(hand, card)) {
				return;
			}
			wentForStraight = true;
		}
		if (stege == 2) {
			System.out.println("333");
			AIDiceRethrow.largeStraight(hand);
			AIDiceRethrow.largeStraight(hand);
			if (AI.catchHand(hand, card)) {
				return;
			}
			wentForStraight = true;
		}

		if (wentForStraight) {
			System.out.println("4444");
			int[] evalScore = new int[15];
			AI.evalScores(hand.getValueArray(), evalScore);
			for (int l = evalScore.length - 1; l >= 0; l--) {
				if (l != ScoreCard.chance && evalScore[l] != 0
						&& freeScores.contains(l)) {
					card.scoreValues[l] = evalScore[l];
					return;
				}
			}
			Nolla.nollaNere(card);
			return;
		}

		if ((card.getEmptyIndexes().size() == 1 && card.scoreValues[ScoreCard.smallStraight] == -1)
				|| (card.getEmptyIndexes().size() == 2
						&& card.scoreValues[ScoreCard.smallStraight] == -1 && card.scoreValues[ScoreCard.chance] == -1)) {
			System.out.println("5555");
			AIDiceRethrow.smallStraight(hand);
			AIDiceRethrow.smallStraight(hand);
			if (AI.catchHand(hand, card)) {
				return;
			}
			Nolla.nollaNere(card);
			return;

		}

		if ((card.getEmptyIndexes().size() == 1 && card.scoreValues[ScoreCard.largeStraight] == -1)
				|| (card.getEmptyIndexes().size() == 2
						&& card.scoreValues[ScoreCard.largeStraight] == -1 && card.scoreValues[ScoreCard.chance] == -1)) {
			System.out.println("6666");
			AIDiceRethrow.largeStraight(hand);
			AIDiceRethrow.largeStraight(hand);
			if (AI.catchHand(hand, card)) {
				return;
			}
			Nolla.nollaNere(card);
			return;
		}

		if ((card.getEmptyIndexes().size() == 2 && (card.scoreValues[ScoreCard.smallStraight] == -1 && card.scoreValues[ScoreCard.largeStraight] == -1))
				|| (card.getEmptyIndexes().size() == 3 && (card.scoreValues[ScoreCard.smallStraight] == -1
						&& card.scoreValues[ScoreCard.largeStraight] == -1 && card.scoreValues[ScoreCard.chance] == -1))) {
			System.out.println("7777");
			AIDiceRethrow.largeStraight(hand);
			if (AI.catchHand(hand, card)) {
				return;
			}
			AIDiceRethrow.largeStraight(hand);
			if (AI.catchHand(hand, card)) {
				return;
			}
			Nolla.nollaNere(card);
			return;
		}

		if (AI.catchHand(hand, card)) {
			return;
		}
		if (AI.fullHouse(card, hand)) {
			return;
		}

		if ((freeScores.contains(ScoreCard.fullHouse) || freeScores
				.contains(ScoreCard.twoPair))
				&& AI.doublePairScore(hand.getValueArray()) != 0) {
			System.out.println("888");
			MidGameAI.twoPairMid(card, hand, freeScores, evalScores);
			return;
		}

		if (freeScores.contains(ScoreCard.fullHouse)
				&& AI.checkTripleScore(hand.getValueArray()) != 0) {
		
			System.out.println("99999");
			fullHouse(card, hand, freeScores, evalScores);
			return;
		}

		if ((freeScores.size() == 1 && freeScores.contains(ScoreCard.fullHouse))
				|| (freeScores.size() == 2
						&& freeScores.contains(ScoreCard.fullHouse) && freeScores
							.contains(ScoreCard.chance))) {
			
			System.out.println("aaaaa");
			fullHouse(card, hand, freeScores, evalScores);
		}

		if ((freeScores.size() == 1 && freeScores.contains(ScoreCard.twoPair))
				|| (freeScores.size() == 2
						&& freeScores.contains(ScoreCard.twoPair) && freeScores
							.contains(ScoreCard.chance))) {
			System.out.println("bbbb");
			AIDiceRethrow.getTwoPair(hand);
			AIDiceRethrow.getTwoPair(hand);
			
			int score = AI.doublePairScore(hand.getValueArray());
			if (score != 0){
				card.scoreValues[ScoreCard.twoPair] = score;
				return;
			}
			Nolla.nolla(card, hand);
			return;

		}
		
		if (freeScores.size() == 1 && freeScores.contains(ScoreCard.chance)){
			System.out.println("cccc");
			
			goForChans(card, hand);
			
			return;
		}

		
		xOfAKind(card, hand);
		
	System.out.println("igenom late");
	}

	
	
	public static void xOfAKind(ScoreCard card, Hand hand){
		MidGameAI.allOfAKindAgressive(card, hand, MidGameAI.betOnInt(card, hand));
	}
	
	
	public static void goForChans(ScoreCard card, Hand hand){
		for (Dice dice : hand.getDices()){
			if (dice.value < 4){
				dice.throwDice();
			}
		}
		for (Dice dice : hand.getDices()){
			if (dice.value < 4){
				dice.throwDice();
			}
		}
		
		card.scoreValues[ScoreCard.chance] = AI.chansScore(hand.getValueArray());
	}
	
	
	
	/**
	 * 
	 * @param card
	 * @param hand
	 * @return 1 liten stege, 2 stor stege, 0 ingen stege
	 */
	public static int stegCheck(ScoreCard card, Hand hand) {
		LinkedList<Integer> freeScores = card.getEmptyIndexes();

		String s = new String();
		for (int k : hand.getValueArray()) {
			if (!s.contains("" + k)) {
				s += k;
			}
		}

		boolean first = s.contains("123");
		boolean second = s.contains("234");
		boolean third = s.contains("345");
		boolean forth = s.contains("456");

		if (freeScores.contains(ScoreCard.smallStraight)
				&& (first || second || third)) {
			return 1;
		}

		if (freeScores.contains(ScoreCard.largeStraight)
				&& (second || third || forth)) {
			return 2;
		}

		return 0;
	}

	public static void fullHouse(ScoreCard card, Hand hand,
			LinkedList<Integer> freeScores, int[] evalScores) {
		if (freeScores.contains(ScoreCard.fullHouse)) {

			AIDiceRethrow.getFullHouse(hand);
			AI.evalScores(hand.getValueArray(), evalScores);

			// fångar kåk direkt om vi ligger under par, kan inte få par
			if (AI.fullHouse(card, hand)) {
				return;
			}

			if (AI.catchHand(hand, card)) {
				return;
			}

			AIDiceRethrow.getFullHouse(hand);

			AI.evalScores(hand.getValueArray(), evalScores);

			if (AI.fullHouse(card, hand)) {
				return;
			}

			if (evalScores[ScoreCard.fourOfAKind] != 0
					&& freeScores.contains(ScoreCard.fourOfAKind)) {
				card.scoreValues[ScoreCard.fourOfAKind] = evalScores[ScoreCard.fourOfAKind];
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

			// nolla
			Nolla.nolla(card, hand);
			return;

		}
	}

}
