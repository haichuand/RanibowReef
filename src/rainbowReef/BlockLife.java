package rainbowReef;

import game2D.GameWorld;

import java.awt.Point;
/**
 * BlockLife gives pop an extra life
 * @author Fudou
 *
 */
public class BlockLife extends BlockBreakable {
	/**
	 * 
	 * @param loc The location of the block
	 */
	public BlockLife(Point loc) {
		super(loc, GameWorld.sprites.get("blockLife"));
		this.score = 0;
	}

}
