package poker;

public enum Suit {
	DIAMONDS("Diamonds"),
	CLUBS("Clubs"),
	HEARTS("Hearts"),
	SPADES("Spades");
	
	private String name;
	
	Suit(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
