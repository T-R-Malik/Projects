import java.util.Random;

public class Player{
	private String name;
	private Card hand[];
	private int totalCards;
	static int playerCounter = 0;
	private int cardIndex;
	private int points;

	Player(String name, int totalCards){
		playerCounter++;
		this.name = name;
		this.totalCards = totalCards;
		this.hand = new Card[totalCards];
		this.cardIndex = 0;
	}

	public void showHand(){
		for(int i = 0; i < hand.length; i++){
			System.out.println(hand[i]);
		}
	}

	public void receiveCard(Card card){
		if(cardIndex < hand.length){
			hand[cardIndex++] = card;
		}
	}

	public Card throwCard() {
		boolean hasCard = false;
		for (Card c : hand) {
			if (c != null) {
				hasCard = true;
				break;
			}
		}
		if (!hasCard) {return null;}
    
    		Random rand = new Random();
    		int index = rand.nextInt(hand.length);
    		while (hand[index] == null) {
        		index = rand.nextInt(hand.length);
    		}
    		Card thrown = hand[index];
    		hand[index] = null;
    		return thrown;
	}

	public void addPoints(){
		points += 10;
	}

	public String getName(){return name;}
	public static int getPlayerCount(){return playerCounter;}
	public int getTotalCards(){return totalCards;}
	public int getHandSize() {return hand.length;}
	public int getPoints(){return points;}
}