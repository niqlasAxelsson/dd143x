package dd143x;

public class Game {

	

	public static void playGame() {

		ScoreCard scoreCard = new ScoreCard();
		Hand hand;
		
		while (!scoreCard.isCardFilled()){
			hand = new Hand();
			//AI anrop
			Main.printer.writeInt(scoreCard.finalScore());
		}
	
	}

}
