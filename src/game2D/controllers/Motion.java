package game2D.controllers;

import game2D.objects.GameObject;
import game2D.objects.MoveableObject;

import java.awt.Point;
import java.awt.Rectangle;

/**Motion class and its subclasses move around moveable objects.
 * Motion class only performs integer axis motions based on Point speed and Rectangle location.
 * @author Fudou
 *
 */
public class Motion {
	protected MoveableObject subject;
	
	public Motion(MoveableObject obj) {
		subject = obj;
	}
	
	/**
	 * Moves subject the number of steps. Each step is one translocation of speed.
	 * @param steps The steps to move. Each step moves object one speed unit.
	 */
	public void move (int steps) {
		subject.getLocation().x += subject.getSpeed().x*steps;
		subject.getLocation().y += subject.getSpeed().y*steps;
	}
	
	/**
	 * Reposition the MoveableObject relative to the other object after a collision is detected.
	 * It translocates the subject on the x or y axis based on which movement is smaller.
	 * @param otherObj The object to position against (not moved).
	 */
	public void collisionReposition(GameObject otherObj) {
    	Rectangle myLoc = subject.getLocation(), otherLoc = otherObj.getLocation();
    	Point myCenter = new Point((myLoc.x + myLoc.width/2), (myLoc.y + myLoc.height/2));
    	Point otherCenter = new Point((otherLoc.x + otherLoc.width/2), (otherLoc.y + otherLoc.height/2));
    	
    	if (Math.abs(Math.abs(myCenter.x-otherCenter.x)-(myLoc.width+otherLoc.width)/2) <= 
    			Math.abs(Math.abs(myCenter.y-otherCenter.y)-(myLoc.height+otherLoc.height)/2)) { //reposition myLoc.x
    		if (myCenter.x < otherCenter.x) {
    			subject.setLocation(otherLoc.x-myLoc.width, myLoc.y);
    		}
    		else {
    			subject.setLocation(otherLoc.x+otherLoc.width, myLoc.y);
    		}
    	}
    	else { //reposition myLoc.y
    		if (myCenter.y < otherCenter.y) {
    			subject.setLocation(myLoc.x, otherLoc.y-myLoc.height);
    		}
    		else {
    			subject.setLocation(myLoc.x, otherLoc.y+otherLoc.height);
    		}
    	}
    }
}
