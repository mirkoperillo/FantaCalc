package app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

import app.model.Player;
import app.model.PlayerReport;

public class TeamCompleterTest {

	/*
	 * TEST 1
	 */
	@Test
	public void calculate() {
		TeamCompleter completer = new TeamCompleter();
		Float score = completer.calculate(setupForTest1());
		Assert.assertEquals(11f, score, 0f);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void emptyTeam() {
		TeamCompleter completer = new TeamCompleter();
		completer.calculate(new ArrayList<>());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void teamSizeLessThen11() {
		TeamCompleter completer = new TeamCompleter();
		completer.calculate(teamSize1());
	}

	@Test
	public void entraDallaPanchina() {
		TeamCompleter completer = new TeamCompleter();
		Float score = completer.calculate(teamEntraDallaPanchina());
		Assert.assertEquals(12f, score, 0f);

	}

	private List<Player> teamEntraDallaPanchina() {
		List<Player> result = new ArrayList<>();

		IntStream.range(0, 10).forEach(i -> {
			Player p1 = new Player();
			p1.setGlobalVote(1);
			p1.setHasPlayed(true);
			result.add(p1);
		});

		Player p1 = new Player();
		p1.setGlobalVote(0); // nonostante non abbia giocato un eventuale
								// punteggio nel global vote viene aggiunto
		p1.setHasPlayed(false);
		p1.setRole((short) 1); // defender
		result.add(p1);

		// substitutes must to be 7
		Player panchina = new Player();
		PlayerReport playReport = new PlayerReport();
		playReport.setVote(2d);
		panchina.setMatchReport(playReport); // score deve essere popolato
		panchina.setGlobalVote(2d);
		panchina.setHasPlayed(true);
		panchina.setRole((short) 1);
		result.add(panchina);

		IntStream.range(0, 6).forEach(i -> {
			Player p = new Player();
			p.setGlobalVote(1);
			p.setHasPlayed(false);
			result.add(p);
		});

		return result;
	}

	private List<Player> teamSize1() {
		List<Player> result = new ArrayList<>();

		IntStream.range(0, 1).forEach(i -> {
			Player p1 = new Player();
			p1.setGlobalVote(1);
			p1.setHasPlayed(true);
			result.add(p1);
		});

		return result;
	}

	private List<Player> setupForTest1() {
		List<Player> result = new ArrayList<>();

		IntStream.range(0, 11).forEach(i -> {
			Player p1 = new Player();
			p1.setGlobalVote(1);
			p1.setHasPlayed(true);
			result.add(p1);
		});

		return result;
	}
}
