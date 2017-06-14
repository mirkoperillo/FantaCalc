package app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Queue;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import app.model.Player;
import app.model.PlayerReport;

public class TeamManager {

	private static final String pathBonusProp = System.getProperty("user.dir") + "/config/bonus.properties";

	private static Logger logger = Logger.getLogger(TeamManager.class);

	private MatchDataManager matchDataManager;

	/**
	 * La funzione prende situazione squadra di giornata effettua eventuali
	 * sostituzione con panchinari e calcola punteggio complessivo di giornata
	 * 
	 * @param team
	 *            squadra da valutare
	 * @return punteggio totale di giornata
	 */

	public float calculateScore(List<Player> team) {

		// array team contiene solo elementi titolari e panchina perche' e'
		// costruito cosi
		// fuori

		Queue<Short> substitutions = new LinkedList<>();
		int substitutionSlots = 3;
		float score = 0;

		List<Player> regularTeam = team.stream().filter(player -> player.getTitolare()).collect(Collectors.toList());
		List<Player> benchTeam = team.stream().filter(player -> !player.getTitolare()).collect(Collectors.toList());

		for (Player player : regularTeam) {
			if (player.isHasPlayed()) {
				score += player.getGlobalVote();
			} else if (substitutionSlots > 0) {
				substitutions.add(player.getRole());
				logger.info("Esce: " + player.getName());
				substitutionSlots--;
			}
		}

		Short roleToSubstitute = null;
		while ((roleToSubstitute = substitutions.poll()) != null) {
			final Short role = roleToSubstitute;
			Optional<Player> substitutePlayer = benchTeam.stream()
					.filter(player -> player.getRole() == role && player.isHasPlayed() && !player.getHasIncome())
					.findFirst();
			if (substitutePlayer.isPresent()) {
				substitutePlayer.get().setHasIncome(true);
				score += substitutePlayer.get().getGlobalVote();
				logger.info("Entra: " + substitutePlayer.get().getName());

			}
		}

		return score;
	}

	/**
	 * La funzione analizza file dei voti e in base a quelli caricati dal file
	 * della formazione carica report prestazione e calcola in base a bonus loro
	 * punteggio
	 * 
	 * @param team
	 *            squadra schierata
	 * @param pathFile
	 *            path file dei voti
	 * @return array di Player con voti e situazione di giornata
	 */

	public List<Player> completeTeam(List<Player> team, String pathFile) {

		// FIXME not correct location for this
		matchDataManager = new FileMatchDataManager(pathFile);

		Properties propBonus = new Properties();
		try {
			propBonus.load(new FileInputStream(pathBonusProp));
		} catch (FileNotFoundException e2) {
			logger.error(e2);
		} catch (IOException e2) {
			logger.error(e2);
		}

		// bonus-malus values
		Double bonusAmmonizione = new Double(propBonus.getProperty("ammonizione"));
		Double bonusEspulsione = new Double(propBonus.getProperty("espulsione"));
		Double bonusAutogoal = new Double(propBonus.getProperty("autogoal"));
		Double bonusGoalSubito = new Double(propBonus.getProperty("goal_subito"));
		Double bonusRigoreSbagliato = new Double(propBonus.getProperty("rigore_sbagliato"));
		Double bonusGoalSegnato = new Double(propBonus.getProperty("goal_segnato"));
		Double bonusAssist = new Double(propBonus.getProperty("assist"));
		Double bonusPortiereImbattuto = new Double(propBonus.getProperty("portiere_imbattuto"));
		Double bonusRigoreSegnato = new Double(propBonus.getProperty("rigore_segnato"));
		Double bonusRigoreParato = new Double(propBonus.getProperty("rigore_parato"));

		final int REGULAR_TIME_MEMBERS = 11;
		int index = 0;
		for (Player player : team) {
			player.setTitolare((index < REGULAR_TIME_MEMBERS));
			// FIXME getTabellino popola anche campo ruolo e squadra di player
			PlayerReport pr = matchDataManager.getTabellino(player);
			player.setMatchReport(pr);

			// portiere s.v voto 6 <-- non e' il posto corretto dove fare
			// questo, e' una regola di calcolo del punteggio
			if (player.getRole() == 0 && pr.hasPlayed() && pr.getVote() == 0) {
				pr.setVote(6.0);
			}

			// non e' il posto corretto dove fare
			// questo, e' una regola di calcolo del punteggio
			Double globalVote = pr.getVote() + (pr.getGoals()) * bonusGoalSegnato
					+ ((pr.isYellowCard()) ? bonusAmmonizione : 0) + ((pr.isRedCard()) ? bonusEspulsione : 0)
					+ pr.getGoalsCatch() * bonusGoalSubito + pr.getAssist() * bonusAssist
					+ pr.getAutogoals() * bonusAutogoal
					+ ((player.getRole() == 0 && pr.getGoalsCatch() == 0) ? bonusPortiereImbattuto : 0)
					+ pr.getRigoreParato() * bonusRigoreParato + pr.getRigoreSegnato() * bonusRigoreSegnato
					+ pr.getRigoreSbagliato() * bonusRigoreSbagliato;

			// giocatore espulso s.v = 4 <-- non e' il posto corretto dove fare
			// questo, e' una regola di calcolo del punteggio
			if (player.isHasPlayed() && player.getMatchReport().getVote() == 0 && player.getMatchReport().isRedCard()) {
				globalVote = 4.0;
			}

			player.setGlobalVote(globalVote);

			index++;
		}

		return team;
	}

}
