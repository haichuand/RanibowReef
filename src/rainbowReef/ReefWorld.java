package rainbowReef;

import game2D.GameClock;
import game2D.GameSounds;
import game2D.GameWorld;
import game2D.objects.BackgroundObject;
import game2D.ui.HelpWindow;
import game2D.ui.StartMenu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Observable;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
/**
 * ReefWorld is the driving and controlling class for the Rainbow Reef game. The main loop goes as:
 * check if conditions met, such as player or enemy died, game won, etc.; clock ticks; update unit movement; detect and resolve collision; draw objects; sleep thread for certain time; loops back
 * @author Fudou
 */
public final class ReefWorld extends GameWorld {
	private static final long serialVersionUID = 1576104258785139911L;
	private static StartMenu reefStartMenu;
	static ArrayList<Pop> popList = new ArrayList<Pop>();
	static ArrayList<Bigleg> biglegList = new ArrayList<Bigleg>();
	static Katch katch;
	private static JFrame gameFrame; //top level game container
	private static Thread gameThread;
	private BufferedImage gameBackground; //background images of game and high score windows
	private ListIterator<?> it1, it2; //for arraylist iteration
	private int currrentLevel; //current level of game
	private final static int MAX_LEVEL = 3; //maximum level of game
	private Rectangle intersect; //intersection rectangle from collision, used to calculate bounce direction
	int playerScore; //total score of player
	private boolean addNewPop; //flag to add new pop to game
	static ReefHighScore highScore; //object for displaying high score list
	static ReefNameInput inputName; //object for player name input
	static HelpWindow helpWindow;
	private static Clip musicClip; //for background music playback
	
	private ReefWorld () {
		this.setDimensions(800,600); //sets game window size
		this.setFocusable(true);
		currrentLevel = 0;
	}
	/**
	 * Gets the instance of ReefWorld game
	 * @return The instance of ReefWorld game
	 */
	public static ReefWorld getInstance(){
		return (ReefWorld)game;
	}
	/**
	 * Function for loading image resources
	 * */
    @Override
    protected void loadSprites() {
    	try {
    		
    		sprites.put("background1", getSprite("Resources/Background1.bmp"));
    	    
    	    sprites.put("background2", getSprite("Resources/Background2.bmp"));
    	        	    
    	    sprites.put("bigleg1", getSprite("Resources/Bigleg1.png"));
    	    sprites.put("bigleg2", getSprite("Resources/Bigleg2.png"));
    	    sprites.put("biglegSmall1", getSprite("Resources/Bigleg_small1.png"));
    	    sprites.put("biglegSmall2", getSprite("Resources/Bigleg_small2.png"));
    	    
    	    sprites.put("blockDouble", getSprite("Resources/Block_double.gif"));
    		sprites.put("blockLife", getSprite("Resources/Block_life.gif"));
    		sprites.put("blockSolid", getSprite("Resources/Block_solid.gif"));
    		sprites.put("blockSplit", getSprite("Resources/Block_split.gif"));
    		sprites.put("block1", getSprite("Resources/Block1.gif"));
    		sprites.put("block2", getSprite("Resources/Block2.gif"));
    		sprites.put("block3", getSprite("Resources/Block3.gif"));
    		sprites.put("block4", getSprite("Resources/Block4.gif"));
    		sprites.put("block5", getSprite("Resources/Block5.gif"));
    		sprites.put("block6", getSprite("Resources/Block6.gif"));
    		sprites.put("block7", getSprite("Resources/Block7.gif"));
    		sprites.put("congratulation", getSprite("Resources/Congratulation.gif"));
    		sprites.put("katch1", getSprite("Resources/Katch1.png"));
    		sprites.put("katch2", getSprite("Resources/Katch2.png"));
    		sprites.put("katchSmall", getSprite("Resources/Katch_small.png"));
    		for (int i=1; i<=45; i++) { //series of pop images for animation
    			String p = "pop" + i;
    			sprites.put(p, getSprite("Resources/Pop" + i +".png"));
    		}
    		sprites.put("wall", getSprite("Resources/Wall.gif"));
    		sprites.put("start", getSprite("Resources/Button_start.gif"));
    		sprites.put("help", getSprite("Resources/Button_help.gif"));
    		sprites.put("score", getSprite("Resources/Button_scores.gif"));
    		sprites.put("quit", getSprite("Resources/Button_quit.gif"));
    		sprites.put("title", getSprite("Resources/Title.gif"));
    		
    	}
    	catch (Exception e) {
    		System.out.println("ReefWorld.loadSprites(): error loading image file");
    		e.printStackTrace();
    		System.exit(0);
    	}
    }
	
