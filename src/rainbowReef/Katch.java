package rainbowReef;

import game2D.GameWorld;
import game2D.controllers.InputController;
import game2D.objects.PlayerShip;

import java.awt.Point;
import java.util.Observable;
/**
 * Katch is the moving sea snail to catch pop. Katch moves only left or right. Pop bounces off katch in directions that
 * depend on the point of impact
 * @author Fudou
 */
public class Katch extends PlayerShip {
	/**
	 * The starting location of katch is always fixed
	 */
	public Katch() {
		super(new Point(GameWorld.sizeX/2-20, GameWorld.sizeY-50), new Point(5,0), 1, 1, GameWorld.sprites.get("katch1"), "Katch");
		this.controller = new InputController (this, ReefWorld.getInstance());
	}
	/**
	 * Animates katch image and sets left or right movement flags
	 */
	@Override
	public void update(Observable ob, Object arg) {
		if (GameWorld.clock.getFrame()%50 < 25)
			this.setImage(GameWorld.sprites.get("katch1"));
		else
			this.setImage(GameWorld.sprites.get("katch2"));
		if (this.left == 1 && this.getX()>20)
			this.motion.move(-2);
		if (this.right == 1 && this.getX()<GameWorld.sizeX-100)
			this.motion.move(2);
	}

}
