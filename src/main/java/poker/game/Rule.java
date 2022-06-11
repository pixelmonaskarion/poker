package poker.game;

public interface Rule {
	public RuleScore checkRule(Card[] river, Hand hand);
}
