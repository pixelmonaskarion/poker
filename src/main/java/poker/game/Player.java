package poker.game;

import java.awt.Color;

public class Player {
	public static final int STARTING_MONEY = 1000;
	
	public boolean active = true;
	public Hand hand;
	public int money = STARTING_MONEY;
	public Color color;
	public String name;
	public int majorScore;
	public int minorScore;
	
	public Player() {
		hand = new Hand();
	}
}