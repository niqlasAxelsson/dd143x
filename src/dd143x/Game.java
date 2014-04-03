package dd143x;

public class Game {

	

	public static void playGame() throws Exception {

		ScoreCard scoreCard = new ScoreCard();
		Hand hand;
		int roundCounter = 1;
		while (!scoreCard.isCardFilled()){
//			System.out.println("roundcounter: " + roundCounter);
			hand = new Hand();
			AI.ai(scoreCard, hand);
//			
			int freeSpaces = scoreCard.getEmptyIndexes().size();

			if ((15 - freeSpaces) != roundCounter){
				throw new Exception("spelkortet är fel ifyllt");
			}
			if (roundCounter > 15){
				throw new Exception("To many Rounds");
			}
			
			roundCounter ++;
		}
		Main.printer.writeInt(scoreCard.finalScore());
	
	}

}
