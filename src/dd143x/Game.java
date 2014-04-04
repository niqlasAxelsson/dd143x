package dd143x;

public class Game {

	

	public static void playGame() throws Exception {

		ScoreCard scoreCard = new ScoreCard();
		Hand hand;
		int roundCounter = 1;
		while (!scoreCard.isCardFilled()){
			//System.out.println();
			
//			System.out.println("roundcounter: " + roundCounter);
			hand = new Hand();
		//	Printer.printArray(hand.getValueArray());
			AI.ai(scoreCard, hand);
//			
			int freeSpaces = scoreCard.getEmptyIndexes().size();

//			if ((15 - freeSpaces +1) != roundCounter){
//				throw new Exception("spelkortet är fel ifyllt");
//			}
			if (roundCounter > 16){
				Printer.printArray(scoreCard.scoreValues);
				throw new Exception("To many Rounds");
			}
			//System.out.println(roundCounter);
			roundCounter ++;
		}
	//	System.out.println("Final score: " + scoreCard.finalScore());
		//System.out.println("Fick vi bonus: " + scoreCard.doWeHaveBonus());
		Main.printer.writeInt(scoreCard.finalScore());
	
	}

}
