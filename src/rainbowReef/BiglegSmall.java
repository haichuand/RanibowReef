package rainbowReef;

import game2D.GameWorld;

import java.awt.Point;
import java.util.Observable;
/**
 * Class for small big legs.
 * @author Fudou
 */
public class BiglegSmall extends Bigleg {
	/**
	 * 
	 * @param location The location point for the samll bigleg
	 */
	public BiglegSmall(Point location) {
		super(location);
		this.setImage(GameWorld.sprites.get("biglegSmall1"));
		this.score = 100;
	}
	/**
	 * animates image for small bigleg
	 */
	@Override
	public void update(Observable ob, Object arg) {
		if(GameWorld.clock.getFrame()%50 < 25) {
			this.setImage(GameWorld.sprites.get("biglegSmall1"));
		}
		else {
			this.setImage(GameWorld.sprites.get("biglegSmall2"));
		}
	}

}
