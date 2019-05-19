package maker.main;

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

import framework.audio.*;
import framework.utils.AnimatedGifEncoder;
import framework.utils.ImageUtil;
import framework.utils.NamedPipe;
import framework.utils.Resource;
import maker.dialog.AnimationCanvas;
import maker.ui.MainWindow;

public class Main
{

	public static void main(String[] args)
	{
		try
		{
			MainWindow m = new MainWindow();
			m.setVisible(true);
			
			System.out.println(System.getProperty("user.dir"));
			//gifTest();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
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
