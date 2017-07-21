package app.rules.impl;

import app.model.Player;
import app.rules.PropertyBonus;
import app.rules.Rule;

public class PenaltyOutRule extends PropertyBonus implements Rule {

	@Override
	public double apply(Player player) {
		return player.getMatchReport().getRigoreSbagliato() * getBonusValue("rigore_sbagliato");
	}

}
