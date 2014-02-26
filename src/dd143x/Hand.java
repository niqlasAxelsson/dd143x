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
	
	public void reThrow(int[] indexToThrow){
		for (int i : indexToThrow){
			dices[i].throwDice();
		}
	}
	
}
