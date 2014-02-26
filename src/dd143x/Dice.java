package dd143x;

import java.util.Random;

public class Dice {
	
	public int value;
	private Random random;
	
	public Dice() {
		random = new Random();
	}
	
	public int getValue() {
		return value;
	}
	
	public void throwDice() {
		value = random.nextInt(6) + 1;
	}

}
