package dd143x;

import java.util.Arrays;
import java.util.LinkedList;

import javax.script.ScriptContext;

public class AI {

	final static private int diceMaxValue = 6;

	public static void ai(ScoreCard scoreCard, Hand hand) {
		LinkedList<Integer> emptyIndex = scoreCard.getEmptyIndexes();
		int firstIndex = emptyIndex.poll();
		Dice[] dices = hand.getHand();
		int score = 0;
		int[] diceValues = new int[5];
		int tempCounter = 0;

		for (Dice dice : dices) {
			diceValues[tempCounter] = dice.getValue();
			score += dice.getValue();
			tempCounter++;
		}

		Arrays.sort(diceValues);

		// set a score to the different scoreCard options
		int[] scoreScore = new int[15];
		evalScores(diceValues, scoreScore);
		
		int highestIndex = 0;
		int highestScore = 0;
		LinkedList<Integer> freeScores = scoreCard.getEmptyIndexes();
		for (int i : freeScores){
			if (scoreScore[i] > highestScore){
				highestIndex = i;
				highestScore = scoreScore[i];
			}
		}
		if(highestScore == 0){
			scoreCard.scoreValues[freeScores.peek()] = 0;
		}else{
		scoreCard.scoreValues[highestIndex] = highestScore;
		}
		// scoreCard.scoreValues[firstIndex] = score; //TODO metoden ger
		// indexOutOfBoundsExeption
	}

	public static void evalScores(int[] diceValues, int[] scoreScore) {

		// poäng för lika värden.
		for (int i = 1; i <= diceMaxValue; i++) {
			int score = numberScore(diceValues, i);
			scoreScore[i - 1] = score;
		}

		scoreScore[ScoreCard.pair] = pairScore(diceValues);
		scoreScore[ScoreCard.twoPair] = doublePairScore(diceValues);
		scoreScore[ScoreCard.threeOfAKind] = checkTripleScore(diceValues);
		scoreScore[ScoreCard.fourOfAKind] = checkQuadruopleScore(diceValues);
		scoreScore[ScoreCard.smallStraight] = smallStraightScore(scoreScore);
		scoreScore[ScoreCard.largeStraight] = largeStraightScore(scoreScore);
		scoreScore[ScoreCard.house] = fullHouseScore(scoreScore);
		scoreScore[ScoreCard.chance] = chansScore(diceValues);
		scoreScore[ScoreCard.yatzy] = yatzyScore(diceValues);
	}

	// beräkna poäng för #of a kind. summerar poängen för de antal
	// tärningar som
	// har
	// värdet number
	public static int numberScore(int[] dices, int number) {
		int score = 0;
		for (int i : dices) {
			if (i == number) {
				score += i;
			}
		}
		return score;
	}

	// alla 6:or är för att det finns 6 olika värden på tärningar.
	public static int pairScore(int[] dices) {
		int[] valueTimes = new int[diceMaxValue];
		int[] scores = new int[diceMaxValue];

		// [0] håller poängen
		// [1] håller vilken valör det var som gav poängen
		int returning = 0;

		// räknar de olika valörerna
		countValues(dices, valueTimes);
		

		// beäkna poängen för de olika paren,
		// måste vara par
		for (int j = 0; j < diceMaxValue; j++) {
			if (valueTimes[j] == 2) {
				scores[j] = (j + 1) * 2;
			}
		}

		// beräkna vilken poäng som är störst.
		for (int k = 0; k < diceMaxValue; k++) {
			if (scores[k] >= returning) {
				returning= scores[k];
			}
		}

		return returning;
	}

	public static void countValues(int[] dices, int[] valueTimes) {

		for (int i : dices) {
			// simply add 1 to the corresponding place in the answer array
			valueTimes[i - 1]++;
		}
	}

	private static int doublePairScore(int[] dices) {
		int returning = 0;

		int[] valueTimes = new int[diceMaxValue];
		countValues(dices, valueTimes);
		boolean firstPair = false;

		int eyeCounter = 1;
		int firstPairEyes = 0;

		for (int i : valueTimes) {
			if (i == 2 && !firstPair) {
				firstPairEyes = eyeCounter;
				firstPair = true; // we have found our first pair
			} else if (i == 2 && firstPair) {
				return firstPairEyes * 2 + eyeCounter * 2;
			}
			eyeCounter++;
		}

		// if not 2 pair return 0
		return returning;
	}

	public static int checkTripleScore(int[] dices) {
		int trippleDice = 1;
		int[] valueTimes = new int[6];
		countValues(dices, valueTimes);
		for (int i : valueTimes) {
			if (i == 3) { // we have three of this dice, dice points indicated
							// by trippleDice
				return trippleDice * 3;
			}
			trippleDice++;
		}

		return 0;
	}

	public static int checkQuadruopleScore(int[] dices) {
		int trippleDice = 1;
		int[] valueTimes = new int[6];
		countValues(dices, valueTimes);
		for (int i : valueTimes) {
			if (i == 4) { // we have three of this dice, dice points indicated
							// by trippleDice
				return trippleDice * 4;
			}
			trippleDice++;
		}

		return 0;
	}

	public static int smallStraightScore(int[] hand) {
		int returning = 0;
		boolean smallStraightTrue = true;
		for (int i = 0; i < 5; i++) {
			if (i != i + 1) {
				// This will only be false iff we dont have a small straight
				// since dice i should have i as score, 1 index as is custom
				// with board games
				smallStraightTrue = false;
			}
		}

		if (smallStraightTrue) {
			returning = 15;
		}

		return returning;
	}

	public static int largeStraightScore(int[] hand) {
		int returning = 0;
		boolean smallStraightTrue = true;
		for (int i = 0; i < 5; i++) {
			if (i != i + 2) {
				// This will only be false iff we dont have a small straight
				// since dice i should have i as score, 1 index as is custom
				// with board games
				smallStraightTrue = false;
			}
		}

		if (smallStraightTrue) {
			returning = 20;
		}

		return returning;
	}

	
	public static int fullHouseScore(int[] hand){
		int returning = 0;
		int pairEyes = 0;
		int trippleEyes = 0;
		for (int i = 0; i < hand.length ; i ++){
			if (hand[i] == 2){
				pairEyes = i+1;
			}
			if (hand[i] == 3){
				trippleEyes = i+1;
			}
		}
		
		if (pairEyes != 0 && trippleEyes !=0){
			returning = pairEyes*2 + trippleEyes*3;
		}
		
		
		return returning;
	}
	
	public static int chansScore(int[] hand) {
		int sum = 0;
		
		for (int i : hand){
			sum += i;
		}
		return sum;
	}

	public static int yatzyScore(int[] hand){

		int[] evaluated = new int[diceMaxValue];
		countValues(hand, evaluated);
		for (int i : evaluated){
			if (i ==5){
				return 50;
			}
		}
		return 0;
	}
	
}
