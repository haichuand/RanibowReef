package game2D.ui;

import game2D.GameSounds;
import game2D.GameWorld;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
/**
 * A game start menu with 4 buttons: Start, Help, Score and Quit. The sprites Hashmap in GameWorld must contain images for the buttons.
 * @author Fudou
 *
 */
public class StartMenu extends JPanel implements MouseListener {
	protected int width, height;
	protected GameWorld game;
	protected BufferedImage img;
	private static final long serialVersionUID = 4638806524120731727L;
	private JButton buttonStart, buttonHelp, buttonScore, buttonQuit;
	/**
	 * Constructs a start menu for the game
	 * @param width Width of the start menu window
	 * @param height Height of the start menu window
	 * @param img Background image for the start menu
	 * @param game The game from which the start menu is called
	 */
	public StartMenu(int width, int height, BufferedImage img, GameWorld game) {
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width,height));
		this.img = img;
		this.game = game;
		buttonStart = new JButton(new ImageIcon(GameWorld.sprites.get("start")));
		buttonHelp = new JButton(new ImageIcon(GameWorld.sprites.get("help")));
		buttonScore = new JButton(new ImageIcon(GameWorld.sprites.get("score")));
		buttonQuit = new JButton(new ImageIcon(GameWorld.sprites.get("quit")));
		
		this.setLayout(new GridLayout(17, 1));
		JPanel filler; //Transparent JPanel to fill empty spaces
		for (int i=0; i<14; i++) {
			filler = new JPanel();
			filler.setOpaque(false);
			this.add(filler);
		}
		JPanel menuRow = new JPanel();
		menuRow.setOpaque(false);
		this.add(menuRow);
		menuRow.setLayout(new GridLayout (1, 6, 36, 0));
		filler = new JPanel();
		filler.setOpaque(false);
		menuRow.add(filler);
		menuRow.add(buttonStart);
		menuRow.add(buttonHelp);
		menuRow.add(buttonScore);
		menuRow.add(buttonQuit);
		filler = new JPanel();
		filler.setOpaque(false);
		menuRow.add(filler);
		
		buttonStart.addMouseListener(this);
		buttonHelp.addMouseListener(this);
		buttonScore.addMouseListener(this);
		buttonQuit.addMouseListener(this);
	}
	
	@Override
	/**
	 * The paintComponent method is overwritten to paint the background image
	 */
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, width, height, null);
		
	}
	
	/**
	 * Mouse click event handler. Calls respective methods in game.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		GameSounds.play("/game2D/Resources/Sound_click.wav");
		Object source = e.getSource();
		if (source.equals(buttonStart)) {
			game.initGame();
		}
		else if(source.equals(buttonHelp)) {
			game.showHelp();
		}
		else if(source.equals(buttonScore)) {
			game.showHighScore();
		}
		else if(source.equals(buttonQuit)) {
			System.exit(0);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
