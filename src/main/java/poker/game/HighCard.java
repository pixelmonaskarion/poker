package poker.game;

public class HighCard implements Rule {

	private int score;
	
	@Override
	public RuleScore checkRule(Card[] river, Hand hand) {
		int highestCard = -1;
		for (Card card : hand.getCards()) {
			if (card.getRank().ordinal() > highestCard) {
				highestCard = card.getRank().ordinal();
			}
		}
		return new RuleScore(score, highestCard);
	}
	
	public HighCard(int score) {
		this.score = score;
	}
}
