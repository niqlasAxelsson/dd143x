package dd143x;

import java.util.Arrays;

public class Hand {
	
	private Dice[] dices = new Dice[5];
	private int roll = 1;
	
	public Hand(){
		for (int i = 0 ;i < 5; i ++ ){
			dices[i] = new Dice();
		}
	}
	
	public Dice[] getDices(){
		return dices;
	}
	
	public void throwed(){
		roll ++;
	}
	
	public int getRoll(){
		return roll;
	}
	
	public int[] getValueArray(){
		int[] returning = new int[dices.length];

		for (int i =0; i < dices.length; i ++){
			returning[i] = dices[i].getValue();
		}
		Arrays.sort(returning);
		return returning;
		
	}
	
	
	public void reThrow(int[] indexToThrow){
		for (int i : indexToThrow){
			dices[i].throwDice();
		}
		roll ++;
	}
	
	
	public void setDices(int[] values){
		for (int i = 0; i < 5; i++){
			dices[i].value = values[i];
		}
	}
	
	
}
