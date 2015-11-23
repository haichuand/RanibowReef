package rainbowReef;

import game2D.GameWorld;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReefLevel {
	int start;
	Integer position;
	private BufferedReader levelReader;
	private int wallWidth = GameWorld.sprites.get("wall").getWidth();
	private int wallHeight = GameWorld.sprites.get("wall").getHeight();
	private int blockWidth = GameWorld.sprites.get("block1").getWidth();
	private int blockHeight = GameWorld.sprites.get("block1").getHeight();
	/**
	 * @param filename Name and path for the text file that includes reef arena setup information
	 */
	public ReefLevel(String filename) {
		try {
			levelReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(filename)));
		} catch (Exception e) {
			System.out.println("public ReefLevel(" + filename + "): Error reading " + filename);
			System.exit(1);
		}
	}
	/**
	 * Load the text file and construct the game arena based on characters in the file.
	 */
	public void load(){
		GameWorld.addBackground(new BorderWall(new Point(0,0), 1, GameWorld.sizeY/wallHeight));
		GameWorld.addBackground(new BorderWall(new Point(wallWidth,0), GameWorld.sizeX/wallWidth-2, 1));
		GameWorld.addBackground(new BorderWall(new Point(GameWorld.sizeX-wallWidth,0), 1, GameWorld.sizeY/wallHeight));
		try {
			String line = levelReader.readLine();
			int h=0;
			while(line!=null){
				for(int i = 0, n = line.length(); i < n ; i++) { 
				    char c = line.charAt(i); 
				    int x = i*blockWidth + wallWidth;
				    int y = h*blockHeight + wallHeight;
				    if(c=='0')				    	
				    	GameWorld.addBackground(new BlockSolid(new Point(x, y)));
				   
				    else if(c=='1')
				    	GameWorld.addBackground(new Block1(new Point(x, y)));
				    
				    else if(c=='2')
				    	GameWorld.addBackground(new Block2(new Point(x, y)));
				    
				    else if(c=='3')
				    	GameWorld.addBackground(new Block3(new Point(x, y)));
				    
				    else if(c=='4')
				    	GameWorld.addBackground(new Block4(new Point(x, y)));
				    
				    else if(c=='5')
				    	GameWorld.addBackground(new Block5(new Point(x, y)));
				    
				    else if(c=='6')
				    	GameWorld.addBackground(new Block6(new Point(x, y)));
				    
				    else if(c=='7')
				    	GameWorld.addBackground(new Block7(new Point(x, y)));
				    
				    else if(c=='8')
				    	GameWorld.addBackground(new BlockLife(new Point(x, y)));
				    
				    else if(c=='9')
				    	GameWorld.addBackground(new BlockSplit(new Point(x, y)));
				    
				    else if(c=='B')
				    	ReefWorld.biglegList.add(new Bigleg(new Point(x, y)));
				    
				    else if(c=='b')
				    	ReefWorld.biglegList.add(new BiglegSmall(new Point(x, y)));
				}
				h++;
				line = levelReader.readLine();
			}
			levelReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//add pop, katch and ui
		ReefWorld.popList.add(new Pop(new Point(GameWorld.sizeX/2-10, GameWorld.sizeY*2/3), new Point2D.Double(1.5, -8)));
		ReefWorld.katch = new Katch();
		new Ui();
	}
}
