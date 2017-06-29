package app;

import org.junit.Assert;
import org.junit.Test;

import app.model.Player;
import app.model.PlayerReport;
import app.rules.Ruleset;
import app.rules.impl.GoalsRule;
import app.rules.impl.YellowCardRule;

public class PlayerTest {

	@Test
	public void yellowCardPlayer() {
		Player p = new Player();
		PlayerReport report = new PlayerReport();
		report.setYellowCard(true);
		report.setVote(6d);
		p.setRole((short) 1);
		p.setTitolare(true);
		p.setName("ANDREA RANOCCHIA");
		p.setMatchReport(report);

		Ruleset ruleset = new Ruleset();
		ruleset.addRule(new YellowCardRule());

		Assert.assertEquals(5.5, p.getGlobalVote(ruleset), 0);
	}

	@Test
	public void applyTwoRules() {
		Player p = new Player();
		PlayerReport report = new PlayerReport();
		report.setYellowCard(true);
		report.setGoals(3);
		report.setVote(6d);
		p.setRole((short) 1);
		p.setTitolare(true);
		p.setName("ANDREA RANOCCHIA");
		p.setMatchReport(report);

		Ruleset ruleset = new Ruleset();
		ruleset.addRule(new YellowCardRule());
		ruleset.addRule(new GoalsRule());

		Assert.assertEquals(14.5, p.getGlobalVote(ruleset), 0);
	}
}
