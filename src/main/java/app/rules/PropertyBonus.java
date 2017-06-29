package app.rules;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public abstract class PropertyBonus {

	private static final Logger logger = Logger.getLogger(PropertyBonus.class);

	private static final Properties props = new Properties();

	private static final String PATH_PROPS_FILE = System.getProperty("user.dir") + "/config/bonus.properties";

	private static final String PROPS_RESOURCE_NAME = "config/bonus.properties";

	public PropertyBonus() {
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPS_RESOURCE_NAME));
			logger.info("Load bonus properties from classpath");
		} catch (IOException e) {
			logger.warn(String.format("resource %s not present in classpath, searching on fs", PROPS_RESOURCE_NAME));
			try {
				props.load(new FileInputStream(PATH_PROPS_FILE));
				logger.info("Load bonus properties from file system");
			} catch (IOException e1) {
				logger.warn(String.format("bonus properties file %s not found", PATH_PROPS_FILE));
			}
		}
	}

	public double getBonusValue(String bonusName) {
		return Double.valueOf(props.getProperty(bonusName));
	}
}
