package poker;

public class Hand {
	private Card[] cards;
	
	public Hand() {
		cards = new Card[2];
	}
	
	public Card getCard(int i) {
		return cards[i];
	}
	
	public void setCard(int i, Card card) {
		cards[i] = card;
	}
	
	public String cardString() {
		String handString = "";
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] != null) {
				handString = handString + cards[i].toString() + ", ";
			} else {
				break;
			}
		}
		return handString.substring(0, handString.length()-2);
	}
}
