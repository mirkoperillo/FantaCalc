package app.rules.impl;

import app.model.Player;
import app.rules.PropertyBonus;
import app.rules.Rule;

public class GoalsRule extends PropertyBonus implements Rule {

	@Override
	public double apply(Player player) {
		return player.getMatchReport().getGoals() * getBonusValue("goal_segnato");
	}

}
