package rainbowReef;

import game2D.GameWorld;

import java.awt.Point;
/**
 * Blocks 1~7 have increasing score points
 * @author Fudou
 */
public class Block6 extends BlockBreakable {
	/**
	 * 
	 * @param loc The location point for the block
	 */
	public Block6(Point loc) {
		super(loc, GameWorld.sprites.get("block6"));
		this.score = 60;
	}
}
