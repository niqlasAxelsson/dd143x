package dd143x;

import java.util.LinkedList;

public class EarlyGameAI {

	public static void play(Hand hand, ScoreCard card) {
		LinkedList<Integer> freeScores = card.getEmptyIndexes();
		boolean checked = AI.catchHand(hand, card);
		
		if (checked){
			return;
		}
		int[] tempScore = new int[15];
		int[] countedDices = new int[AI.diceMaxValue];

		AI.countValues(hand.getValueArray(), countedDices);
		

		// we start with 3 or 4 of a kind and we havnt filled that value
		// throws 2 more times to collect those and fills in the score card
		// if we happen upon a straight or yatze that is filled in.
			
			int valueToKeep = valueToKeep(card, countedDices);
			AIDiceRethrow.allOfAKind(hand, valueToKeep);
			AI.evalScores(hand.getValueArray(), tempScore);
			AI.countValues(hand.getValueArray(), countedDices);
			

			checked = AI.catchHand(hand, card);
			if (checked){
				return;
			}
			// this is done 2 times, no more no less, so no own method
			valueToKeep = valueToKeep(card, countedDices);
			AIDiceRethrow.allOfAKind(hand, valueToKeep);
			AI.evalScores(hand.getValueArray(), tempScore);

			checked = AI.catchHand(hand, card);
			if(checked){
				return;
			}

			////System.out.println("nytt test");
			////System.out.println(valueToKeep);
			////System.out.println((freeScores.contains(ScoreCard.fourOfAKind)));
			////System.out.println(!freeScores.contains(valueToKeep -1));
			
			int[] howManyDoWeHave = new int[AI.diceMaxValue];
			AI.countValues(hand.getValueArray(), howManyDoWeHave);
			if (freeScores.contains(ScoreCard.fourOfAKind) && !freeScores.contains(valueToKeep -1) && (valueToKeep > 3)){
				
				if (howManyDoWeHave[valueToKeep -1] >= 4){
					card.scoreValues[ScoreCard.fourOfAKind] = 4*valueToKeep;
					return;
				}
				
			}
			

			//större än 3, större än indexet för 3 på tärningarna
			int highestPair = 0;
			boolean weHaveMore = false;
			for(int c = howManyDoWeHave.length-1; c  >=0; c --){
				if (howManyDoWeHave[c] == 2){
					if (highestPair ==0){
					highestPair = c+1;
				}
				}
				if (howManyDoWeHave[c] > 2){
					weHaveMore = true;
				}
			}
		
			
			if (highestPair >= 4 && !weHaveMore && freeScores.contains(ScoreCard.pair)){
		
				card.scoreValues[ScoreCard.pair] = highestPair*2;
				return;
			}
			
			// calc score to set
			int score = 0;
			for (int i : hand.getValueArray()) {
				if (i == valueToKeep) {
					score += i;
				}
			}
			
			// fill in the one that should now have 3, 4 or 5 of a kind
			card.scoreValues[valueToKeep - 1] = score;
			
			//System.out.println("igenom early");

	}

	/**
	 * calculates what value should be saved, does not consider straights since straight on first throw 
	 * should alredy have been caught, fullHous is split upp
	 * @param card
	 * @param countedDices an int[AI.diceMaxValue] were each position holds an int representing the number of dices having that value
	 * @return the value to go for
	 */
	public static int valueToKeep(ScoreCard card, int[] countedDices) {
		int valueToKeep = -1;
		
		
		
		LinkedList<Integer> freeScores = card.getEmptyIndexes();
		// find 3 or 4 of a kind and set so aim for that if not already filled
		// yatzy is alredy caught
		for (int diceValueTemp = AI.diceMaxValue; diceValueTemp >= 1; diceValueTemp--) {
			if ((countedDices[diceValueTemp - 1] >= 3)
					&& (freeScores.contains(diceValueTemp - 1))) {
				valueToKeep = diceValueTemp;
				return diceValueTemp;
			}

		}
		
		
		if (freeScores.contains(ScoreCard.fourOfAKind)){
		//	////System.out.println("vg;wieurkv-=wsdgk;iusvhk;svg'vsdfhk");
			for (int h = countedDices.length-1; h > 3; h --){
				if (countedDices[h] >= 4){
					return h+1;
				}
			}
		}
		
		// catch pair or broken straight and decide what to go for
		// starights already caught
		if (valueToKeep == -1) {
			for (int isThisARealNumber = countedDices.length - 1; isThisARealNumber >= 0; isThisARealNumber--) {
				if ((countedDices[isThisARealNumber] == 2)
						&& (card.scoreValues[isThisARealNumber] == -1)) {
					return isThisARealNumber+1;
				}
			}

			for (int anotherFakkingInt = AI.diceMaxValue; anotherFakkingInt >= 1; anotherFakkingInt--) {
				if (freeScores.contains(anotherFakkingInt - 1)
						&& (countedDices[anotherFakkingInt - 1] != 0)) {
					valueToKeep = anotherFakkingInt;
					return anotherFakkingInt;
				}
			}
		}
		
		for(int q = 1; q <=AI.diceMaxValue ; q++){
			if (card.scoreValues[q-1] == -1){
				return q;
			}
		}
		return 0;
	}

}
