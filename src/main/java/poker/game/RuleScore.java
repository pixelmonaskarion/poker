package poker.game;

public class RuleScore {
	private int ruleScore;
	private int score;
	
	public RuleScore(int ruleScore, int score) {
		this.ruleScore = ruleScore;
		this.score = score;
	}

	public int getRuleScore() {
		return ruleScore;
	}

	public int getScore() {
		return score;
	}
}
