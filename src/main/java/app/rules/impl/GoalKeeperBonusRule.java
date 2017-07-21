package app.rules.impl;

import app.model.Player;
import app.rules.PropertyBonus;
import app.rules.Rule;

public class GoalKeeperBonusRule extends PropertyBonus implements Rule {

	@Override
	public double apply(Player player) {
		return player.getRole() == 0 && player.getMatchReport().getGoalsCatch() == 0
				? getBonusValue("portiere_imbattuto") : 0;
	}

}
