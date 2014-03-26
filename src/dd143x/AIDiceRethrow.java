package dd143x;


/**
 * Part of the AI to caluculate which dices to rethrow and rethows them
 * @author Niklas
 *
 */
public class AIDiceRethrow {

	
	/**
	 * rethrows every dice in @hand that doesnt have the value @value
	 * @param hand
	 * @param value
	 */
	public static void rehrowInt(Hand hand, int value){
		for (Dice dice : hand.getHand()){
			if (dice.value != value){
				dice.throwDice();
			}
		}
	}
	
	/**
	 * rethrow a hand that has pair, tripple or quardruple to the higest value if there are more then two pairs.
	 * @param hand
	 * @param nrDices
	 */
	public static void rethrowNrDices(Hand hand, int nrDices){
		int[] countedDices = new int[6];
		for (Dice dice: hand.getHand()){
			countedDices[dice.getValue()-1] ++;
		}
		
		int valueToKeep = 1;
		
		for ( int i =0; i < 6; i++){
			if (countedDices[i] == nrDices){
				valueToKeep = i+1;
			}
		}
		
		for (Dice dice : hand.getHand()){
			if (dice.value != valueToKeep){
				dice.throwDice();
			}
		}
	}
	
	
	/**
	 * requires a twoPair hand in to work correctly
	 * @param hand
	 */
	public static void rethrowTwoPair(Hand hand){
		int[] countedDices = new int[6];
		AI.countValues(hand.getValueArray(), countedDices);
		int valueToRethrow = 1;
		for (int i  = 0; i < countedDices.length; i++){
			if (countedDices[i] == 1){
				valueToRethrow = i+1;
			}
		}
		
		for (Dice dice: hand.getHand()){
			if (dice.value == valueToRethrow){
				dice.throwDice();
			}
		}
		
	}
	
	
	/**
	 * rethrows every surplus copy of a dice value in order to get a straight
	 * @param hand
	 */
	public static void rethrowStraight(Hand hand) {
		boolean[] haveThisValue = {false, false, false, false, false, false};
		for (Dice dice: hand.getHand()){
			if (haveThisValue[dice.getValue()-1]){
				dice.throwDice();
			}else{
				haveThisValue[dice.getValue()-1] = true;
			}
		}
		
		
	}
	
	
}
