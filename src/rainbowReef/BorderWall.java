package rainbowReef;

import game2D.GameWorld;
import game2D.objects.BackgroundObject;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
/**
 * BorderWall appears on the top, left and right borders of the Rainbow Reef game window.
 * The smaller wall units are combined into 3 big blocks (left, top, right) to improve efficiency
 * @author Fudou
 *
 */
public class BorderWall extends BackgroundObject{
	/**
	 * Constructs a combined border wall starting from specified location and extending for x and y units
	 * @param loc Upper left location of wall
	 * @param xUnits Number of horizontal wall blocks
	 * @param yUnits Number of vertical wall blocks
	 */
	public BorderWall(Point loc, int xUnits, int yUnits) {
		super(loc, GameWorld.sprites.get("wall"));
		BufferedImage img = this.getImg();
		BufferedImage wallImg = new BufferedImage(img.getWidth()*xUnits, img.getHeight()*yUnits, BufferedImage.TYPE_INT_ARGB);
		Graphics g = wallImg.getGraphics();
		for (int i=0; i<xUnits; i++) { //makes a combined border wall image
			for (int j=0; j<yUnits; j++)
			g.drawImage(img, img.getWidth()*i, img.getHeight()*j, null);
		}
		this.setImage(wallImg);
	}

}
