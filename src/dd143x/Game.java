package dd143x;

public class Game {

	

	public static void playGame() {

		ScoreCard scoreCard = new ScoreCard();
		Hand hand;
		//int roundCounter = 1;
		while (!scoreCard.isCardFilled()){
			hand = new Hand();
			AI.ai(scoreCard, hand);
			//roundCounter ++;
		}
		Main.printer.writeInt(scoreCard.finalScore());
	
	}

}
