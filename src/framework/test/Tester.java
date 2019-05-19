package framework.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class Tester
{
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
