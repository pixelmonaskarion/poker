package poker.game;

public class MatchRule implements Rule {

	private int matchAmount;
	private MatchType type;
	private int score;
	
	@Override
	public RuleScore checkRule(Card[] river, Hand hand) {
		int[] cardAmounts;
		if (type == MatchType.SUIT) {
			cardAmounts = new int[4];
		} else {
			cardAmounts = new int[13];
		}
		for (Card card : river) {
			if (type == MatchType.SUIT) {
				cardAmounts[card.getSuit().ordinal()]++;
			} else {
				cardAmounts[card.getRank().ordinal()]++;
			}
		}
		for (Card card : hand.getCards()) {
			if (type == MatchType.SUIT) {
				cardAmounts[card.getSuit().ordinal()]++;
			} else {
				cardAmounts[card.getRank().ordinal()]++;
			}
		}
		int highestRank = -1;
		for (int i = 0; i < cardAmounts.length; i++) {
			if (cardAmounts[i] == matchAmount) {
				highestRank = i;
			}
		}
		if (highestRank != -1) {
			return new RuleScore(score, highestRank);
		}
		return new RuleScore(0, 0);
	}

	public MatchRule(int matchAmount, MatchType type, int score) {
		this.matchAmount = matchAmount;
		this.type = type;
		this.score = score;
	}
	
	public enum MatchType {
		SUIT,
		RANK;
	}
}
