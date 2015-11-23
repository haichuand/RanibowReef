package game2D.weapons;

import game2D.GameWorld;
import game2D.objects.Bullet;
import game2D.objects.MoveableObject;

import java.awt.Point;

/**
 * A simple weapon to use
 * @author Fudou
 *
 */
public class SimpleWeapon extends Weapon {
	/**
	 *  default strength of 5 and reload time of 10
	 */
	public SimpleWeapon(){
		this(5, 10);
	}
	/**
	 * Default strength of 5
	 * @param reload Reload time of the weapon
	 */
	public SimpleWeapon(int reload){
		this(5, reload);
	}
	/**
	 * @param strength Strength of weapon
	 * @param reload Reload time of weapon
	 */
	public SimpleWeapon(int strength, int reload){
		super(strength, reload);
	}
	/**
	 * Fires the weapon
	 */
	@Override
	public void fireWeapon(MoveableObject theShip) {
		
		Point location = theShip.getLocationPoint();
		Point offset = theShip.getGunLocation();
		location.x += offset.x;
		location.y += offset.y;
		Point speed = new Point(theShip.getSpeed().x+20, theShip.getSpeed().y+20);
		
		Bullet bullet = new Bullet(location, speed, strength, GameWorld.sprites.get("bullet"), theShip);
		GameWorld.addBullet(bullet);
	}
}
