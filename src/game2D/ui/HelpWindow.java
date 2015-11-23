package game2D.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * Displays a help window with information about the game. Usually launched by clicking the "Help" button on start menu.
 * The help information is contained in a txt file whose name and path is passed to the constructor.
 * @author Fudou
 */
public class HelpWindow extends JTextArea {
	
	private static final long serialVersionUID = 7141269261798959337L;
	private Image img;
	private BufferedReader reader;
	private String helpText;
	private JFrame displayWindow = new JFrame("Help");
	JScrollPane scrollPane;
	static final int ROW_NUM = 10, COL_NUM = 30; //size of text display area
	/**
	 * Construct an object to display the help window. Size of window is 800X800 pixels.
	 * @param img The background image for the window
	 * @param fileName The txt file that contains information to be displayed in the window.
	 */
	public HelpWindow (Image img, String fileName) {
		super(ROW_NUM, COL_NUM);
		this.img = img;
		this.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.setOpaque(false);
		this.setEditable(false);
		this.setMargin(new Insets(10, 15, 0, 15));
		scrollPane = new JScrollPane(this);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(800, 800));
		
		try {
			reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName)));
		} catch (Exception e) {
			System.out.println("game2D.ui.HelpWindow(): Error reading " + fileName);
			e.printStackTrace();
		}
	}
	/**
	 * Display the help window with background image and text.
	 */
	public void display() {
		try {
			helpText = reader.readLine();
			while (helpText != null) {
				helpText += "\n";
				this.append(helpText);
				helpText = reader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		displayWindow.setContentPane(scrollPane);
		displayWindow.validate();
		displayWindow.pack();
		displayWindow.setVisible(true);
		displayWindow.setResizable(false);
		displayWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	/**
	 * paintComponent() method is overwritten to allow painting of background image. This method should not be called.
	 * @param g The graphics object used by Swing. Do not change this object.
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		super.paintComponent(g);
	}
}
