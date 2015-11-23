package rainbowReef;

import game2D.ui.HighScoreWindow;
/**
 * High score list window for Raibow reef game.
 * @author Fudou
 *
 */
public class ReefHighScore extends HighScoreWindow {

	private static final long serialVersionUID = -6531097537929304044L;

	public ReefHighScore() {
		super(ReefWorld.sprites.get("background1"), "/rainbowReef/Resources/score.txt");
	}

}
