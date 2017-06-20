package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

import app.model.Player;
import app.model.PlayerReport;

public class TeamManagerTest {

	/*
	 * TEST 1
	 */
	@Test
	public void calculate() {
		TeamManager completer = new TeamManager(new DummieMatchManager());
		Float score = completer.calculateScore(setupForTest1());
		Assert.assertEquals(11f, score, 0f);
	}

	@Test
	public void emptyTeam() {
		TeamManager completer = new TeamManager(new DummieMatchManager());
		Assert.assertEquals(0f, completer.calculateScore(new ArrayList<>()), 0);
	}

	@Test
	public void teamSizeLessThen11() {
		TeamManager completer = new TeamManager(new DummieMatchManager());
		float score = completer.calculateScore(teamSize1());
		Assert.assertEquals(1f, score, 0f);
	}

	@Test
	public void entraDallaPanchina() {
		TeamManager completer = new TeamManager(new DummieMatchManager());
		Float score = completer.calculateScore(teamEntraDallaPanchina());
		Assert.assertEquals(12f, score, 0f);

	}

	@Test
	public void playerGlobalVote() {
		TeamManager manager = new TeamManager(new TestMatchDataManager());
		Player player = new Player();
		player.setRole((short) 1);
		List<Player> result = manager.completeTeam(Arrays.asList(player), null);
		Assert.assertEquals(12.5f, result.get(0).getGlobalVote(), 0);
	}

	private List<Player> teamEntraDallaPanchina() {
		List<Player> result = new ArrayList<>();

		IntStream.range(0, 10).forEach(i -> {
			Player p1 = new Player();
			p1.setRole((short) 1);
			PlayerReport matchReport = new PlayerReport();
			matchReport.setHasPlayed(true);
			matchReport.setVote(1d);
			p1.setMatchReport(matchReport);
			p1.setTitolare(true);
			result.add(p1);
		});

		Player p1 = new Player();
		p1.setRole((short) 1);
		PlayerReport report = new PlayerReport();
		report.setVote(0d);
		p1.setMatchReport(report);
		p1.setTitolare(true);
		p1.setRole((short) 1); // defender
		result.add(p1);

		// substitutes must to be 7
		Player panchina = new Player();
		PlayerReport playReport = new PlayerReport();
		playReport.setHasPlayed(true);
		playReport.setVote(2d);
		panchina.setMatchReport(playReport); // score deve essere popolato
		panchina.setRole((short) 1);
		panchina.setTitolare(false);
		result.add(panchina);

		IntStream.range(0, 6).forEach(i -> {
			Player p = new Player();
			p.setRole((short) 1);
			PlayerReport r = new PlayerReport();
			r.setVote(1d);
			p.setMatchReport(r);
			result.add(p);
		});

		return result;
	}

	private List<Player> teamSize1() {
		List<Player> result = new ArrayList<>();

		IntStream.range(0, 1).forEach(i -> {
			Player p1 = new Player();
			PlayerReport matchReport = new PlayerReport();
			matchReport.setVote(1d);
			p1.setRole((short) 1);
			matchReport.setHasPlayed(true);
			p1.setMatchReport(matchReport);
			p1.setTitolare(true);
			result.add(p1);
		});

		return result;
	}

	private List<Player> setupForTest1() {
		List<Player> result = new ArrayList<>();

		IntStream.range(0, 11).forEach(i -> {
			Player p1 = new Player();
			PlayerReport matchReport = new PlayerReport();
			matchReport.setHasPlayed(true);
			matchReport.setVote(1d);
			p1.setMatchReport(matchReport);
			p1.setTitolare(true);
			p1.setRole((short) 1);
			result.add(p1);
		});

		return result;
	}

	public class TestMatchDataManager implements MatchDataManager {

		@Override
		public PlayerReport getTabellino(Player player) {
			PlayerReport matchReport = new PlayerReport();
			matchReport.setHasPlayed(true);
			matchReport.setVote(6d);
			matchReport.setAssist(1);
			matchReport.setGoals(2);
			matchReport.setYellowCard(true);
			return matchReport;
		}

	}

	private class DummieMatchManager implements MatchDataManager {

		@Override
		public PlayerReport getTabellino(Player player) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
