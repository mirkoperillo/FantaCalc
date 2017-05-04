package app;

/**
 * Copyright (C) 2008 Mirko Perillo

 This file is part of FantaCalc.

 FantaCalc is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 FantaCalc is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with FantaCalc.  If not, see <http://www.gnu.org/licenses/>.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import app.model.Player;
import app.model.PlayerReport;

public class TeamCompleter {

	private static final String pathPositionProp = System
			.getProperty("user.dir") + "/config/position.properties";
	private static final String pathBonusProp = System.getProperty("user.dir")
			+ "/config/bonus.properties";

	private static Logger logger = Logger.getLogger(TeamCompleter.class);

	/**
	 * La funzione prende situazione squadra di giornata effettua eventuali
	 * sostituzione con panchinari e calcola punteggio complessivo di giornata
	 * 
	 * @param team
	 *            squadra da valutare
	 * @return punteggio totale di giornata
	 */

	public Float calculate(List<Player> team) {
		Player p = null;
		boolean panchina = false;
		List<Short> sostituzioni = new ArrayList<Short>();
		int j = 0;
		float points = 0;
		for (int i = 0; i < 11; i++) {
			p = (Player) team.get(i);
			System.out.println(p.getGlobalVote() + " -- " + i);
			points += p.getGlobalVote();
			if (!p.isHasPlayed() && j < 3) {
				panchina = true;
				sostituzioni.add(p.getRole());
				logger.info("Esce: " + p.getName());
				j++;
			}
		}

		j = 0;
		int dimensionePanchina = 7;
		for (int i = 11; panchina && i < (11 + dimensionePanchina); i++) {
			p = (Player) team.get(i);
			if (p.isHasPlayed() && p.getMatchReport().getVote() > 0 && j < 3
					&& sostituzioni.contains(p.getRole())) {
				logger.info("Entra: " + p.getName());
				p.setHasIncome(true);
				points += p.getGlobalVote();
				j++;
				sostituzioni.remove(new Short(p.getRole()));
			}

		}

		return new Float(points);
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
		BufferedReader reader = null;
		Properties propPosition = new Properties();
		Properties propBonus = new Properties();
		try {
			propPosition.load(new FileInputStream(pathPositionProp));
			propBonus.load(new FileInputStream(pathBonusProp));
		} catch (FileNotFoundException e2) {
			logger.error(e2);
		} catch (IOException e2) {
			logger.error(e2);
		}

		// bonus-malus values
		Double bonusAmmonizione = new Double(
				propBonus.getProperty("ammonizione"));
		Double bonusEspulsione = new Double(propBonus.getProperty("espulsione"));
		Double bonusAutogoal = new Double(propBonus.getProperty("autogoal"));
		Double bonusGoalSubito = new Double(
				propBonus.getProperty("goal_subito"));
		Double bonusRigoreSbagliato = new Double(
				propBonus.getProperty("rigore_sbagliato"));
		Double bonusGoalSegnato = new Double(
				propBonus.getProperty("goal_segnato"));
		Double bonusAssist = new Double(propBonus.getProperty("assist"));
		Double bonusPortiereImbattuto = new Double(
				propBonus.getProperty("portiere_imbattuto"));
		Double bonusRigoreSegnato = new Double(
				propBonus.getProperty("rigore_segnato"));
		Double bonusRigoreParato = new Double(
				propBonus.getProperty("rigore_parato"));

		// position of valutation
		Integer namePosition = Integer
				.valueOf(propPosition.getProperty("nome"));
		Integer ruoloPosition = Integer.valueOf(propPosition
				.getProperty("ruolo"));
		Integer squadraPosition = Integer.valueOf(propPosition
				.getProperty("squadra"));
		Integer votePurePosition = Integer.valueOf(propPosition
				.getProperty("votoPuro"));
		Integer yellowPosition = Integer.valueOf(propPosition
				.getProperty("ammonizione"));
		Integer hasPlayedPosition = Integer.valueOf(propPosition
				.getProperty("haGiocato"));
		Integer redPosition = Integer.valueOf(propPosition
				.getProperty("espulsione"));
		Integer goalPosition = Integer.valueOf(propPosition
				.getProperty("goalSegnati"));
		Integer assistPosition = Integer.valueOf(propPosition
				.getProperty("assist"));
		Integer goalPresiPosition = Integer.valueOf(propPosition
				.getProperty("goalSubiti"));
		Integer autogoalPosition = Integer.valueOf(propPosition
				.getProperty("autogoal"));
		Integer rigorePosition = Integer.valueOf(propPosition
				.getProperty("rigore"));
		Integer rigoreSbagliatoPosition = Integer.valueOf(propPosition
				.getProperty("rigoreSbagliato"));

		Integer rigoreSubitoPosition = Integer.valueOf(propPosition
				.getProperty("rigoreSubito"));
		Integer rigoreParatoPosition = Integer.valueOf(propPosition
				.getProperty("rigoreParato"));

		String separatore = propPosition.getProperty("separator");

		try {
			reader = new BufferedReader(new FileReader(new File(pathFile)));
		} catch (FileNotFoundException e1) {
			logger.error(e1);
		}
		Player[] players = (Player[]) team.toArray(new Player[0]);

		for (int i = 0; i < team.size(); i++) {

			String toSearch = players[i].getName();
			String buffer = null;
			try {
				reader = new BufferedReader(new FileReader(new File(pathFile)));
				boolean load = false;
				while ((buffer = reader.readLine()) != null) {
					String[] parts = buffer.split(separatore);
					String name = parts[namePosition - 1];
					// String name1=new StringTransformer().onlyChars(name);
					String name1 = name;
					if (name1.toLowerCase().startsWith(
							"\"" + toSearch.toLowerCase() + "\"")) {

						String squadra = parts[squadraPosition - 1];
						Short ruolo = Short.valueOf(parts[ruoloPosition - 1]);

						Boolean hasPlayed = String.valueOf(
								parts[hasPlayedPosition - 1]).equals("1");
						Boolean yellowCard = (Integer.valueOf(
								parts[yellowPosition - 1]).shortValue() == 1);
						Boolean redCard = (Integer.valueOf(
								parts[redPosition - 1]).shortValue() == 1);

						Double pureVote = Double
								.valueOf(parts[votePurePosition - 1]);

						// number of vote variations
						Integer numeroGoal = Integer
								.valueOf(parts[goalPosition - 1]);
						Integer numeroGoalSubiti = Integer
								.valueOf(parts[goalPresiPosition - 1]);
						Integer numeroAutogoal = Integer
								.valueOf(parts[autogoalPosition - 1]);
						Integer numeroAssist = Integer
								.valueOf(parts[assistPosition - 1]);
						Integer numeroRigoriParati = Integer
								.valueOf(parts[rigoreParatoPosition - 1]);
						Integer numeroRigoriSegnati = Integer
								.valueOf(parts[rigorePosition - 1]);
						Integer numeroRigoriSbagliati = Integer
								.valueOf(parts[rigoreSbagliatoPosition - 1]);

						logger.debug(name + "(" + squadra + ")" + "loaded");

						players[i].setName(name);
						players[i].setRole(ruolo);
						players[i].setSquadra(squadra);

						players[i].setHasPlayed(hasPlayed);
						players[i].setTitolare((i < 11));

						PlayerReport pr = new PlayerReport();
						pr.setVote(pureVote);
						// portiere s.v voto 6
						if (players[i].getRole() == 0 && hasPlayed
								&& pureVote == 0) {
							pr.setVote(6.0);
						}
						pr.setAssist(numeroAssist);
						pr.setAutogoals(numeroAutogoal);
						pr.setGoals(numeroGoal
								- (numeroRigoriSegnati - numeroRigoriSbagliati));
						pr.setGoalsCatch(numeroGoalSubiti);
						pr.setRedCard(redCard);
						pr.setYellowCard(yellowCard);
						pr.setRigoreParato(numeroRigoriParati);
						pr.setRigoreSbagliato(numeroRigoriSbagliati);
						pr.setRigoreSegnato(numeroRigoriSegnati
								- numeroRigoriSbagliati);
						players[i].setMatchReport(pr);

						// Double globalVote = pureVote + (numeroGoal) *
						// bonusGoalSegnato + ((yellowCard) ? bonusAmmonizione :
						// 0) + ((redCard) ? bonusEspulsione : 0) +
						// numeroGoalSubiti * bonusGoalSubito + numeroAssist *
						// bonusAssist + numeroAutogoal * bonusAutogoal +
						// ((ruolo == 0 && numeroGoalSubiti == 0 ) ?
						// bonusPortiereImbattuto : 0) + numeroRigoriParati *
						// bonusRigoreParato + (numeroRigoriSegnati -
						// numeroRigoriSbagliati) * bonusRigoreSegnato +
						// numeroRigoriSbagliati * bonusRigoreSbagliato;
						Double globalVote = pr.getVote()
								+ (pr.getGoals())
								* bonusGoalSegnato
								+ ((pr.isYellowCard()) ? bonusAmmonizione : 0)
								+ ((pr.isRedCard()) ? bonusEspulsione : 0)
								+ pr.getGoalsCatch()
								* bonusGoalSubito
								+ pr.getAssist()
								* bonusAssist
								+ pr.getAutogoals()
								* bonusAutogoal
								+ ((ruolo == 0 && pr.getGoalsCatch() == 0) ? bonusPortiereImbattuto
										: 0) + pr.getRigoreParato()
								* bonusRigoreParato + pr.getRigoreSegnato()
								* bonusRigoreSegnato + pr.getRigoreSbagliato()
								* bonusRigoreSbagliato;

						// giocatore espulso s.v = 4
						if (players[i].isHasPlayed()
								&& players[i].getMatchReport().getVote() == 0
								&& players[i].getMatchReport().isRedCard()) {
							globalVote = 4.0;
						}

						players[i].setGlobalVote(globalVote);
						load = true;
						break;
					}
				}
				if (!load) {
					logger.debug(players[i].getName() + " not loaded");
				}
			} catch (IOException e1) {
				logger.error(e1);
			}

		}

		List<Player> ret = new ArrayList<Player>();
		for (int i = 0; i < players.length; i++)
			ret.add(players[i]);
		return ret;
	}

}
