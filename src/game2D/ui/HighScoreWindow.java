package game2D.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * High score window for displaying, adding to and sorting the high score list.
 * The list is read first from a local txt file ("user.dir"+"file.separator"+"score.txt"). If it's not available, 
 * then it is read from the fileName passed to the constructor. Each line of the list file must contain exactly two fields separated by a comma:
 * playerName,score
 * The list contains the highest 10 scores. It is saved to the local txt file ("user.dir"+"file.separator"+"score.txt").
 * Extend the class to construct specific high score windows for the game.
 * @author Fudou
 */
public abstract class HighScoreWindow extends JPanel {
	private static final long serialVersionUID = -262660727628381695L;
	protected String fileName;
	private BufferedImage backgroundImg, img;
	private ArrayList<PlayerScore> list = new ArrayList<PlayerScore>(10);
	private BufferedReader in;
	private OutputStreamWriter out;
	private String line;
	private String scoreFile; //local txt file path for storing high score list
	private JFrame highScores = new JFrame("High Scores");
	
	/**
	 * Constructs a high score window with background image and a high score list. The window size is 600X600 pixels
	 * @param background The background image to display for the high score window
	 * @param fileName The file name and path for the file that contains the high score list. Each line of the list file must contain exactly two fields separated by a comma: playerName,score
	 */
	public HighScoreWindow (BufferedImage background, String fileName) {
		this.backgroundImg = background;
		this.fileName = fileName;
		highScores.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		scoreFile = System.getProperty("user.dir") + System.getProperty("file.separator") + "score.txt";
		try {
			File f = new File(scoreFile);
			if (f.exists() && !f.isDirectory())
				in = new BufferedReader (new InputStreamReader(new FileInputStream(f)));
			else
				in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName)));
			line = in.readLine();
			String[] data;
			
			while (line!= null && !line.isEmpty()) {
				data = line.split(",");
				PlayerScore thisPlayer = new PlayerScore(data[0], Integer.parseInt(data[1]));
				list.add(thisPlayer);
				line = in.readLine();
			}
			in.close();
		}
		catch (IOException e) {
			System.out.println("Game2D.ui.HighScoreList: cannot read " + fileName);
		}
		catch (Exception e) {
			System.out.println("Game2D.ui.HighScoreList: error processing " + fileName);
			e.printStackTrace();
		}
	}
	/**
	 * Overriden method to allow painting of high score list
	 */
	@Override
	public void paint(Graphics g) {
		img = (BufferedImage) createImage (600, 600);
		Graphics2D g2 = img.createGraphics();
		g2.drawImage(backgroundImg, 0, 0, 600, 600, null);
		
		g2.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
		g2.setColor(Color.BLACK);
		g2.drawString("High Scores", 200, 50);
		g2.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		for (int i=0; i<Math.min(10, list.size()); i++) {
			g2.drawString(Integer.toString(i+1)+".", 60, 100+i*50);
			g2.drawString(list.get(i).player, 160, 100+i*50);
			g2.drawString(Integer.toString(list.get(i).score), 480, 100+i*50);
		}
		g2.dispose();
		g.drawImage(img, 0, 0, 600, 600, null);	
	}
	/**
	 * Displays the high score window
	 */
	public void display() {
		Collections.sort(list);
		repaint();
		highScores.setContentPane(this);
		highScores.pack();
		highScores.setSize(new Dimension(600,600));
		highScores.setVisible(true);
	    highScores.setResizable(false);
	    highScores.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	/**
	 * Add another player-score pair to the high score list. It also sorts and saves the list.
	 * @param player The player's name
	 * @param score The player's score
	 */
	public void add(String player, int score) {
		list.add(new PlayerScore (player, score));
		Collections.sort(list);
		save();
		display();
	}
	/**
	 * Saves the highest 10 player scores for the list. The list is saved in a local txt file ("user.dir"+"file.separator"+"score.txt").
	 */
	public void save() {
		try {
			out = new OutputStreamWriter(new FileOutputStream(scoreFile)); //writing to local txt file
			System.out.println("Saving high score list in: "+ scoreFile);
			for (int i=0; i<Math.min(10, list.size()); i++) {
				String record = list.get(i).player + "," + Integer.toString(list.get(i).score) + "\n";
				out.write(record);
			}
			out.close();
		}
		catch (Exception e) {
			System.out.println("Game2D.ui.HighScoreList: error saving" + fileName);
			e.printStackTrace();
		}
	}
	
	/**
	 * Helper class for organizing and sorting player-score pair
	 * @author Fudou
	 */
	class PlayerScore implements Comparable<PlayerScore> {
		String player;
		int score;
		
		public PlayerScore(String thePlayer, int theScore) {
			player = thePlayer;
			score = theScore;
		}

		@Override
		public int compareTo(PlayerScore other) {
			return (other.score - this.score);
		}
	}
	

}
