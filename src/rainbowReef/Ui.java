package rainbowReef;

import game2D.GameWorld;
import game2D.ui.InterfaceObject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
/**
 * Displays pop's life left and player's score
 * @author Fudou
 */
public class Ui extends InterfaceObject {
	

	public Ui() {
		super(new Point(20, GameWorld.sizeY-20), GameWorld.sprites.get("katchSmall"));
	}
	
	/**
	 * Draws the Ui object if the field show is true
	 * @param g The graphics object
	 * @param ob The image observer
	 */
	@Override
	public void draw(Graphics g, ImageObserver ob) {
		int popNum = ReefWorld.popList.size();
		for (int i=0; i<popNum; i++) {
			int offset = i*180;
			for (int j=0; j<ReefWorld.popList.get(i).lives; j++) {
				g.drawImage(img, location.x+offset+40*j, location.y, ob);
			}
		}
		g.setFont(new Font("Calibri", Font.BOLD, 20));
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(ReefWorld.getInstance().playerScore), location.x+650, location.y+10);
		
	}

}
