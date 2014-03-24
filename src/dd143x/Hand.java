package dd143x;

public class Hand {
	
	private Dice[] dices = new Dice[5];
	
	
	public Hand(){
		for (int i = 0 ;i < 5; i ++ ){
			dices[i] = new Dice();
		}
	}
	
	public Dice[] getHand(){
		return dices;
	}
	
	public int[] getValueArray(){
		int[] returning = new int[dices.length];

		for (int i =0; i < dices.length; i ++){
			returning[i] = dices[i].getValue();
		}
		
		return returning;
		
	}
	
	
	public void reThrow(int[] indexToThrow){
		for (int i : indexToThrow){
			dices[i].throwDice();
		}
	}
	
}
