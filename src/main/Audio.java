package main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio
{
	private AudioInputStream inputStream;
	private Clip clip;
	
	public Audio(String audioPath)
	{
		try
		{
			inputStream = AudioSystem.getAudioInputStream(new File(audioPath));
			clip = AudioSystem.getClip();
			clip.open(inputStream);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	}
	
	public void play()
	{
		clip.start();
	}
	
	public void resetPosition()
	{
		clip.setFramePosition(0);
	}
}
