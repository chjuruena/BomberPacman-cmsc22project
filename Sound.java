import javax.sound.sampled.*;

public class Sound{

	private Clip clip;

	public static Sound menu = new Sound("music/menu.wav");
	public static Sound battle1 = new Sound("music/battle1.wav");
	public static Sound dead = new Sound("music/die.mid");
	public static Sound explosion = new Sound("music/explosion.mid");
	public static Sound win = new Sound("music/win.wav");
	public static Sound bell = new Sound("music/bell.wav");

	public Sound(String fileName){

		try{
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName)); // reads audio from external audio file
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch(Exception e){
			e.printStackTrace();
		}

	}

	public void play(){ // plays the clip until the sound ends
		try{

			if(clip != null){
				new Thread(){
					public void run(){
						synchronized(clip){
							clip.stop();
							clip.setFramePosition(0); // sets media sample frames. starts at 0 
							clip.start();
						}
					}
				}.start();
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void stop(){ // stops clip and ceases audio activity
		if(clip == null) return;
		clip.stop();
	}

	public void loop(){ // plays the clip and loops when it reaches the end
		try{

			if(clip != null){
				new Thread(){
					public void run(){
						synchronized(clip){
							clip.stop();
							clip.setFramePosition(0); //sets media sample frames. starts at 0
							clip.loop(Clip.LOOP_CONTINUOUSLY); // loops until interrupted
						}
					}
				}.start();
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}