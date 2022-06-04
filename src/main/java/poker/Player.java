package poker;

public class Player {
	public static final int STARTING_MONEY = 1000;
	
	public boolean active = true;
	public Hand hand;
	public int money = STARTING_MONEY;
	
	public Player() {
		hand = new Hand();
	}
}