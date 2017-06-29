package app.rules.impl;

import app.model.Player;
import app.rules.Rule;

public class GoalsRule implements Rule {

	@Override
	public double apply(Player player) {
		return player.getMatchReport().getGoals() * 3;
	}

}
