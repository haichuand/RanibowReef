package game2D;


import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *  Game sounds are played using JLayer library
 */
public class GameSounds {
    public static final GameSounds sounds = new GameSounds();
    
    /**
     * Play the given sound file in continuous loops.
     * @param filename The sound file name
     * @return The Clip object for the sound file
     */
    public static Clip playLoop(String filename)
    {
    	
        try {
        	Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(GameWorld.class.getResource(filename)));
			new Thread() {
	    		public void run() {
	    			clip.loop(Clip.LOOP_CONTINUOUSLY);
	    		}        		
	    	}.start();
	    	return clip;
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
    
    /**
     * Play the sound clip.
     * @param filename The sound clip name
     */
    public static void playClip(String filename)
    {
    	Thread playThread = null;
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(GameWorld.class.getResource(filename)));
            playThread = new Thread() {
            	public void run() {
            		clip.loop(0);
            	}
            };
            playThread.start();
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }
    
    /**
     * Creates a new thread to start playing the sound file.
     * @param filename The sound file to be played
     */
    public static void play(final String filename)
    {
    	try {
            // Play now. 
    		new Thread() {
                public void run() {
                	try { 
                    AudioInputStream in= AudioSystem.getAudioInputStream(GameWorld.class.getResource(filename));
                    AudioInputStream din = null;
                    AudioFormat baseFormat = in.getFormat();
                    AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
            				                                      baseFormat.getSampleRate(),
            				                                      16,
            				                                      baseFormat.getChannels(),
            				                                      baseFormat.getChannels() * 2,
            				                                      baseFormat.getSampleRate(),
            				                                      false);
                    din = AudioSystem.getAudioInputStream(decodedFormat, in);
                    rawplay(decodedFormat, din);
                    in.close();}
                    catch (Exception e) { e.printStackTrace(); }
                }
            }.start();
          } catch (Exception e)
            {
                System.out.println("GameSounds.play(): error playing file" + filename);
                e.printStackTrace();
            }
    } 

    private static void rawplay(AudioFormat targetFormat, AudioInputStream din) throws IOException,                                                                                                LineUnavailableException
    {
      byte[] data = new byte[4096];
      SourceDataLine line = getLine(targetFormat); 
      if (line != null)
      {
        // Start
        line.start();
        int nBytesRead = 0, nBytesWritten = 0;
        while (nBytesRead != -1)
        {
            nBytesRead = din.read(data, 0, data.length);
            if (nBytesRead != -1) nBytesWritten = line.write(data, 0, nBytesRead);
        }
        // Stop
        line.drain();
        line.stop();
        line.close();
        din.close();
      } 
    }

    private static SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException
    {
      SourceDataLine res = null;
      DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
      res = (SourceDataLine) AudioSystem.getLine(info);
      res.open(audioFormat);
      return res;
    } 
}