package rainbowReef;

import game2D.GameWorld;

import java.awt.Point;
/**
 * Blocks 1~7 have increasing score points
 * @author Fudou
 *
 */
public class Block1 extends BlockBreakable {
	/**
	 * 
	 * @param loc The location point for the block
	 */
	public Block1(Point loc) {
		super(loc, GameWorld.sprites.get("block1"));
		this.score = 10;
	}

}
