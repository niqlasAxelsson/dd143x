package dd143x;

public class Main {
	int test;
	public static Printer printer;
	
	
	
	
	public Main(String[] args) {
		printer = new Printer();
		if (args.length == 0 ){
			throw new IllegalArgumentException("No Input");
		}
		int nrGames = Integer.parseInt(args[0]);
		for (int gameCounter = 1 ; gameCounter <= nrGames ; gameCounter ++){
			Game.playGame();
		}
		
		
	}
	
	
}