	public static void main(String argv[]) {
		gameFrame = new JFrame("Reef Game");
		game = new ReefWorld();
		((ReefWorld)game).loadSprites();
		BufferedImage titleImg = GameWorld.sprites.get("title");
		reefStartMenu= new StartMenu(sizeX, sizeY, titleImg, game);
		highScore =  new ReefHighScore();
		helpWindow = new HelpWindow(sprites.get("background1"), "/rainbowReef/Resources/help.txt");
	    gameFrame.addWindowListener(new WindowAdapter() {
		    public void windowGainedFocus(WindowEvent e) {
		       reefStartMenu.requestFocusInWindow();
		    }
	    });
	    gameFrame.setContentPane(reefStartMenu);
	    gameFrame.pack();
	    gameFrame.setVisible(true);
	    gameFrame.setResizable(false);
	    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //Insets inset = gameFrame.getInsets();		
		//gameFrame.setSize(new Dimension(sizeX+inset.left+inset.right, sizeY+inset.top+inset.bottom)); //add inset margins to let frame matches component
		musicClip = GameSounds.playLoop("/rainbowReef/Resources/Music.wav");
	}
	/**
	 * Initialize the game for playing
	 */
	@Override
	public void initGame()	{
		musicClip.stop();
		gameFrame.setContentPane(game);
		gameFrame.pack();
		gameFrame.setVisible(true);
	    gameFrame.setResizable(false);
	    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.requestFocusInWindow();
	    playerScore = 0;
	    currrentLevel = 0;
	    levelUp(); //load first level
	    repaint();	    
	    gameThread = new Thread(this);
        gameThread.setPriority(Thread.MIN_PRIORITY);
        gameThread.start();
	}	
	 
