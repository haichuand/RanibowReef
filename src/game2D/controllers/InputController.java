package game2D.controllers;

import game2D.GameWorld;
import game2D.objects.PlayerShip;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * Listens to user key input and controls the movement of PlayerShip units. It sets the left, up, right, down and firing flags
 * based on the keys pressed by the player. The mapping of key values to movements is determined by the keys integer array.
 * @author Fudou
 *
 */
public class InputController implements KeyListener{
	
	int[] keys;
	PlayerShip player;
	/**
	 * Default key mapping, left = left arrow, up = up arrow, right = right arrow, down = down arrow, fire = space bar
	 */
	final static int[] DEFAULT_KEYS = new int[] {KeyEvent.VK_LEFT,KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_SPACE};
	/**
	 * Constructs an InputController with default keys
	 * @param player The PlayerShip that is controlled by the input controller
	 * @param world The current game world
	 */
	public InputController(PlayerShip player, GameWorld world){
		this(player, DEFAULT_KEYS, world);
	}
	/**
	 * Constructs an InputController with specified key mapping
	 * @param player The PlayerShip that is controlled by the input controller
	 * @param keys The desired key mapping, in the order of left, up, right, down and fire keys
	 * @param world The current game world
	 */
	public InputController(PlayerShip player, int[] keys, GameWorld world){
		this.player = player;
		this.keys = keys;
		world.addKeyListener(this);
	}
	/**
	 * Event handler for player key input. Do not call this method.
	 */
    public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		// left
		if(code==keys[0]) {
			player.left = 1;
		}
		// up
		else if(code==keys[1]) {
			player.up = 1;
		}
		// right
		else if(code==keys[2]) {
			player.right = 1;
		}
		// down
		else if(code==keys[3]) {
			player.down = 1;
		}
		// fire
		else if(code==keys[4]){
			player.startFiring();
		}
    }
    /**
	 * Event handler for player key input. Do not call this method.
	 */
    public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code==keys[0]) {		//
			player.left = 0;
		}
		else if(code==keys[1]) {
			player.up = 0;
		}
		else if(code==keys[2]) {
			player.right = 0;
		}
		else if(code==keys[3]) {
			player.down = 0;
		}
		else if(code==keys[4]){
			player.stopFiring();
		}
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}