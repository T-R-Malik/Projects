import java.util.Random;

public class Deck{
	private Card cards[];
	private Rank ranks[];
	private Suit suits[];
	private int counter = 0;

	Deck(){
		cards = new Card[52];

		Rank ranks[] = Rank.values();
		Suit suits[] = Suit.values();

		for(int i = 0; i < suits.length; i++){
			for(int j = 0; j < ranks.length; j++){
				cards[counter++] = new Card(ranks[j], suits[i]);
			}
		}
	}

	public void displayDeck(){
		for(int i = 0; i < cards.length; i++){
			System.out.println(cards[i]);
		}
	}

	public void shuffle(){
		Random rand = new Random();
		Card temp;
		for(int i = 0; i < 2000; i++){
			int ran = rand.nextInt(52);
			temp = cards[0];
			cards[0] = cards[ran];
			cards[ran] = temp;
		}
	}

	public Card dealCard(){
		if (cards.length == 0) {return null;}
		Card newDeck[] = new Card[cards.length - 1];
		Card topCard = cards[0];
		for(int i = 0; i < newDeck.length; i++){
			newDeck[i] = cards[i + 1];
		}
		cards = newDeck;
		return topCard;
	}	
}