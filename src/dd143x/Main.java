package dd143x;

public class Main {
	int test;
	public static Printer printer;
	
	
	
	
	public static void main(String[] args) {
		printer = new Printer();
		if (args.length == 0 ){
			throw new IllegalArgumentException("No Input");
		}
		int errors = 0;
		int nrGames = Integer.parseInt(args[0]);
		for (int gameCounter = 1 ; gameCounter <= nrGames ; gameCounter ++){
		//	System.out.println("Gamecounter: "+ gameCounter);
			try {
				Game.playGame();
			} catch (Exception e) {
				gameCounter --;
				errors ++;
			}
		}
		System.out.println("errors: " + errors);
		printer.close();
		
		
	}
	
	
}
