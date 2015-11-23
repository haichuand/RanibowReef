package rainbowReef;

import game2D.objects.BackgroundObject;

import java.awt.Point;
import java.awt.image.BufferedImage;
/**
 * Breakable blocks can be destroyed by pop collision. Some breakable blocks have score points, while some give pop life or split pop
 * @author Fudou
 *
 */
public abstract class BlockBreakable extends BackgroundObject {
	protected int score;
	/**
	 * @param loc The location of the breakable block
	 * @param img The image of the block
	 */
	public BlockBreakable (Point loc, BufferedImage img) {
		super(loc, img);
	}
	/**
	 * @return The score points of the block
	 */
	public int getScore() {
		return score;
	}
}
