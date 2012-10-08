package gui.audio;
//source: http://codeidol.com/java/swing/Audio/Play-Non-Trivial-Audio/

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class AudioPlayer extends Object 
implements LineListener {
	File soundFile;
	PCMFilePlayer player;

	//FloatControl volume = (FloatControl) player.getLine().getControl(FloatControl.Type.MASTER_GAIN);
	
	public static void createPlayer (String filename) {
		File f = new File(filename);
		try {
			AudioPlayer s = new AudioPlayer (f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AudioPlayer (File f)
	throws LineUnavailableException, IOException,
	UnsupportedAudioFileException { 
		soundFile = f; 
		player = new PCMFilePlayer (soundFile);
		player.getLine().addLineListener (this);
	}
	
	public void start(){
		player.start();
	}
	
	public void stop(){
		player.stop();
	}
	
//	LineListener
	public void update (LineEvent le) {
		LineEvent.Type type = le.getType();
		if (type == LineEvent.Type.STOP) {
			player.line.close();
		} 
	} 
	
	public PCMFilePlayer player() {
		return player;
	}

	//public FloatControl volume() {
	//	return volume;
	//}
}