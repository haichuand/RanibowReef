package rainbowReef;

import game2D.GameWorld;

import java.awt.Point;
/**
 * BlockSplit splits pop into two pops upon collision
 * @author Fudou
 *
 */
public class BlockSplit extends BlockBreakable {
	/**
	 * 
	 * @param loc The location point to put the block
	 */
	public BlockSplit(Point loc) {
		super(loc, GameWorld.sprites.get("blockSplit"));
		this.score = 0;
	}

}
