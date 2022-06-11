package poker.game;

import java.util.Collections;
import java.util.Stack;

public class Deck {
	private Stack<Card> cards;
	
	public Deck() {
		cards = new Stack<>();
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				cards.push(new Card(suit, rank));
			}
		}
		shuffle();
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	public Card pop() {
		return cards.pop();
	}
	
	public void push(Card card) {
		cards.push(card);
	}
}
