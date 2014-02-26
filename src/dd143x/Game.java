package dd143x;

public class Game {

	public Game() {
	}

	public static int playGame() {

		ScoreCard scoreCard = new ScoreCard();
		Hand hand = new Hand();
		
		while (!scoreCard.isCardFilled()){
			
		}
		
		
		return scoreCard.finalScore();
	}

}
