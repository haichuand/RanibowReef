package game2D;

import game2D.objects.BackgroundObject;
import game2D.objects.Bullet;
import game2D.objects.Enemy;
import game2D.objects.PlayerShip;
import game2D.objects.PowerUp;
import game2D.objects.SmallExplosion;
import game2D.ui.HighScoreWindow;
import game2D.ui.InterfaceObject;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Observer;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * GameWorld represents the gaming environment. It keeps track of game objects.
 * It has methods to add and remove game objects and iterate through them.
 * It also has methods to detect and resolve collision between objects, and draw game objects.
 * Extend GameWorld to create specific games using the Game2D engine.
 * @author Fudou
 */
public abstract class GameWorld extends JPanel implements Runnable, Observer {
	protected static GameWorld game;
	//game sprite image map
	public static HashMap<String,BufferedImage> sprites = new HashMap<String,BufferedImage>();
	protected boolean gameOver; //is game over
	protected static Point speed;
	private Random generator = new Random();
	//game sound and game clock are singletons
    public static final GameSounds sound = new GameSounds();
    public static GameClock clock = new GameClock();
   
    public static int sizeX, sizeY; //size of window
    protected BufferedImage gameImg; //image of game
    
    //ArrayLists to keep track of different types of game objects
    protected static ArrayList<BackgroundObject> backgrounds = new ArrayList<BackgroundObject>();
    protected static ArrayList<PlayerShip> players = new ArrayList<PlayerShip>();
    protected static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    protected static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    protected static ArrayList<BackgroundObject> explosions = new ArrayList<BackgroundObject>();
    protected static ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
    protected static ArrayList<InterfaceObject> uis = new ArrayList<InterfaceObject>();
    HighScoreWindow highScoreList;
    
	private static final long serialVersionUID = 1692137856541631175L;
	
	/**
	 * Load the sprite images.
	 */
    protected abstract void loadSprites();
    /**
     * Draws each frame of the game.
     * @param g The game graphics object
     */
    public abstract void paint(Graphics g);
    
    /**
     * Window for player name input after game ends
     */
    public abstract void playerNameInput();
    
