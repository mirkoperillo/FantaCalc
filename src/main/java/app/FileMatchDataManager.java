package app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.log4j.Logger;

import app.model.Player;
import app.model.PlayerReport;

public class FileMatchDataManager implements MatchDataManager {
	private Path matchDataPath;

	private static final String pathPositionProp = System.getProperty("user.dir") + "/config/position.properties";

	private static final Logger logger = Logger.getLogger(FileMatchDataManager.class);

	Properties propPosition = new Properties();

	// position of valutation
	private Integer namePosition;
	private Integer ruoloPosition;
	private Integer squadraPosition;
	private Integer votePurePosition;
	private Integer yellowPosition;
	private Integer hasPlayedPosition;
	private Integer redPosition;
	private Integer goalPosition;
	private Integer assistPosition;
	private Integer goalPresiPosition;
	private Integer autogoalPosition;
	private Integer rigorePosition;
	private Integer rigoreSbagliatoPosition;

	private Integer rigoreSubitoPosition;
	private Integer rigoreParatoPosition;

	private String separatore;

	private void init() {
		try {
			propPosition.load(new FileInputStream(pathPositionProp));

			namePosition = Integer.valueOf(propPosition.getProperty("nome"));
			ruoloPosition = Integer.valueOf(propPosition.getProperty("ruolo"));
			squadraPosition = Integer.valueOf(propPosition.getProperty("squadra"));
			votePurePosition = Integer.valueOf(propPosition.getProperty("votoPuro"));
			yellowPosition = Integer.valueOf(propPosition.getProperty("ammonizione"));
			hasPlayedPosition = Integer.valueOf(propPosition.getProperty("haGiocato"));
			redPosition = Integer.valueOf(propPosition.getProperty("espulsione"));
			goalPosition = Integer.valueOf(propPosition.getProperty("goalSegnati"));
			assistPosition = Integer.valueOf(propPosition.getProperty("assist"));
			goalPresiPosition = Integer.valueOf(propPosition.getProperty("goalSubiti"));
			autogoalPosition = Integer.valueOf(propPosition.getProperty("autogoal"));
			rigorePosition = Integer.valueOf(propPosition.getProperty("rigore"));
			rigoreSbagliatoPosition = Integer.valueOf(propPosition.getProperty("rigoreSbagliato"));

			rigoreSubitoPosition = Integer.valueOf(propPosition.getProperty("rigoreSubito"));
			rigoreParatoPosition = Integer.valueOf(propPosition.getProperty("rigoreParato"));

			separatore = propPosition.getProperty("separator");
		} catch (FileNotFoundException e2) {
			logger.error(e2);
		} catch (IOException e2) {
			logger.error(e2);
		}
	}

	public FileMatchDataManager(String matchDataPath) {
		this.matchDataPath = Paths.get(matchDataPath);
		init();
	}

	@Override
	public PlayerReport getTabellino(Player player) {
		PlayerReport report = new PlayerReport();
		BufferedReader dataReader = null;
		try {
			dataReader = new BufferedReader(new FileReader(matchDataPath.toFile()));
			String playerData = null;
			boolean matchDataLoaded = false;
			while ((playerData = dataReader.readLine()) != null) {
				String[] parts = playerData.split(separatore);
				String name = parts[namePosition - 1];
				String name1 = name;
				if (name1.toLowerCase().startsWith("\"" + player.getName().toLowerCase() + "\"")) {
					matchDataLoaded = true;
					String squadra = parts[squadraPosition - 1];
					Short ruolo = Short.valueOf(parts[ruoloPosition - 1]);

					// FIXME questi campi vanno popolati se in altro modo
					player.setSquadra(squadra);
					player.setRole(ruolo);

					Boolean hasPlayed = String.valueOf(parts[hasPlayedPosition - 1]).equals("1");
					Boolean yellowCard = (Integer.valueOf(parts[yellowPosition - 1]).shortValue() == 1);
					Boolean redCard = (Integer.valueOf(parts[redPosition - 1]).shortValue() == 1);

					Double pureVote = Double.valueOf(parts[votePurePosition - 1]);

					// number of vote variations
					Integer numeroGoal = Integer.valueOf(parts[goalPosition - 1]);
					Integer numeroGoalSubiti = Integer.valueOf(parts[goalPresiPosition - 1]);
					Integer numeroAutogoal = Integer.valueOf(parts[autogoalPosition - 1]);
					Integer numeroAssist = Integer.valueOf(parts[assistPosition - 1]);
					Integer numeroRigoriParati = Integer.valueOf(parts[rigoreParatoPosition - 1]);
					Integer numeroRigoriSegnati = Integer.valueOf(parts[rigorePosition - 1]);
					Integer numeroRigoriSbagliati = Integer.valueOf(parts[rigoreSbagliatoPosition - 1]);

					logger.debug(name + "(" + squadra + ")" + "loaded");

					report.setHasPlayed(hasPlayed);
					report.setVote(pureVote);
					report.setAssist(numeroAssist);
					report.setAutogoals(numeroAutogoal);
					report.setGoals(numeroGoal - (numeroRigoriSegnati - numeroRigoriSbagliati));
					report.setGoalsCatch(numeroGoalSubiti);
					report.setRedCard(redCard);
					report.setYellowCard(yellowCard);
					report.setRigoreParato(numeroRigoriParati);
					report.setRigoreSbagliato(numeroRigoriSbagliati);
					report.setRigoreSegnato(numeroRigoriSegnati - numeroRigoriSbagliati);

					break;
				}
			}

			if (!matchDataLoaded) {
				logger.debug(player.getName() + " not found");
			}
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException " + matchDataPath.toString(), e);
		} catch (IOException e) {
			logger.error("IOException " + matchDataPath.toString(), e);
		} finally {
			try {
				dataReader.close();
			} catch (IOException e) {
				logger.error("Impossibile to close match data file: " + matchDataPath.toString(), e);
			}
		}
		return report;
	}

}
