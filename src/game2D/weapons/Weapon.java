package game2D.weapons;

import game2D.GameWorld;
import game2D.objects.MoveableObject;

import java.util.Observable;
import java.util.Observer;

/**
 * Weapons are possessed by MoveableObjects in the game. Weapons observe game clock to count reload time.
 * @author Fudou
 *
 */
public abstract class Weapon implements Observer {
	
	protected boolean friendly;
	protected int reloadTime;
	
	protected int strength;
	protected int reloadCount;
	/**
	 * Constructs a weapon
	 * @param strength The strength of the weapon
	 * @param reloadTime The reload time of the weapon
	 */
	public Weapon(int strength, int reloadTime){
		this.reloadTime = reloadTime;
		this.strength = strength;
		reloadCount = reloadTime;
		GameWorld.addClockObserver(this);
	}
	/**
	 * If the reload count reaches reload time, fires the weapon
	 */
	@Override
	public void update (Observable ob, Object arg) {
		if (reloadCount < reloadTime)
			reloadCount++;
	}
	/**
	 * Dispose the weapon
	 */
	public void dispose() {
		GameWorld.removeClockObserver(this);
	}
	/**
	 * Subclasses implement fireWeapon to specify firing behavior
	 * @param theShip The ship that fires the weapon
	 */
	public abstract void fireWeapon (MoveableObject theShip);
	
}
