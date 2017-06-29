package app.rules.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import app.model.Player;
import app.rules.Rule;

public class YellowCardRule implements Rule {

	private static final String pathBonusProp = System.getProperty("user.dir") + "/config/bonus.properties";

	private static final Properties propBonus = new Properties();

	private static final Logger logger = Logger.getLogger(YellowCardRule.class);

	static {
		try {
			propBonus.load(new FileInputStream(pathBonusProp));
		} catch (FileNotFoundException e2) {
			logger.error(e2);
		} catch (IOException e2) {
			logger.error(e2);
		}
	}

	@Override
	public double apply(Player player) {
		Double bonusAmmonizione = new Double(propBonus.getProperty("ammonizione"));
		return player.getMatchReport().isYellowCard() ? -bonusAmmonizione : 0;
	}

}
