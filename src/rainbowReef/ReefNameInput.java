package rainbowReef;

import game2D.ui.NameInputWindow;

import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
/**
 * Name input window for Rainbow reef game. Called after game finishes to record player name and score.
 * @author Fudou
 */
public class ReefNameInput extends NameInputWindow {

	private static final long serialVersionUID = 3942191317141262193L;
	/**
	 * Constructs an object to display player name input window
	 * @param img The background image for the name input window.
	 * @param score The score of the player.
	 */
	public ReefNameInput(BufferedImage img, int score) {
		super(img, score);
	}
	/**
	 * Processes player's name input
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		if (Character.isLetter(e.getKeyChar()) || Character.isDigit(e.getKeyChar())) {
			playerName += e.getKeyChar();
			repaint();
		}
		else if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			ReefWorld.highScore.add(playerName, playerScore);
			nameInput.dispatchEvent(new WindowEvent(nameInput, WindowEvent.WINDOW_CLOSING));
		}
		else if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE && !playerName.isEmpty()) {
			playerName = playerName.substring(0, playerName.length()-1);
			repaint();
		}
		else if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			playerName = playerName + " ";
			repaint();
		}
			
	}
}
