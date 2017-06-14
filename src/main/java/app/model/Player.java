package app.model;

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