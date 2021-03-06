package dd143x;

/**
 * Part of the AI to caluculate which dices to rethrow and rethows them
 * 
 * @author Niklas
 * 
 */
public class AIDiceRethrow {

	/**
	 * rethrows every dice in @hand that doesnt have the value @value
	 * 
	 * @param hand
	 * @param value
	 */
	public static void allOfAKind(Hand hand, int value) {
		for (Dice dice : hand.getDices()) {
			if (dice.value != value) {
				dice.throwDice();
			}
		}
		hand.throwed();
	}

	/**
	 * rethrow a hand that has pair, tripple or quardruple to the higest value
	 * if there are more then two pairs.
	 * 
	 * @param hand
	 * @param nrDices
	 */
	public static void nrDices(Hand hand, int nrDices) {
		int[] countedDices = new int[6];
		for (Dice dice : hand.getDices()) {
			countedDices[dice.getValue() - 1]++;
		}

		int valueToKeep = 1;

		for (int i = 0; i < 6; i++) {
			if (countedDices[i] == nrDices) {
				valueToKeep = i + 1;
			}
		}

		for (Dice dice : hand.getDices()) {
			if (dice.value != valueToKeep) {
				dice.throwDice();
			}
		}
		hand.throwed();
	}

	/**
	 * requires a twoPair hand in to work correctly
	 * 
	 * @param hand
	 */
	public static void twoPair(Hand hand) {
		int[] countedDices = new int[6];
		AI.countValues(hand.getValueArray(), countedDices);
		int valueToRethrow = 1;
		for (int i = 0; i < countedDices.length; i++) {
			if (countedDices[i] == 1) {
				valueToRethrow = i + 1;
			}
		}

		for (Dice dice : hand.getDices()) {
			if (dice.value == valueToRethrow) {
				dice.throwDice();
			}
		}

		hand.throwed();
	}

	/**
	 * rethrows every surplus copy of a dice value in order to get a straight
	 * 
	 * @param hand
	 */
	public static void largeStraight(Hand hand) {
		boolean[] haveThisValue = { false, false, false, false, false, false };
		for (Dice dice : hand.getDices()) {
			if (haveThisValue[dice.getValue() - 1] || dice.value == 1) {
				dice.throwDice();
			} else {
				haveThisValue[dice.getValue() - 1] = true;
			}
		}
		hand.throwed();

	}

	/**
	 * rethrows every surplus copy of a dice value in order to get a straight
	 * 
	 * @param hand
	 */
	public static void smallStraight(Hand hand) {
		boolean[] haveThisValue = { false, false, false, false, false, false };
		for (Dice dice : hand.getDices()) {
			if (haveThisValue[dice.getValue() - 1] || dice.value == 6) {
				dice.throwDice();
			} else {
				haveThisValue[dice.getValue() - 1] = true;
			}
		}
		hand.throwed();

	}

	/**
	 * Whatto do in case of full house on first or second throw. needs to know
	 * the status of the scorecard to do correct decission
	 * 
	 * @param card
	 * @param hand
	 */
	public static void fullHouse(ScoreCard card, Hand hand) {
		int roll = hand.getRoll();

		int[] countedDice = new int[AI.diceMaxValue];
		int weHaveThree = 0;
		for (int i = 0; i < countedDice.length; i++) {
			if (countedDice[i] == 3) {
				weHaveThree = i + 1;
			}
		}

		if (weHaveThree == 0) {
			throw new IllegalArgumentException(
					"tried fullhouse rethrow without a tripple");
		}

		boolean upperFilled = false;
		boolean fourFilled = false;

		if (card.scoreValues[weHaveThree - 1] != 0) {
			upperFilled = true;
		}

		if (card.scoreValues[ScoreCard.fourOfAKind] != 0) {
			fourFilled = true;
		}

		AI.evalScores(hand.getValueArray(), countedDice);
		if (roll == 1) {
			if (!upperFilled) {
				for (Dice dice : hand.getDices()) {
					if (dice.value != weHaveThree) {
						dice.throwDice();
					}
				}
			}

			if (upperFilled && !fourFilled) {
				for (Dice dice : hand.getDices()) {
					if (dice.value != weHaveThree) {
						dice.throwDice();
					}
				}
			}

		}

		if (roll == 2) {
			if (!upperFilled && weHaveThree >= 4) {
				for (Dice dice : hand.getDices()) {
					if (dice.value != weHaveThree) {
						dice.throwDice();
					}
				}
			}
		}

		hand.throwed();
	}

