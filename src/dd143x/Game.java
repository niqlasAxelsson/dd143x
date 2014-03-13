package dd143x;

public class Game {

	

	public static void playGame() {

		ScoreCard scoreCard = new ScoreCard();
		Hand hand;
//		int roundCounter = 1;
		while (!scoreCard.isCardFilled()){
//			System.out.println("roundcounter: " + roundCounter);
			hand = new Hand();
			AI.ai(scoreCard, hand);
//			roundCounter ++;
//			if (roundCounter > 25){
//				System.exit(0);
//			}
		}
		Main.printer.writeInt(scoreCard.finalScore());
	
	}

}
