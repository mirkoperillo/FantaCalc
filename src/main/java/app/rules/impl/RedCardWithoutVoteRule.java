package app.rules.impl;

import app.model.Player;
import app.rules.PropertyBonus;
import app.rules.Rule;

public class RedCardWithoutVoteRule extends PropertyBonus implements Rule {

	private final double BONUS_VALUE = 4d;

	@Override
	public double apply(Player player) {
		return player.isHasPlayed() && player.getMatchReport().getVote() == 0 && player.getMatchReport().isRedCard()
				? BONUS_VALUE : 0;
	}

}