	public static void twoPairToHouse(Hand hand) {
		int i = 0;
		int j = 0;

		int[] countedDices = new int[6];
		AI.countValues(hand.getValueArray(), countedDices);

		for (int c = 0; c < countedDices.length; c++) {
			if (countedDices[c] == 2) {
				if (i == 0) {
					i = c + 1;
				} else {
					j = c + 1;
				}
			}
		}

		for (Dice dice : hand.getDices()) {
			if (dice.value != i && dice.value != j) {
				dice.throwDice();
			}
		}
		hand.throwed();
	}

	public static void getFullHouse(Hand hand) {
		int[] countedDices = new int[6];
		AI.countValues(hand.getValueArray(), countedDices);
		int valueToKeep;
		int trissScore = AI.checkTripleScore(hand.getValueArray());
		if (trissScore != 0) {
			valueToKeep = trissScore / 3;
			int otherValue = 0;
			for (int e = 5; e >= 2; e--) {
				if (countedDices[e] == 1) {
					otherValue = e + 1;
					break;
				}
			}
			for (Dice dice : hand.getDices()) {
				if (dice.value != valueToKeep && dice.value != otherValue) {
					dice.throwDice();
				}
			}
			hand.throwed();
			return;

		}

		if (AI.doublePairScore(hand.getValueArray()) != 0) {
			twoPairToHouse(hand);
			return;
		}

		int pairScore = AI.pairScore(hand.getValueArray());
		if (pairScore != 0) {
			valueToKeep = pairScore / 2;
			int otherValue = 0;
			for (int e = 5; e >= 2; e--) {
				if (countedDices[e] == 1) {
					otherValue = e + 1;
					break;
				}
			}
			for (Dice dice : hand.getDices()) {
				if (dice.value != valueToKeep && dice.value != otherValue) {
					dice.throwDice();
				}
			}
			hand.throwed();
			return;
		}

		int i = 0;
		int j = 0;
		for (int c = countedDices.length - 1; c >= 0; c--) {
			if (countedDices[c] == 1) {
				if (i == 0) {
					i = c + 1;
				} else {
					j = c + 1;
					break;
				}
			}
		}

		for (Dice dice : hand.getDices()) {
			if (dice.value != i && dice.value != j) {
				dice.throwDice();
			}
		}
		hand.throwed();
	}

	public static void getTwoPair(Hand hand) {
		int[] countedDices = new int[6];
		AI.countValues(hand.getValueArray(), countedDices);

		int keep1 = 0;
		int keep2 = 0;

		for (int i = 5; i >= 0; i--) {

			if (countedDices[i] > 0) {
				if (keep1 == 0) {
					keep1 = i + 1;
				} else if (keep2 == 0) {
					keep2 = i + 1;
				}

				if (keep1 != 0 && keep2 != 0) {
					if (keep1 > keep2) {
						if (countedDices[keep2 - 1] < countedDices[i]) {
							keep2 = i + 1;
						}
					} else {
						if (countedDices[keep1 - 1] < countedDices[i]) {
							keep1 = i + 1;
						}
					}
				}

			}

		}

		for (Dice dice : hand.getDices()){
			if(dice.value != keep1 && keep2 != dice.value){
			//	System.out.println("kastar om: "+ dice.value);
				dice.throwDice();
			}
			if (countedDices[dice.value -1] > 2){
				countedDices[dice.value-1] --;
				//System.out.println("kastar om: "+ dice.value);
				dice.throwDice();
			}
			
		}
		
		hand.throwed();

	}

}
