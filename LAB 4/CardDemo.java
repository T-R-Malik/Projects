public class CardDemo{
	public static void main(String args[]){
		String names[] = {"Tayyaba", "Adila", "Habiba"};
		Game g1 = new Game(names);
		System.out.println("");
		g1.showPlayers();

		System.out.println("");
		g1.showAllHands();

		System.out.println("");
		g1.dealCards();
		g1.showAllHands();

		System.out.println("");
		g1.playGame();
	}
}