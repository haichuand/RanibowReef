package rainbowReef;

import game2D.GameSounds;
import game2D.GameWorld;
import game2D.objects.GameObject;
import game2D.objects.MoveableObject;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Observable;
/**
 * Pop is the bouncing starfish.
 * @author Fudou
 *
 */
public class Pop extends MoveableObject {
	int lives;
	PopMotion motion;
	static Point resetPoint;
	private int respawnCounter;
	private boolean isRespawning;
	/**
	 * Constructs a new instance of pop
	 * @param loc The location of pop
	 * @param speed The speed of pop
	 */
	public Pop (Point loc, Point2D.Double speed) {
		//speed filed in MoveableObject is not used; instead use motion.popSpeed
		super (loc, new Point(0,0), 20, 20, GameWorld.sprites.get("pop1"));
		motion = new PopMotion(this);
		motion.popSpeed = speed;
		motion.popLoc = new Point2D.Double(loc.x, loc.y);
		this.lives = 3;
		resetPoint = new Point(ReefWorld.sizeX/2-17, ReefWorld.sizeY-300);
		respawnCounter = 50;
		isRespawning = false;
	}
	
	/**
     * Detects if pop collides with another game object (wall, block, katch, bigleg) and gets the intersect rectangle to calculate pop bounce direction.
     * It firsts check if the two location rectangles intersect. 
     * If yes, it then uses image bit masks to determine if any non-transparent parts overlap.
     * @param otherObj The other object to detect collision against
     * @return True if collision is detected, false otherwise.
     */
    public Rectangle popCollision(GameObject otherObj) {
    	Rectangle intersect = location.intersection(otherObj.getLocation());
        if(!intersect.isEmpty()){
        	return intersect;
        }        
        return null;
    }
    /**
     * checks if pop is dead, respawning, lost, then moves pop and animates image
     */
	@Override
	public void update(Observable o, Object arg) {
		if (isDead) {
			ReefWorld.popList.remove(this);
			return;
		}
		if (isRespawning) {
			respawnCounter--;
			if (respawnCounter < 0){				
				isRespawning = false;
			}				
		}
		else if (this.getY() > ReefWorld.katch.getY()-25) {
			this.die();
			GameSounds.play("/rainbowReef/Resources/Sound_lost.wav");
		}
		else{
			motion.move(1);
			int num = GameWorld.clock.getFrame()%45+1;
			this.setImage(GameWorld.sprites.get("pop"+ num));
		}
	}

	@Override
	public void damage(int damageDone) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Resets pop location to resetPoint
	 */
	public void reset()	{
		this.setLocation(resetPoint);
		this.motion.popLoc.x = resetPoint.x;
		this.motion.popLoc.y = resetPoint.y;
		this.motion.popSpeed = new Point2D.Double(1.5, -7);
	}
	/**
	 * Reduces pop's lives until no life left.
	 */
	@Override
	public void die() {
		this.lives -= 1;
		if (lives <=0 )
			isDead = true;
		else {
			reset();
			respawnCounter = 50;
			isRespawning = true;
		}
	}
}
