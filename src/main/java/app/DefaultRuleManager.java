package app;

import app.rules.Ruleset;
import app.rules.impl.AssistRule;
import app.rules.impl.AutogoalRule;
import app.rules.impl.GoalCatchRule;
import app.rules.impl.GoalKeeperBaseScoreRule;
import app.rules.impl.GoalKeeperBonusRule;
import app.rules.impl.GoalsRule;
import app.rules.impl.PenaltyGoalRule;
import app.rules.impl.PenaltyOutRule;
import app.rules.impl.PenaltySavedRule;
import app.rules.impl.RedCardRule;
import app.rules.impl.RedCardWithoutVoteRule;
import app.rules.impl.YellowCardRule;

public class DefaultRuleManager {
	private Ruleset ruleset = new Ruleset();

	public DefaultRuleManager() {
		ruleset.addRule(new AssistRule());
		ruleset.addRule(new AutogoalRule());
		ruleset.addRule(new GoalCatchRule());
		ruleset.addRule(new GoalKeeperBaseScoreRule());
		ruleset.addRule(new GoalKeeperBonusRule());
		ruleset.addRule(new GoalsRule());
		ruleset.addRule(new PenaltyGoalRule());
		ruleset.addRule(new PenaltyOutRule());
		ruleset.addRule(new PenaltySavedRule());
		ruleset.addRule(new RedCardRule());
		ruleset.addRule(new RedCardWithoutVoteRule());
		ruleset.addRule(new YellowCardRule());
	}

	public Ruleset getRuleset() {
		return ruleset;
	}
}
