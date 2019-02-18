package main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

import audio.AudioClip;
import audio.AudioTrack;
import utils.FFmpeg;
import utils.NamedPipe;

public class Main
{

	public static void main(String[] args)
	{
		System.setProperty("user.dir", "C:\\Users\\Admin\\Desktop\\ut");
		MainWindow m = new MainWindow();
		m.setVisible(true);
		
		System.out.println(System.getProperty("user.dir"));
		test();
	}
	
	private static void test()
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
			
			byte[] buffer = f.getAudioDataFromFile("C:\\Users\\Admin\\Desktop\\a\\battle_starand_16.wav", 48000);
			AudioClip clip = new AudioClip(buffer,48000);
			AudioClip clip2 = new AudioClip(buffer, 48000);
			short[] data = clip.getDatas();
			
			clip.setDatas(data);
			clip.updateRawDatas();
			clip.setPosition(0);
			clip2.setPosition(10);
			
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
		
		//a.play(System.getProperty("user.dir") + "\\voices\\asriel.wav");
		//a.play("C:\\Users\\Admin\\Desktop\\battle_starand_16.wav");
		//a.play("C:\\Users\\Admin\\Desktop\\2.wav");
	}

}
