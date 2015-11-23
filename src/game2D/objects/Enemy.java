package game2D.objects;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Observable;

/**
 * Enemy objects in the game
 * @author Fudou
 *
 */
public class Enemy extends MoveableObject {
	/**
	 * Construct an enemy object with specified parameters
	 * @param location Location of the enemy
	 * @param speed Speed of the enemy
	 * @param strength Strength of the dnemy
	 * @param health Hit points of the enemy
	 * @param img Image of the enemy
	 */
	public Enemy(Point location, Point speed, int strength, int health,
			BufferedImage img) {
		super(location, speed, strength, health, img);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void damage(int damageDone) {
		// TODO Auto-generated method stub
		
	}

}
