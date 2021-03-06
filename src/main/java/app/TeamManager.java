package app;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import app.model.Player;
import app.model.PlayerReport;

public class TeamManager {

	private static Logger logger = Logger.getLogger(TeamManager.class);

	private MatchDataManager matchDataManager;

	private DefaultRuleManager ruleManager = new DefaultRuleManager();

	public TeamManager(MatchDataManager dataManager) {
		this.matchDataManager = dataManager;
	}

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
				score += player.getGlobalVote(ruleManager.getRuleset());
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
				score += substitutePlayer.get().getGlobalVote(ruleManager.getRuleset());
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
		// matchDataManager = new FileMatchDataManager(pathFile);

		final int REGULAR_TIME_MEMBERS = 11;
		int index = 0;
		for (Player player : team) {
			player.setTitolare((index < REGULAR_TIME_MEMBERS));
			// FIXME getTabellino popola anche campo ruolo e squadra di player
			PlayerReport pr = matchDataManager.getTabellino(player);
			player.setMatchReport(pr);

			index++;
		}

		return team;
	}

}
