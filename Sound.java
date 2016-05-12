package com.welliver.sidescroller;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	
	private AudioInputStream stream;
	public Clip clip;
	public boolean playing = false;
	private Rainmaker ss;
	private String filename;
	FloatControl gainControl;
	
	public Sound(String filename, Rainmaker ss){
		this.filename = filename;
		this.ss = ss;
		try {
			stream = AudioSystem.getAudioInputStream(new File(filename).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.close();
			clip.drain();
			clip.flush();
			clip.open(stream);
			gainControl = 
				    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void play(){
		try{
			clip.start();
			//gainControl.setValue(10.0f);
			//clip.loop(0);
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	public void stop(){
		try{
			clip.stop();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void restart(){
		try{
			clip.stop();
			clip.flush();
			clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void setVolume(int newVolume){
		//gainControl.setValue(10.0f);
	}
	public void resetSong(File file){
		try {
			stream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.close();
			clip.drain();
			clip.flush();
			System.gc();
			clip.open(stream);
			gainControl = 
				    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
