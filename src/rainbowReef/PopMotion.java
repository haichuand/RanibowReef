package rainbowReef;

import game2D.controllers.Motion;
import game2D.objects.GameObject;
import game2D.objects.MoveableObject;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
/**
 * PopMotion controls motion of pop. It calculates pop's location in the next frame based on pop's current location, speed and gravity.
 * Gravity gradually increases pop's y speed until it reaches a maximum.
 * PopMotion also calculates pop's new direction after bouncing with wall, blocks and katch.
 * @author Fudou
 *
 */
public class PopMotion extends Motion {
	/**
	 * speed of pop in double precision
	 */
	Point2D.Double popSpeed;
	/**
	 * location of pop in double precision
	 */
	Point2D.Double popLoc;
	private static final double MAX_SPEED = 15; //maximum speed of pop so that it doesn't get too difficult

	/**
	 * gravity determines how fast the y speed increases
	 */
	double gravity = 0.005;
	final int KATCH_WIDTH = 80;
	final int POP_WIDTH = 35;
	final double[] BOUNCE_DEG = new double[] {130, 110, 90, 70, 50};
	
	public PopMotion(MoveableObject obj) {
		super(obj);
		
	}
	/**
	 * Moves pop after each clock tick. It first calculates changes in y speed based on gravity, then updates popLoc
	 */
	@Override
	public void move(int steps) {
		if (popSpeed.y>0 && popSpeed.y<MAX_SPEED)
			popSpeed.y += gravity*steps;
		popLoc.x += popSpeed.x*steps;
		popLoc.y += popSpeed.y*steps;
		this.subject.setLocation((int)popLoc.x, (int)popLoc.y);
	}
	/**
	 * Updates pop speed after collision with wall, blocks and katch
	 * @param obj The object pop bounces against
	 * @param intersect The intersection between pop and the other object, returned by Pop.collision()
	 */
	public void bounce(GameObject obj, Rectangle intersect) {
		if (obj instanceof Katch) {
			if (intersect.x+intersect.width < obj.getX()+KATCH_WIDTH*0.225+POP_WIDTH/2) { //bounce 130 degrees
				popSpeed.x = popSpeed.y*Math.cos(Math.toRadians(BOUNCE_DEG[0]));
			}
			else if (intersect.x+intersect.width < obj.getX()+KATCH_WIDTH*0.45+POP_WIDTH/2) { //bounce 110 degrees
				popSpeed.x = popSpeed.y*Math.cos(Math.toRadians(BOUNCE_DEG[1]));
			}
			else if (intersect.x+intersect.width < obj.getX()+KATCH_WIDTH*0.55+POP_WIDTH/2) { //bounce 90 degrees
				popSpeed.x = popSpeed.y*Math.cos(Math.toRadians(BOUNCE_DEG[2]));
			}
			else if (intersect.x+intersect.width < obj.getX()+KATCH_WIDTH*0.775+POP_WIDTH/2) { //bounce 70 degrees
				popSpeed.x = popSpeed.y*Math.cos(Math.toRadians(BOUNCE_DEG[3]));
			}
			else { //bounce 50 degrees
				popSpeed.x = popSpeed.y*Math.cos(Math.toRadians(BOUNCE_DEG[4]));
			}
			popSpeed.y = -popSpeed.y;
			popLoc.y = ReefWorld.katch.getY()-35; //reset pop y location above katch
			this.subject.setLocation((int)popLoc.x, (int)popLoc.y);
		}
		else { //collision with wall & blocks
			if (intersect.width > intersect.height) { //collision with ceiling or floor
				if (Math.abs(intersect.y-popLoc.y)<2) { //collision with ceiling
					popLoc.y += intersect.height;
				}
				else { //collision with floor
					popLoc.y -= intersect.height;
				}
				subject.setLocation((int)popLoc.x, (int)popLoc.y);
				popSpeed.y = -popSpeed.y;
			}
			else if (intersect.width < intersect.height) { //collision on left or right sides of pop
				if (Math.abs(intersect.x-popLoc.x)<2) { //collision on left side of pop
					popLoc.x += intersect.width;
				}
				else { //collision on right side of pop
					popLoc.x -= intersect.width;
				}
				subject.setLocation((int)popLoc.x, (int)popLoc.y);
				popSpeed.x = -popSpeed.x;
			}
			else { //exactly corner collision
				popSpeed.x = -popSpeed.x;
				popSpeed.y = -popSpeed.y;
			}
		}
	}
}
