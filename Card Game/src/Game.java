public class Game{
	private Player players[];
	private Deck deck;
	
	public Game(String playerNames[]){
		deck = new Deck();
		deck.shuffle();
		players = new Player[playerNames.length];
		for(int i = 0; i < playerNames.length; i++){
			players[i] = new Player(playerNames[i], 52 / playerNames.length);
		}
	}




	public void dealCards(){
		int playerIndex = 0;
		Card card;
		while((card = deck.dealCard()) != null){
			players[playerIndex].receiveCard(card);
			playerIndex = (playerIndex + 1) % players.length;
		}
	}





	public void round(){
		Card thrownCards[] = new Card[players.length];
		for(int i = 0; i < players.length; i++){
			Card card = players[i].throwCard();
			thrownCards[i] = card;
			if (card != null) {
				System.out.println(players[i].getName() + " throws " + card);
			}
			else {
				System.out.println(players[i].getName() + " has no cards left!");
			}
		}

		int highestCardValue = thrownCards[0].getRank().getValue();

    		for (int i = 0; i < players.length; i++) {
        		if (thrownCards[i].getRank().getValue() > highestCardValue) {
            			highestCardValue = thrownCards[i].getRank().getValue();
        		}
    		}

		String winners[] = new String[players.length];
		int winnerCount = 0;
		for (int i = 0; i < players.length; i++) {
			if (thrownCards[i].getRank().getValue() == highestCardValue) {
				players[i].addPoints();
				winners[winnerCount++] = players[i].getName();
			}
		}

		if (winnerCount == 1) {System.out.println("Winner: " + winners[0]);}
		else {
			System.out.print("It's a tie between: ");
			for (int i = 0; i < winnerCount; i++) {
				System.out.print(winners[i] + " ");
			}
		System.out.println();
		}
	}




	public void getWinner(){
		int highestPointPlayer = players[0].getPoints();
		for(int i = 1; i < players.length; i++){
			if(players[i].getPoints() > highestPointPlayer){
				highestPointPlayer = players[i].getPoints();
			}
		}
    
		System.out.println("\n--- Final Scores ---");
    		for (int i = 0; i < players.length; i++) {
			System.out.println(players[i].getName() + ": " + players[i].getPoints() + " points");
		}

		System.out.print("=========== Game Winner(s): ");
		boolean first = true;
		for (int i = 0; i < players.length; i++) {
			if (players[i].getPoints() == highestPointPlayer) {
				if (!first) {System.out.print(", ");}
				System.out.print(players[i].getName());
				first = false;
			}
		}

		System.out.println(" with " + highestPointPlayer + " points ===========");
	}





	public void playGame(){
		int rounds = players[0].getHandSize();
		for(int i = 0; i < rounds; i++){
			System.out.println("");
			System.out.println("============Round " + (i + 1) + "============");
			round();
		}
		getWinner();
	}




	public void showAllHands(){
		for(int i = 0; i < players.length; i++){
			System.out.println("Player "+ (i + 1));
			System.out.println("");
			players[i].showHand();
			System.out.println("");
		}
	}





	public void showPlayers(){
		for(int i = 0; i < players.length; i++){
			System.out.println(players[i].getName());
		}
	}
}