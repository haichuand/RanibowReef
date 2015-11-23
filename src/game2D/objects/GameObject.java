package game2D.objects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Observer;

/**
 * GameObject is the base class of objects in the game world.
 * @author Fudou
 *
 */
abstract public class GameObject implements Observer {
	
	protected Rectangle location;
	private BufferedImage image;
	private ImageObserver observer;
	public boolean show;
	protected boolean isDead;
	/**
	 * 
	 * @param loc Location of the game object
	 * @param img Image of the game object
	 */
	public GameObject(Point loc, BufferedImage img){
		this.image = img;
		this.show=true;
		this.isDead = false;
		try{
			this.location = new Rectangle (loc.x, loc.y, img.getWidth(observer), img.getHeight(observer));
		}
	    catch (NullPointerException e){
	    	e.printStackTrace();
	    }
	}
	
	/**
	 * If the field show is true, draws the game object
	 * @param g The graphics object to draw on
	 * @param obs The image observer
	 */
    public void draw(Graphics g, ImageObserver obs) {
    	if(show)
    		g.drawImage(getImg(), location.x, location.y, obs);
    }
    
    /**
     * Sets the image of the game object
     * @param img The new image of the game object
     */
    public void setImage(BufferedImage img){
    	this.image = img;
		try{
	        location.width = img.getWidth(observer);
	        location.height = img.getHeight(observer);
		}
	    catch (NullPointerException e){
	    	e.printStackTrace();
	    }
    }
    /**
     * 
     * @return The X axis of location of the object
     */
    public int getX(){
    	return location.x;
    }
    /**
     * 
     * @return The y axis of the location of the object
     */
    public int getY(){
    	return location.y;
    }
    /**
     * 
     * @return The width of the object
     */
    public int getWidth(){
    	return location.width;
    }
    /**
     * 
     * @return The height of the object
     */
    public int getHeight(){
    	return location.height;
    }
    /**
     * Sets the location of the object
     * @param location The new location
     */
    public void setLocation(Point location){
    	this.location.x = location.x;
    	this.location.y = location.y;
    }
    /**
     * Sets the location of the object
     * @param x X coordinate of the object in pixels
     * @param y Y corridnate of the object in pixels
     */
    public void setLocation (int x, int y) {
    	location.x = x;
    	location.y = y;
    }
    /**
     * Gets the location of the object
     * @return The location of the object in Rectangle
     */
    public Rectangle getLocation(){
    	return this.location;
    }
    /**
     * Gets the location point of the object
     * @return The location of the object in Point
     */
    public Point getLocationPoint(){
    	return new Point(location.x, location.y);
    }
    /**
     * Moves the object a specified distance
     * @param dx The X coordinate distance to move
     * @param dy The Y coordinate distance to move
     */
    public void move(int dx, int dy){
    	location.translate(dx, dy);
    }
    /**
     * Detects if the game object collides with another game object.
     * It firsts check if the two location rectangles intersect. 
     * If yes, it then uses image bit masks to determine if any non-transparent parts overlap.
     * @param otherObj The other object to detect collision against
     * @return True if collision is detected, false otherwise.
     */
    public boolean collision(GameObject otherObj) {
    	Rectangle intersect = location.intersection(otherObj.getLocation());
        if(!intersect.isEmpty()){
        	//pixel-level collision detection
        	BufferedImage mask1 = this.getImg().getSubimage(intersect.x-this.location.x, intersect.y-this.location.y, intersect.width, intersect.height),
        			mask2 = otherObj.getImg().getSubimage(intersect.x-otherObj.location.x, intersect.y-otherObj.location.y, intersect.width, intersect.height);
        	for (int x=1; x<intersect.width; x++){
        		for (int y=1; y<intersect.height; y++) {
        			if ((mask1.getRGB(x,y) & mask2.getRGB(x,y) & 0xFF000000)!= 0x00000000)
        				return true;
        		}
        	}
        }
        return false;
    }
    /**
     * Hide the object so that it will not be displayed
     */
    public void hide(){
    	this.show = false;
    }
    /**
     * Show the object so that it will not be displayed
     */
    public void show(){
    	this.show = true;
    }
    
    /**
     * Test if the object is dead
     * @return True if isDead field is true, false otherwise.
     */
    public boolean isDead() {
    	return isDead;
    }

	
	/**
	 * Get the image of the game object
	 * @return The image object of the game object
	 */
	public BufferedImage getImg() {
		return image;
	}
}