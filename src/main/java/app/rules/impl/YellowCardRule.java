package app.rules.impl;

import app.model.Player;
import app.rules.Rule;

public class YellowCardRule implements Rule {

	@Override
	public double apply(Player player) {
		return player.getMatchReport().isYellowCard() ? -0.5 : 0;
	}

}
