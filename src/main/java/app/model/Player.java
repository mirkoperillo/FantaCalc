package app.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Copyright (C) 2008 Mirko Perillo
 * 
 * This file is part of FantaCalc.
 * 
 * FantaCalc is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * FantaCalc is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * FantaCalc. If not, see <http://www.gnu.org/licenses/>.
 */
public class Player {

	private static final String pathBonusProp = System.getProperty("user.dir") + "/config/bonus.properties";

	private static final Logger logger = Logger.getLogger(Player.class);

	private static final Properties propBonus = new Properties();

	static {
		try {
			propBonus.load(new FileInputStream(pathBonusProp));
		} catch (FileNotFoundException e2) {
			logger.error(e2);
		} catch (IOException e2) {
			logger.error(e2);
		}
	}

	private short idCode;
	private String name;
	private short role;
	private String squadra;
	private boolean titolare;
	private boolean hasIncome;
	private double globalVote;
	private PlayerReport matchReport;

	public Player() {
		super();
		hasIncome = false;
	}

	public double getGlobalVote() {
		if (role == 0 && isHasPlayed() && matchReport.getVote() == 0) {
			matchReport.setVote(6.0);
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

		Double globalVote = matchReport.getVote() + (matchReport.getGoals()) * bonusGoalSegnato
				+ ((matchReport.isYellowCard()) ? bonusAmmonizione : 0)
				+ ((matchReport.isRedCard()) ? bonusEspulsione : 0) + matchReport.getGoalsCatch() * bonusGoalSubito
				+ matchReport.getAssist() * bonusAssist + matchReport.getAutogoals() * bonusAutogoal
				+ ((role == 0 && matchReport.getGoalsCatch() == 0) ? bonusPortiereImbattuto : 0)
				+ matchReport.getRigoreParato() * bonusRigoreParato
				+ matchReport.getRigoreSegnato() * bonusRigoreSegnato
				+ matchReport.getRigoreSbagliato() * bonusRigoreSbagliato;

		// giocatore espulso s.v = 4
		if (isHasPlayed() && matchReport.getVote() == 0 && matchReport.isRedCard()) {
			globalVote = 4.0;
		}

		return globalVote;
	}

	public void setGlobalVote(double globalVote) {
		this.globalVote = globalVote;
	}

	public boolean isHasPlayed() {
		return matchReport != null ? matchReport.hasPlayed() : false;
	}

	public PlayerReport getMatchReport() {
		return matchReport;
	}

	public void setMatchReport(PlayerReport matchReport) {
		this.matchReport = matchReport;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getIdCode() {
		return idCode;
	}

	public short getRole() {
		return role;
	}

	public void setRole(short role) {
		this.role = role;
	}

	public void setIdCode(short idCode) {
		this.idCode = idCode;
	}

	public String toString() {
		String nl = System.getProperty("line.separator");
		String ret = "";
		if (!isHasPlayed() && titolare) {
			ret = "ESCE: ";
		} else if (hasIncome) {
			ret = "ENTRA: ";
		}
		// if (isHasPlayed()) {
		ret += name + " (" + squadra + ") ->" + globalVote + nl;
		ret += "\t voto: " + matchReport.toString() + nl + nl;
		// }
		return ret;

	}

	public boolean getHasIncome() {
		return hasIncome;
	}

	public void setHasIncome(boolean hasIncome) {
		this.hasIncome = hasIncome;
	}

	public boolean getTitolare() {
		return titolare;
	}

	public void setTitolare(boolean titolare) {
		this.titolare = titolare;
	}

	public String getSquadra() {
		return squadra;
	}

	public void setSquadra(String squadra) {
		this.squadra = squadra;
	}

}

enum Schieramento {
	TITOLARE, PANCHINA, TRIBUNA
}