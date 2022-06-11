package poker.game;

import java.awt.Color;
import java.awt.Graphics;

public class Card {
	private Suit suit;
	private Rank rank;
	
	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	public int nextTo(Card card) {
		return ((rank.ordinal()-card.rank.ordinal())%Rank.values().length);
	}

	public Suit getSuit() {
		return suit;
	}

	public Rank getRank() {
		return rank;
	}
	
	@Override
	public String toString() {
		return "Card[" + rank.getRank() + " of " + suit.getName() + "]";
	}
	
	public static final int CARD_WIDTH = 48;
	public static final int CARD_HEIGHT = 80;
	public static final int CARD_MARGIN = 2;
	public static final float TEXT_SIZE = 20.0f;
	
	public void draw(Graphics g, int sx, int sy) {
		Color color = suit.getColor();
		g.setColor(color);
		g.fillRect(sx, sy, CARD_WIDTH, CARD_HEIGHT);
		g.setColor(Color.white);
		g.fillRect(sx+CARD_MARGIN, sy+CARD_MARGIN, CARD_WIDTH-CARD_MARGIN*2, CARD_HEIGHT-CARD_MARGIN*2);
		g.setColor(color.darker());
		g.setFont(g.getFont().deriveFont(TEXT_SIZE));
		g.drawString(rank.getRank(), sx+CARD_WIDTH/2-g.getFontMetrics().stringWidth(rank.getRank())/2, sy+CARD_HEIGHT/2-(int)TEXT_SIZE/2);
	}
}