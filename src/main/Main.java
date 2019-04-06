package main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

import audio.AudioClip;
import audio.AudioPlayer;
import audio.AudioTrack;
import ui.MainWindow;
import utils.AnimatedGifEncoder;
import utils.FFmpeg;
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
			//audioTest();
			//audioTest2();
			//gifTest();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static void audioTest()
	{
		AudioTrack a = new AudioTrack(48000);
		
		try
		{
			
			/*NamedPipe pipe = new NamedPipe("test");
			pipe.waitForConnect();
			System.out.println("连接成功！");
			
			FileInputStream fis = new FileInputStream("C:\\Users\\Admin\\Desktop\\battle_starand_16.wav");
			FileOutputStream out = new FileOutputStream("E:\\a.wav");
			byte[] buffer = new byte[fis.available()];
			byte[] b = new byte[512];
			//fis.read(buffer);
			ByteArrayOutputStream input = new ByteArrayOutputStream();
			
			while(pipe.read(b,b.length))
			{
				input.write(b);
			}
			
			pipe.close();
			out.write(input.toByteArray());
			input.close();
			fis.close();
			out.close();*/
			
			FFmpeg f = new FFmpeg();
			//f.start(arg);
			
			//C:\\Users\\Administrator\\Desktop\\a\\battle_starand_16.wav
			byte[] buffer = f.getAudioDataFromFile(Resource.getResFolder() + "\\voices\\asriel.wav", 48000);
			AudioClip clip = new AudioClip(buffer,48000);
			AudioClip clip2 = new AudioClip(buffer, 48000);
			short[] data = clip.getDatas();
			
			clip.setDatas(data);
			clip.updateRawDatas();
			clip.setPosition(0);
			clip2.setPosition((float)(clip.getPositionInSec() + 0.1));
			
			System.out.println(clip2.getPosition());
			
			a.addAudioClip(clip);
			a.addAudioClip(clip2);
			
			buffer = a.toRawData();
			
			System.out.println("Get data length=" + buffer.length);
			FileOutputStream out = new FileOutputStream("E:\\test.pcm");
			out.write(buffer);
			out.close();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static void audioTest2() throws Exception
	{
		FFmpeg ffmpeg = new FFmpeg();
		String path = "C:\\Users\\Administrator\\Desktop\\Dr-Music\\field_of_hopes_16.wav";
		//AudioPlayer player = new AudioPlayer("C:\\Users\\Administrator\\Desktop\\Dr-Music\\field_of_hopes_16.wav");
		AudioPlayer player = new AudioPlayer(ffmpeg.getAudioDataFromFile(path, 48000),48000);
		//player.close();
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
}