    /**
     * Add an observer to the game clock.
     * @param o The observer to be added
     */
    public static void addClockObserver (Observer o) {
    	clock.addObserver(o);
    }
    /**
     * Remove an observer from the game clock.
     * @param o The observer to be removed
     */
    public static void removeClockObserver (Observer o) {
    	clock.deleteObserver(o);
    }
    /**
     * Set GameWorld speed.
     * @param speed The new speed
     */
    public static void setSpeed(Point speed){
    	GameWorld.speed = speed;
    }
    /**
     * Get GameWorld speed
     * @return The GameWorld speed
     */
    public static Point getSpeed(){
    	return new Point(GameWorld.speed);
    }
    /**
     * Creates a Graphics2D object to draw game frames.
     * @param w Width of object
     * @param h Height of object
     * @return The Graphics2D object
     */
    public Graphics2D createGraphics2D(int w, int h) {
        Graphics2D g2 = null;
        if (gameImg == null || gameImg.getWidth() != w || gameImg.getHeight() != h) {
            gameImg = (BufferedImage) createImage(w, h);
            
        }
        g2 = gameImg.createGraphics();
        g2.setBackground(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.clearRect(0, 0, w, h);
        return g2;
    }
    
    /**
     * Gets the sprite image.
     * @param fileName The file name of the image
     * @return The BufferedImage object from the file
     * @throws IOException If the file cannot be read
     */
    public BufferedImage getSprite(String fileName) throws IOException {
    	return (ImageIO.read(this.getClass().getResource(fileName)));    	
    }
    /**
     * Add background object(s) to the BackgroundObject array list
     * @param newBackground Background object(s) to be added
     */
    public static void addBackground (BackgroundObject...newBackground) {
    	for (BackgroundObject obj : newBackground)
    		backgrounds.add(obj);
    }
    /**
     * Remove background object(s) from the BackgroundObject array list
     * @param objects Background objects to be removed
     */
    public static void removeBackground(BackgroundObject...objects) {
    	for (BackgroundObject obj : objects)
    		backgrounds.remove(obj);
	}
    /**
     * Add PlayerShip object(s) to the PlayerShip array list
     * @param newPlayer PlayerShip object(s) to be added
     */
    public static void addPlayer (PlayerShip...newPlayer) {
    	for (PlayerShip obj : newPlayer){
    		players.add(obj);    		
    	}
    }
    /**
     * Remove PlayerShip object(s) from the PlayerShip array list
     * @param thePlayer The PlayerShip object(s) to be removed
     */
	public static void removePlayer(PlayerShip...thePlayer) {
		for (PlayerShip obj : thePlayer){
			players.remove(obj);
			GameWorld.clock.deleteObserver(obj);
		}
	}
	/**
     * Add Enemy object(s) to the enemies array list
     * @param newEnemy Enemy object(s) to be added
     */
    public static void addEnemy (Enemy...newEnemy) {
    	for (Enemy obj : newEnemy){
    		enemies.add(obj);
    	}
    }
    /**
     * Remove enemy object(s) from the enemies array list
     * @param theEnemy Enemy object(s) to be removed
     */
    public static void removeEnemy(Enemy...theEnemy) {
    	for (Enemy enemy : theEnemy) {
    		enemies.remove(enemy);
    		GameWorld.clock.deleteObserver(enemy);
    	}
	}
    /**
     * Add new bullet object(s) to the bullets array list
     * @param newBullet The new bullet(s) to be added
     */
    public static void addBullet (Bullet...newBullet) {
    	for (Bullet obj : newBullet) {
    		bullets.add(obj);
    	}
    }
    /**
     * Remove bullet object(s) from the bullet array list
     * @param theBullet The bullet(s) to be removed
     */
    public static void removeBullet(Bullet...theBullet) {
    	for (Bullet bullet : theBullet) {
    		bullets.remove(bullet);
    		GameWorld.clock.deleteObserver(bullet);
    	}
	}
    /**
     * Add new explosion object(s) to the explosion array list
     * @param newExplosion The explosion object(s) to be added
     */
    public static void addExplosion (BackgroundObject...newExplosion) {
    	for (BackgroundObject expl : newExplosion) {
    		explosions.add(expl);
    		GameWorld.clock.addObserver(expl);
    	}
    }
   /**
    * Remove explosion object(s) from the explosion array list
    * @param theExplosion The explosion object(s) to be removed
    */
    public static void removeExplosion (BackgroundObject...theExplosion) {
    	for (BackgroundObject expl : theExplosion) {
    		explosions.remove(expl);
    		GameWorld.clock.deleteObserver(expl);
    	}
    }
    /**
     * Add powerUp object(s) to the powerUp array list
     * @param powerUp The powerUp object(s) to be added
     */
	public static void addPowerUp(PowerUp...powerUp) {
		for (PowerUp pow : powerUp)	{
			powerUps.add(pow);
		}
	}
	/**
	 * Remove powerUp object(s) from the powerUp array list
	 * @param thePow The powerUp object(s) to be removed
	 */
	public static void removePowerUp(PowerUp...thePow) {
		for (PowerUp pow : thePow) {
			powerUps.remove(pow);
			GameWorld.clock.deleteObserver(pow);
		}
	}
	/**
	 * Add Ui object(s) to the ui array list
	 * @param obj The Ui object(s) to be added
	 */
	public static void addUi(InterfaceObject...obj) {
		for (InterfaceObject ui : obj)	{
			uis.add(ui);
		}
	}
	/**
	 * Remove Ui object(s) from the ui array list
	 * @param obj The Ui object(s) to be removed
	 */
	public static void removeUi(InterfaceObject...obj) {
		for (InterfaceObject ui : obj) {
			uis.remove(ui);
		}
	}
	/**
	 * Gets the game clock frame number
	 * @return The frame number of the game clock
	 */
	public int getFrameNumber(){
    	return clock.getFrame();
    }
	/**
	 * Get the current game time in milliseconds
	 * @return The time since game started, in milliseconds
	 */
    public int getTime(){
    	return clock.getTime();
    }
    /**
     * For iterating through the background object array list
     * @return A ListIterator for background objects
     */
    public ListIterator<BackgroundObject> getBackgroundObjects(){
    	return backgrounds.listIterator();
    }
    /**
     * For iterating through the bullet array list
     * @return A ListIterator for bullet objects
     */
    public ListIterator<Bullet> getBullets(){
    	return bullets.listIterator();
    }
    /**
     * For iterating through the explosion array list
     * @return A ListIterator for explosion objects
     */
    public ListIterator<BackgroundObject> getExplosions() {
		return explosions.listIterator();
	}
    
    /**
     * For iterating through the PlayerShip array list
     * @return A ListIterator for PlayerShip objects
     */
    public ListIterator<PlayerShip> getPlayers(){
    	return players.listIterator();
    }
    /**
     * For iterating through the enemy array list
     * @return A listIterator for enemy objects
     */
    public ListIterator<Enemy> getEnemies(){
    	return enemies.listIterator();
    }
    /**
     * For iterating through the powerUp array list
     * @return A ListIterator for powerUp objects
     */
    public ListIterator<PowerUp> getPowerUps(){
    	return powerUps.listIterator();
    }
    /**
     * For iterating through the interface array list
     * @return A ListIterator for interface objects
     */
    public ListIterator<InterfaceObject> getUis(){
    	return uis.listIterator();
    }
    /**
     * Sets the dimension of game window
     * @param w Width of game window in pixels
     * @param h Height of game window in pixels
     */
    public void setDimensions(int w, int h){
    	sizeX = w;
    	sizeY = h;
    	this.setPreferredSize(new Dimension(sizeX, sizeY));
    }
    
    /**
     * Detects collision between player and background objects. Once a collision is detected, it repositions the player.
     */
    public void playerBackgroundCollision() {
    	ListIterator <PlayerShip> iterator1 = getPlayers();
    	ListIterator <BackgroundObject> iterator2;
    	PlayerShip player;
    	BackgroundObject obj;
    	while(iterator1.hasNext()) { // iterate through background objects
        	player = iterator1.next();
        	iterator2 = getBackgroundObjects();
        	while(iterator2.hasNext()) { //detects and resolves player-background collision
        		obj = iterator2.next();
        		if (player.collision(obj))
        			player.getMotion().collisionReposition(obj);
        	}
    	}
    }
    
    /**
     * Detects collision between two players. Once a collision is detected, it repositions the player based on who is moving.
     *  
     */
    public void playerPlayerCollision() {
    	for (int i=0; i<players.size() && !players.get(i).isDead(); i++){ //detects and resolves player-player collision
        	for (int j=i+1; j<players.size() && !players.get(j).isDead(); j++) {
        		if (players.get(i).collision(players.get(j))) {
        			if (players.get(i).isMoving()) {
        				if (!players.get(j).isMoving()) {
        					players.get(i).getMotion().collisionReposition(players.get(j));
        				}
        				else {
        					int r = generator.nextInt();
        					if (r > 0)
        						players.get(i).getMotion().collisionReposition(players.get(j));
        					else
        						players.get(j).getMotion().collisionReposition(players.get(i));
        				}
        			}
        			else {
        				players.get(j).getMotion().collisionReposition(players.get(i));
        			}
        		}
        	}
        }
    }
    /**
     * Detects collision between player and enemy objects. Once a collision is detected, it simply repositions the player.
     */
    public void playerEnemyCollision() {
    	ListIterator <PlayerShip> iterator1 = getPlayers();
    	ListIterator <Enemy> iterator2;
    	PlayerShip player;
    	Enemy enemy;
    	while(iterator1.hasNext()) { // iterate through background objects
        	player = iterator1.next();
        	iterator2 = getEnemies();
        	while(iterator2.hasNext()) { //detects and resolves player-background collision
        		enemy = iterator2.next();
        		if (player.collision(enemy)){
        			player.getMotion().collisionReposition(enemy);
        		}        			
        	}
    	}
    }
    
    /**
     * Detects collision between player and PowerUp objects. Once a collision is detected,
     * the player gets the weapon from the PowerUp object, and the PowerUp object dissapears.
     */
    public void playerPowerUpCollision () {
    	ListIterator <PlayerShip> iterator1 = getPlayers();
    	ListIterator <PowerUp> iterator2;
    	PlayerShip player;
    	PowerUp powerUp;
    	while(iterator1.hasNext()) { // iterate through background objects
        	player = iterator1.next();
        	iterator2 = getPowerUps();
        	while(iterator2.hasNext()) { //detects and resolves player-background collision
        		powerUp = iterator2.next();
        		if (player.collision(powerUp)){
        			player.getWeapon().dispose();
        			player.setWeapon(powerUp.getWeapon());
        			powerUp.die();
        		}        			
        	}
    	}
    }
    
    /**
     * Detects collision between bullet and background objects. Generate explosion upon collision,
     * damage the ground object, and destroys the bullet
     */
    public void bulletBackgroundCollision() {
    	ListIterator<Bullet> iterator1= getBullets();
    	ListIterator<BackgroundObject> iterator2;
    	Bullet bullet;
    	BackgroundObject obj;
    	while (iterator1.hasNext()) {
    		bullet = iterator1.next();
    		iterator2 = getBackgroundObjects();
    		while (iterator2.hasNext()) {
    			obj = iterator2.next();
    			if (bullet.collision(obj) && !bullet.getOwner().equals(obj)) {
    				Point explosionPoint = bullet.getLocationPoint();
    				explosionPoint.translate(-16, -16);
    				GameWorld.addExplosion(new SmallExplosion(explosionPoint));
    				obj.damage(bullet.getStrength());
    				bullet.die();
    				break;
    			}
    		}
    	}
    }
    /**
     * Detects collision between bullet and players. Generate explosion upon collision, damage the player object only if 
     * (a) the palyer is not respawning; (b) the player is not the owner of the bullet.
     * Then destroys the bullet.
     */
    public void bulletPlayerCollision() {
    	ListIterator<Bullet> iterator1= getBullets();
    	ListIterator<PlayerShip> iterator2;
    	Bullet bullet;
    	PlayerShip player;
    	while (iterator1.hasNext()) {
    		bullet = iterator1.next();
    		iterator2 = getPlayers();
    		while (iterator2.hasNext()) {
    			player = iterator2.next();
    			if (!player.isRespawning() && bullet.collision(player) && !(bullet.getOwner().equals(player))) {
    				((PlayerShip)bullet.getOwner()).addScore(bullet.getStrength());
    				GameWorld.addExplosion(new SmallExplosion(bullet.getLocationPoint()));
    				player.damage(bullet.getStrength());
    				bullet.die();
    				break;
    			}
    		}
    	}
    }
    /**
     * Draw background objects
     * @param g The Graphics object to draw on, created from game image
     * @param obs The image observer
     */
    public void drawBackgroundObjects(Graphics g, ImageObserver obs) {
    	ListIterator<BackgroundObject> iterator = getBackgroundObjects();
    	while (iterator.hasNext()) {
    		iterator.next().draw(g, obs);
    	}
    }
    /**
     * Draw PlayerShip objects
     * @param g The Graphics object to draw on, created from game image
     * @param obs The image observer
     */
    public void drawPlayers(Graphics g, ImageObserver obs) {
    	ListIterator<PlayerShip> iterator = getPlayers();
    	while (iterator.hasNext()) {
    		iterator.next().draw(g, obs);
    	}
    }
    /**
     * Draw Enemy objects
     * @param g The Graphics object to draw on, created from game image
     * @param obs The image observer
     */
    public void drawEnemies(Graphics g, ImageObserver obs) {
    	ListIterator<Enemy> iterator = getEnemies();
    	while (iterator.hasNext()) {
    	}
    }
    /**
     * Draw Bullet objects
     * @param g The Graphics object to draw on, created from game image
     * @param obs The image observer
     */
    public void drawBullets(Graphics g, ImageObserver obs) {
    	ListIterator<Bullet> iterator = getBullets();
    	while (iterator.hasNext()) {
    		iterator.next().draw(g, obs);
    	}
    }
    /**
     * Draw Explosion objects
     * @param g The Graphics object to draw on, created from game image
     * @param obs The image observer
     */
    public void drawExplosions(Graphics g, ImageObserver obs) {
    	ListIterator<BackgroundObject> iterator = getExplosions();
    	while (iterator.hasNext()) {
    		iterator.next().draw(g, obs);
    	}
    }
    /**
     * Draw PowerUp objects
     * @param g The Graphics object to draw on, created from game image
     * @param obs The image observer
     */
	public void drawPowerUps(Graphics g, ImageObserver obs) {
    	ListIterator<PowerUp> iterator = getPowerUps();
    	while (iterator.hasNext()) {
    		iterator.next().draw(g, obs);
    	}
    }
	/**
     * Draw Ui objects
     * @param g The Graphics object to draw on, created from game image
     * @param obs The image observer
     */
	public void drawUis(Graphics g, ImageObserver obs) {
    	ListIterator<InterfaceObject> iterator = getUis();
    	while (iterator.hasNext()) {
    		iterator.next().draw(g, obs);
    	}
    }
	/**
	 * Displays the high score list. Implementation is delegated to sublcasses
	 */
	public abstract void showHighScore();
	/**
	 * Initializes the game
	 */
	public abstract void initGame();
	/**
	 * Displays the help information window. Implementation is delegated to subclasses
	 */
	public abstract void showHelp();
	
}
