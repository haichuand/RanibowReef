package rainbowReef;

import game2D.GameWorld;
import game2D.objects.BackgroundObject;

import java.awt.Point;

/**
 * Solid blocks are non-breakable
 * @author Fudou
 *
 */
public class BlockSolid extends BackgroundObject {
	/**
	 * @param loc The location of the block
	 */
	public BlockSolid (Point loc) {
		super(loc, GameWorld.sprites.get("blockSolid"));
	}
}
