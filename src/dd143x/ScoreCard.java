package dd143x;

import java.util.LinkedList;

public class ScoreCard {

public static final int ones = 0;
	public static final int twos = 1;
	public static final int threes = 2;
	public static final int fours = 3;
	public static final int fives = 4;
	public static final int sixes = 5;
	public static final int pair = 6;
	public static final int twoPair = 7;
	public static final int threeOfAKind = 8;
	public static final int fourOfAKind = 9;
	public static final int smallStraight = 10;
	public static final int largeStraight = 11;
	public static final int house = 12;
	public static final int chance = 13;
	public static final int yatzy = 14;
	
	public static final int bonus = 50;
	
	public int[] scoreValues = new int[15]; 
	
	public ScoreCard() {
		for (int i = 0; i < scoreValues.length; i++){
			scoreValues[i] = -1;
		}
		
	}
	
	public int finalScore() {
		int sum = 0;
		int pointsToBonus = 63;
		
		for(int i = 0; i < scoreValues.length; i++) {
			sum += scoreValues[i];
			if (i == 5 && sum >= pointsToBonus) {
				sum += bonus;
			}
		}
		return sum;
	}    
	
	public boolean isCardFilled(){
		return getEmptyIndexes().isEmpty();
	}
	
	
	public LinkedList<Integer> getEmptyIndexes(){
		LinkedList<Integer> emptyIndexes = new LinkedList<Integer>();
		for(Integer i = 0; i < scoreValues.length ; i++){
			if (scoreValues[i] == -1 ){
				emptyIndexes.add(i);
			}
		}
		return emptyIndexes;
	}

}
