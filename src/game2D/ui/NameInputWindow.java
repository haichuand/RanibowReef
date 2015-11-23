package game2D.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * Used to display player name input window after game finishes. Extend the class to construct specific subclasses for the game.
 * @author Fudou
 *
 */
public abstract class NameInputWindow extends JPanel implements KeyListener {
	private static final long serialVersionUID = 974008214940770232L;
	protected BufferedImage img, congratImg;
	protected String playerName = "";
	protected int playerScore;
	protected JFrame nameInput;
	/**
	 * Constructs a player name input window with specified background image
	 * @param img The background image for the window
	 * @param score The score points of the player. Needed for the high score list
	 */
	public NameInputWindow(BufferedImage img, int score) {
		this.congratImg = img;		
		this.playerScore = score;
		nameInput = new JFrame("Player name input");
		nameInput.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				nameInput.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		nameInput.addKeyListener(this);
	}
	/**
	 * Overriden method to allow painting of user input window
	 */
	@Override
	public void paint(Graphics g) {
		img = (BufferedImage) createImage (640, 480);
		Graphics2D g2 = img.createGraphics();
		g2.drawImage(congratImg, 0, 0, 640, 480, null);
		
		g2.setFont(new Font("Comic Sans MS", Font.BOLD, 44));
		g2.setColor(Color.BLACK);
		g2.drawString("Please enter your name", 70, 100);
		g2.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
		g2.drawString(playerName+"_", 100, 200);
		
		g2.dispose();
		g.drawImage(img, 0, 0, 640, 480, null);	
	}
	
	/**
	 * Displays the name input window at a size of 640X480 pixels
	 */
	public void display() {
		nameInput.setContentPane(this);
		nameInput.pack();
		nameInput.setSize(new Dimension(640,480));
		nameInput.setVisible(true);
	    nameInput.setResizable(false);
	    nameInput.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	/**
	 * Must be implemented by subclasses to handle user key input
	 */
	public abstract void keyTyped (KeyEvent e);

}
