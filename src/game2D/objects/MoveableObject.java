package game2D.objects;

import game2D.GameWorld;
import game2D.controllers.Motion;
import game2D.weapons.Weapon;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Moving objects in the game. The movement is controlled by Motion object. They have speed, strength, health and weapon too.
 * @author Fudou
 *
 */
public abstract class MoveableObject extends GameObject {
	/**
	 * motion determines movement of the moveable object
	 */
	protected Motion motion;
	protected Point speed;
	protected int strength;
	protected int health;
	protected Weapon weapon;
    protected Point gunLocation;
    
    /**
     * 
     * @param location Location of the object
     * @param speed Speed of the object
     * @param strength Strength of the object
     * @param health Health points of the object
     * @param img Image of the object
     */
    public MoveableObject(Point location, Point speed, int strength, int health, BufferedImage img){
    	super(location,img);
    	this.speed=speed;
    	this.strength=strength;
    	this.health=health;
    	this.motion = new Motion(this);
    	GameWorld.clock.addObserver(this);
    }
    /**
     * Damage the moveable object specified points
     * @param damageDone The damage to do to the object
     */
    public abstract void damage(int damageDone);
    
    /**
     * Subclasses of MoveableObject must implement die method.
     */
    public abstract void die();
    /**
     * Sets the health (hit points) of the moveable object
     * @param health The health points to set for the object
     */
    public void setHealth(int health){
    	this.health = health;
    }
    /**
     * Gets the health points of the object
     * @return The health points of the object
     */
    public int getHealth(){
    	return health;
    }
    /**
     * @return The Motion object of the moveable object
     */
    public Motion getMotion(){
    	return this.motion;
    }
    /**
     * 
     * @param motion The motion object to set for the moveable object
     */
    public void setMotion(Motion motion){
    	this.motion = motion;
    }
    /**
     * 
     * @return The speed of the moveable object
     */
  	public Point getSpeed() {
		return speed;
	}
  	/**
  	 * 
  	 * @param speed The speed to set
  	 */
  	public void setSpeed (Point speed) {
  		this.speed = speed;
  	}
  	
  	/**
  	 * 
  	 * @return The weapon of the moveable object
  	 */
    public Weapon getWeapon(){
    	return this.weapon;
    }
    /**
     * 
     * @param weapon The weapon to set for the object
     */
    public void setWeapon(Weapon weapon) {
    	this.weapon = weapon;
    }
    /**
     * 
     * @return The location point of gun for the moveable object
     */
    public Point getGunLocation(){
    	return this.gunLocation;
    }

}