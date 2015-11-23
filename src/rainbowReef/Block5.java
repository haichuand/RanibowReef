package rainbowReef;

import game2D.GameWorld;

import java.awt.Point;
/**
 * Blocks 1~7 have increasing score points
 * @author Fudou
 */
public class Block5 extends BlockBreakable {
	/**
	 * 
	 * @param loc The location point for the block
	 */
	public Block5(Point loc) {
		super(loc, GameWorld.sprites.get("block5"));
		this.score = 50;
	}

}
