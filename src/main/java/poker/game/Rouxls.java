package poker.game;

import java.util.ArrayList;

import poker.game.MatchRule.MatchType;

public class Rouxls {
	public static ArrayList<Rule> rules;
	public static void init() {
		rules = new ArrayList<>();
		//one pair
		rules.add(new MatchRule(2, MatchType.RANK, 2));
		//three of a kind
		rules.add(new MatchRule(3, MatchType.RANK, 4));
		//flush
		rules.add(new MatchRule(5, MatchType.SUIT, 6));
		//four of a kind
		rules.add(new MatchRule(4, MatchType.RANK, 8));
		//high card
		rules.add(new HighCard(0));
	}
	
	public static RuleScore check(Card[] river, Hand hand) {
		RuleScore highestScore = new RuleScore(0, 0);
		for (Rule rule : rules) {
			RuleScore score = rule.checkRule(river, hand);
			if (score.getRuleScore() > highestScore.getRuleScore()) {
				highestScore = score;
			} else if (score.getRuleScore() == highestScore.getRuleScore()) {
				if (score.getScore() > highestScore.getScore()) {
					highestScore = score;
				} else {
					//needs secondary scoring
				}
			}
		}
		return highestScore;
	}
}
