package poker.game;

import java.awt.Color;

public enum Suit {
	DIAMONDS("Diamonds", Color.yellow),
	CLUBS("Clubs", Color.green),
	HEARTS("Hearts", Color.red),
	SPADES("Spades", Color.blue);
	
	private String name;
	private Color color;
	
	Suit(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Color getColor() {
		return this.color;
	}
}
