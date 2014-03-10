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

		// scoreCard.scoreValues[firstIndex] = score; //TODO metoden ger
		// indexOutOfBoundsExeption
	}

	private static void evalScores(int[] diceValues, int[] scoreScore) {

		// poäng för lika värden.
		for (int i = 1; i <= diceMaxValue; i++) {
			int score = numberScore(diceValues, i);
			scoreScore[i - 1] = score;
		}

		scoreScore[ScoreCard.pair] = pairScore(diceValues)[0];
		scoreScore[ScoreCard.twoPair] = doublePairScore(diceValues);
		scoreScore[ScoreCard.threeOfAKind] = checkTripleScore(diceValues);
	}

	// beräkna poäng för #of a kind. summerar poängen för de antal tärningar som
	// har
	// värdet number
	private static int numberScore(int[] dices, int number) {
		int score = 0;
		for (int i : dices) {
			if (i == number) {
				score += i;
			}
		}
		return score;
	}

	// alla 6:or är för att det finns 6 olika värden på tärningar.
	private static int[] pairScore(int[] dices) {
		int[] valueTimes = new int[diceMaxValue];
		int[] scores = new int[diceMaxValue];

		// [0] håller poängen
		// [1] håller vilken valör det var som gav poängen
		int[] returning = new int[2];

		// räknar de olika valörerna
		countValues(dices, valueTimes);
		//
		// System.out.println("NrCount");
		// for (int v : valueTimes){
		// System.out.println(v);
		// }
		// System.out.println();

		// beäkna poängen för de olika paren,
		// måste vara par
		for (int j = 0; j < diceMaxValue; j++) {
			if (valueTimes[j] == 2) {
				scores[j] = (j + 1) * 2;
			}
		}

		// beräkna vilken poäng som är störst.
		for (int k = 0; k < diceMaxValue; k++) {
			if (scores[k] >= returning[0]) {
				returning[0] = scores[k];
				returning[1] = k + 1;
			}
		}

		return returning;
	}

	private static void countValues(int[] dices, int[] valueTimes) {
		// for (int j : dices){
		// System.out.println(j);
		// }

		for (int i : dices) {
			// try{
			valueTimes[i - 1]++;
			// }catch(IndexOutOfBoundsException e){
			// System.out.println("Index Error"+i);
			// }
		}
	}

	private static int doublePairScore(int[] dices) {
		int returning = 0;
		int[] valueTimes = new int[6]
		
		
//		int[] firstPairScore = pairScore(dices);
//		int valueFirstPair = firstPairScore[0];
//		int diceToRemove = firstPairScore[1];
//		if (valueFirstPair == 0) {
//			return returning;
//		}
//
//		int[] remainingDices = new int[3];
//		int tempCounter = 0;
//		for (int i = 0; i < 5; i++) {
//			if (dices[i] != diceToRemove) {
//				remainingDices[tempCounter] = dices[i];
//				// System.out.println(dices[i] + "  " + tempCounter);
//				tempCounter++;
//			}
//		}
//
//		int[] secondpair = pairScore(remainingDices);
//
//		// vi har inte tvåpar
//		if (secondpair[0] == 0) {
//			return returning;
//		}
//
//		returning = firstPairScore[0] + secondpair[0];
		return returning;
	}
	
	private static int checkTripleScore(int[] dices){
		int trippleDice = 1;
		int[] valueTimes = new int[6];
		countValues(dices, valueTimes);
		for (int i : valueTimes){
			if (i == 3){ //we have three of this dice, dice points indicated by trippleDice
				return trippleDice*3;
			}
			trippleDice ++;
		}
		
		return 0;
	}
	
	private static int checkQuadruopleScore(int[] dices){
		int trippleDice = 1;
		int[] valueTimes = new int[6];
		countValues(dices, valueTimes);
		for (int i : valueTimes){
			if (i == 4){ //we have three of this dice, dice points indicated by trippleDice
				return trippleDice*4;
			}
			trippleDice ++;
		}
		
		return 0;
	}
	
	
}
