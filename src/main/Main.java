package main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

import animation.AnimationCanvas;
import audio.*;
import ui.MainWindow;
import utils.AnimatedGifEncoder;
import utils.ImageUtil;
import utils.NamedPipe;
import utils.Resource;

public class Main
{

	public static void main(String[] args)
	{
		try
		{
			MainWindow m = new MainWindow();
			m.setVisible(true);
			
			System.out.println(System.getProperty("user.dir"));
			/*AnimationCanvas canvas = m.getCanvas();
			AudioTrack track = new AudioTrack(48000);
			canvas.renderAudio(track, AnimationCanvas.getFPS());
			byte[] buffer = PCMCoder.encoderShort(track.getSamples());
			new FFmpeg().exportToWav(buffer, 48000, 16, 2, "C:\\testing.wav");*/
			//audioTest();
			//gifTest();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static void audioTest() throws Exception
	{
		//String filePath = Resource.getResFolder() + "\\voices\\asriel.wav";
		String filePath = "D:\\1.wav";
		int sampleRate = 48000;
		
		FFmpeg f = new FFmpeg();
		byte[] buffer = f.getAudioDataFromFile(filePath, sampleRate);
		
		short[] samples = PCMCoder.decodeShort(buffer);
		
		
		AudioTrack track = new AudioTrack(sampleRate);
		AudioClip clip = new AudioClip(samples, sampleRate);
		AudioClip clip2 = clip.clone();
		track.addClip(clip);
		track.addClipRepeat(clip, 3, 0);
		
		System.out.println("[Test]Clip length = " + clip.getLengthInSec());
		System.out.println("[Test]Track length = " + track.getLengthInSec());
		
		byte[] b = PCMCoder.encoderShort(track.getSamples());
		write(b, "C:\\test.pcm");
		f.exportToWav(b, sampleRate, 16, 2, "C:\\test.wav");
		
		//测试播放
		AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sampleRate, 16, 2, 4, 48000, false);
		Clip clip3 = AudioSystem.getClip();
		clip3.open(format, b, 0, b.length);
		clip3.start();
		
		waitForExit();
		
		clip3.stop();
		clip3.close();
	}
	
	private static void gifTest() throws Exception
	{
		OutputStream out = new FileOutputStream("E:\\test.gif");

		AnimatedGifEncoder gif = new AnimatedGifEncoder();
		gif.start(out);
		gif.setRepeat(0);
		gif.addFrame(ImageUtil.getBufferedImage("E:\\1.png"));
		gif.addFrame(ImageUtil.getBufferedImage("E:\\2.png"));
		gif.setFrameRate(1f);
		
		gif.finish();
		
		out.close();
	}
	
	public static void write(byte[] buffer, String path) throws IOException
	{
		FileOutputStream stream = new FileOutputStream(path);
		stream.write(buffer);
		stream.close();
	}
	
	public static byte[] read(String path) throws IOException
	{
		FileInputStream input = new FileInputStream(path);
		byte[] buffer = new byte[input.available()];
		input.read(buffer);
		input.close();
		return buffer;
	}
	
	public static void waitForExit()
	{
		System.out.println("Enter q to exit.");
		try
		{
			while(!(System.in.read() == (int)'q'))
			{
				
			}
		} 
		catch (IOException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
