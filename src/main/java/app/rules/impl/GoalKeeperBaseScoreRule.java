package app.rules.impl;

import app.model.Player;
import app.rules.PropertyBonus;
import app.rules.Rule;

public class GoalKeeperBaseScoreRule extends PropertyBonus implements Rule {

	private final double BONUS_VALUE = 6d;

	@Override
	public double apply(Player player) {
		return player.getRole() == 0 && player.isHasPlayed() && player.getMatchReport().getVote() == 0 ? BONUS_VALUE
				: 0;
	}

}
