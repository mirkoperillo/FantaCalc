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
public class PlayerReport {
	private boolean yellowCard;
	private boolean redCard;
	private int goals;
	private int goalsCatch;
	private int autogoals;
	private int assist;
	private double vote;
	private int rigoreParato;
	private int rigoreSbagliato;
	private int rigoreSegnato;

	public Integer getRigoreParato() {
		return rigoreParato;
	}

	public void setRigoreParato(Integer rigoreParato) {
		this.rigoreParato = rigoreParato;
	}

	public Integer getRigoreSbagliato() {
		return rigoreSbagliato;
	}

	public void setRigoreSbagliato(Integer rigoreSbagliato) {
		this.rigoreSbagliato = rigoreSbagliato;
	}

	public Integer getRigoreSegnato() {
		return rigoreSegnato;
	}

	public void setRigoreSegnato(Integer rigoreSegnato) {
		this.rigoreSegnato = rigoreSegnato;
	}

	public PlayerReport() {
	}

	public Integer getAutogoals() {
		return autogoals;
	}

	public void setAutogoals(Integer autogoals) {
		this.autogoals = autogoals;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public Integer getGoalsCatch() {
		return goalsCatch;
	}

	public void setGoalsCatch(Integer goalsCatch) {
		this.goalsCatch = goalsCatch;
	}

	public Boolean isRedCard() {
		return redCard;
	}

	public void setRedCard(Boolean redCard) {
		this.redCard = redCard;
	}

	public Boolean isYellowCard() {
		return yellowCard;
	}

	public void setYellowCard(Boolean yellowCard) {
		this.yellowCard = yellowCard;
	}

	public Integer getAssist() {
		return assist;
	}

	public void setAssist(Integer assist) {
		this.assist = assist;
	}

	public Double getVote() {
		return vote;
	}

	public void setVote(Double vote) {
		this.vote = vote;
	}

	public String toString() {
		return getVote() + " ; g: " + getGoals() + " ; ag: " + getAutogoals()
				+ " ; gs: " + getGoalsCatch() + "; ass: " + getAssist()
				+ " ; amm: " + ((isYellowCard()) ? "Y" : "N") + " ; esp: "
				+ ((isRedCard()) ? "Y" : "N") + "; rp: " + getRigoreParato()
				+ "; rse: " + getRigoreSegnato() + "; rsb: "
				+ getRigoreSbagliato();
	}

}
