package rainbowReef;

import game2D.GameWorld;
import game2D.objects.GameObject;

import java.awt.Point;
import java.util.Observable;
/**
 * Class for big legs
 * @author Fudou
 *
 */
public class Bigleg extends GameObject {
	int score;
	/**
	 * @param loc Location of bigleg to put
	 */
	public Bigleg(Point loc) {
		super(loc, GameWorld.sprites.get("bigleg1"));
		this.score = 200;
		ReefWorld.biglegList.add(this);
		GameWorld.addClockObserver(this);
	}
	/**
	 * Animates bigleg image
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if(GameWorld.clock.getFrame()%50 < 25) {
			this.setImage(GameWorld.sprites.get("bigleg1"));
		}
		else {
			this.setImage(GameWorld.sprites.get("bigleg2"));
		}
	}
	/**
	 * @return The score points of bigleg
	 */
	public int getScore() {
		return score;
	}

}
