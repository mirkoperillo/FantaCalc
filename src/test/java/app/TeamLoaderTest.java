package app;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import app.model.Player;

public class TeamLoaderTest {

	@Test
	public void caricamentoUnDato() throws URISyntaxException {
		TeamLoader loader = new TeamLoader();
		List<Player> players = loader.read(new File(
				Thread.currentThread().getContextClassLoader().getResource("teamloader-tests/sample_1.txt").toURI()));
		Assert.assertEquals(1, players.size());
		Assert.assertEquals("ABBIATI Christian", players.get(0).getName());
		Assert.assertEquals(1, players.get(0).getRole());
	}

	@Test
	public void caricamentoDueDati() throws URISyntaxException {
		TeamLoader loader = new TeamLoader();
		List<Player> players = loader.read(new File(
				Thread.currentThread().getContextClassLoader().getResource("teamloader-tests/sample_2.txt").toURI()));
		Assert.assertEquals(2, players.size());
		Assert.assertEquals("ABBIATI Christian", players.get(0).getName());
		Assert.assertEquals(1, players.get(0).getRole());
	}

	@Test
	public void carica2volteFile() throws URISyntaxException {
		TeamLoader loader = new TeamLoader();
		// file contiene due giocatori
		loader.read(new File(
				Thread.currentThread().getContextClassLoader().getResource("teamloader-tests/sample_2.txt").toURI()));
		List<Player> players = loader.read(new File(
				Thread.currentThread().getContextClassLoader().getResource("teamloader-tests/sample_2.txt").toURI()));
		Assert.assertEquals(4, players.size());
	}

	@Test
	public void ruoloNonSupportato() throws URISyntaxException {
		TeamLoader loader = new TeamLoader();
		List<Player> players = loader.read(new File(
				Thread.currentThread().getContextClassLoader().getResource("teamloader-tests/sample_3.txt").toURI()));
		Assert.assertEquals(1, players.size());
		Assert.assertEquals("ABBIATI Christian", players.get(0).getName());
		Assert.assertEquals(15, players.get(0).getRole());
	}

	@Test
	public void rigaVuota() throws URISyntaxException {
		TeamLoader loader = new TeamLoader();
		List<Player> players = loader.read(new File(
				Thread.currentThread().getContextClassLoader().getResource("teamloader-tests/sample_4.txt").toURI()));
		Assert.assertEquals(2, players.size());
		Assert.assertEquals("ABBIATI Christian", players.get(0).getName());
		Assert.assertEquals(1, players.get(0).getRole());
	}

}
