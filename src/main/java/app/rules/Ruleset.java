package app.rules;

import java.util.ArrayList;
import java.util.List;

import app.model.Player;

public class Ruleset {
	private List<Rule> rules = new ArrayList<>();

	public void addRule(Rule rule) {
		rules.add(rule);
	}

	public double apply(Player p) {
		return rules.stream().map(rule -> rule.apply(p)).reduce(Double::sum).orElse(0d);
	}
}