	/**
	 * Main loop of game
	 */
	@Override
	public void run() {
		Thread me = Thread.currentThread();
        while (gameThread==me) {
        	if (popList.size() == 0){ //no pop life left. input player name, display high score and returns to start menu
        		playerNameInput();
        		gameFrame.setContentPane(reefStartMenu);
        		gameFrame.pack();
        		musicClip.start();
        		gameThread = null;
        	}
        	else if (biglegList.size()==0) { //all biglegs destroyed; advance to next level
        		clock = new GameClock();
        		levelUp();
        		repaint();
        		try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    break;
                }
        	}
        	else {
        		if (addNewPop) { //add a new pop because of split block hit
        			popList.add(new Pop(new Point(GameWorld.sizeX/2-10, GameWorld.sizeY*2/3), new Point2D.Double(-1.5, -7)));
        			addNewPop = false;
        		}
        		clock.tick();
            	//collision detection
            	popBackgroundCollision();
            	popBiglegCollision();
            	popKatchCollision();
                repaint();
        	}
          try {
                Thread.sleep(25); // pause a little to slow things down
            } catch (InterruptedException e) {
                break;
            }
        }
		
	}
	/**
	 * display window to accept player name input
	 */
	public void playerNameInput() {
		inputName = new ReefNameInput(ReefWorld.sprites.get("background2"), playerScore);
		inputName.display();
	}
	/**
	 * Advance game to the next level
	 */
	public void levelUp() {
		popList.clear();
		biglegList.clear();
		backgrounds.clear();
		uis.clear();
		currrentLevel++;
		if(currrentLevel > MAX_LEVEL) { //win game
			inputName = new ReefNameInput(ReefWorld.sprites.get("congratulation"), playerScore);
			inputName.display();
			gameFrame.setContentPane(reefStartMenu);
			gameFrame.pack();
    		musicClip.start();
    		gameThread = null;
		}
		else {
			String fileName = "Resources/level" + currrentLevel + ".txt";
			ReefLevel newLevel = new ReefLevel(fileName);
			newLevel.load();
		}
	}
	/**
	 * Paint background and objects in the game. Called by repaint() in run().
	 */
	@Override	
	public void paint(Graphics g) {
		Graphics2D g2 = this.createGraphics2D(sizeX, sizeY);
		gameBackground = GameWorld.sprites.get("background1");
		g2.drawImage(gameBackground, 0, 0, sizeX, sizeY, this);
		drawBackgroundObjects(g2, this);
		drawBiglegs(g2, this);
		drawPops(g2, this);
		drawKatch(g2, this);
		drawUis(g2, this);
		g2.dispose();
		g.drawImage(gameImg, 0, 0, this);
	}
	/**
	 * Draw bouncing Pops
	 * @param g The graphics object to draw on
	 * @param ob
	 */
	private void drawPops(Graphics g, ImageObserver ob){
		for (int i=0; i<popList.size(); i++)
			popList.get(i).draw(g, ob);		
	}
	/**
	 * Draw Biglegs
	 * @param g The graphics object to draw on
	 * @param ob
	 */
	private void drawBiglegs (Graphics g, ImageObserver ob){
		for (int i=0; i<biglegList.size(); i++) {
			biglegList.get(i).draw(g, ob);
		}			
	}
	/**
	 * Draw Katch
	 * @param g
	 * @param ob
	 */
	private void drawKatch(Graphics g, ImageObserver ob) {
		katch.draw(g, this);
	}
	/**
	 * Detects and resolves pop collision with background (wall and blocks)
	 */
	private void popBackgroundCollision() {
		Pop thePop;
		BackgroundObject obj;
		it1 = popList.listIterator();
		while (it1.hasNext()) {
			thePop = (Pop) it1.next();
			it2 = getBackgroundObjects();
			while (it2.hasNext()) {
				obj = (BackgroundObject) it2.next();
				intersect = thePop.popCollision(obj);
				if(intersect!=null) {
					thePop.motion.bounce(obj, intersect);
					if (obj instanceof BlockBreakable) {
						playerScore += ((BlockBreakable) obj).getScore();
						GameSounds.play("/rainbowReef/Resources/Sound_block.wav");
						if (obj instanceof BlockLife) {
							thePop.lives++;
						}
						else if (obj instanceof BlockSplit) {
							addNewPop = true; //set addNewPop flag. Cannot add here during list iteration
						}
						it2.remove();
						break; //MUST break after detecting collision with any block, otherwise unexpected behavior might occur when pop collides with two blocks simultaneously
					}
					else
						GameSounds.play("/rainbowReef/Resources/Sound_wall.wav");
				}
			}
			
		}		
	}
	/**
	 * Detects and resolves pop collision with Bigleg
	 */
	private void popBiglegCollision() {
		it1 = popList.listIterator();
		Pop thePop;
		Bigleg theBigleg;
		while (it1.hasNext()) {
			thePop = (Pop) it1.next();
			it2 = biglegList.listIterator();
			while (it2.hasNext()) {
				theBigleg = (Bigleg) it2.next();
				if (thePop.collision(theBigleg)) {
					GameSounds.play("/rainbowReef/Resources/Sound_bigleg.wav");
					playerScore += theBigleg.getScore();
					it2.remove();
				}
			}
		}
	}
	/**
	 * Detects and resolves pop collision with katch
	 */
	private void popKatchCollision() {
		it1 = popList.listIterator();
		Pop thePop;
		while (it1.hasNext()) {
			thePop = (Pop) it1.next();
			intersect = thePop.popCollision(katch);
			if (intersect!= null) {
				GameSounds.play("/rainbowReef/Resources/Sound_katch.wav");
				thePop.motion.bounce(katch, intersect);
			}
		}
	}
	@Override
	public void update(Observable arg0, Object arg1) {
	}
	/**
	 * Shows the high score list
	 */
	@Override
	public void showHighScore() {
		highScore.display();		
	}
	/**
	 * Shows the help information window
	 */
	@Override
	public void showHelp() {
		helpWindow.display();
	}

	
}
