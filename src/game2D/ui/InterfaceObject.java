package game2D.ui;

import game2D.GameWorld;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;
/**
 * Objects related to user interfaces
 * @author Fudou
 *
 */
public class InterfaceObject {
	protected Point location;
	protected Image img;
	protected boolean show;
	/**
	 * @param loc Location of interface object
	 * @param img Image of interface object
	 */
	public InterfaceObject (Point loc, Image img) {
		this.location = loc;
		this.img = img;
		this.show = true;
		GameWorld.addUi(this);
	}
	/**
	 * Sets the show flag of the object
	 * @param sh The show flag to set
	 */
	public void setShow(boolean sh) {
		this.show = sh;
	}
	/**
	 * Gest the show flag of the object
	 * @return The show flag of the object
	 */
	public boolean getShow() {
		return show;
	}
	
	/**
	 * Draws the object if the show flag is true
	 * @param g The Graphics object to draw on
	 * @param ob The image observer
	 */
	public void draw(Graphics g, ImageObserver ob) {
		if (show)
			g.drawImage(img, location.x, location.y, ob);
	}
}
