package app;

import app.model.Player;
import app.model.PlayerReport;

public interface MatchDataManager {
	PlayerReport getTabellino(Player player);
}
