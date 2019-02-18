package utils;

import java.awt.List;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Arrays;

public class FFmpeg
{
	private String exePath;
	
	public FFmpeg() throws Exception
	{
		exePath = System.getProperty("user.dir") + "\\tools\\ffmpeg.exe";
		if(!new File(exePath).exists())
			throw new Exception("File not found: " + exePath);
	}
	
	/**
	 * 以指定参数启动FFmpeg
	 * @param args 启动参数
	 */
	public void start(String[] args)
	{
		String[] a = new String[]{exePath};
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> argsList = new ArrayList<String>(Arrays.asList(args));
		
		list.add(exePath);
		list.addAll(Arrays.asList(args));
		list.add("-y");//不经过询问直接覆盖文件
		
		try
		{
			ProcessBuilder builder = new ProcessBuilder(list);
			Process process = builder.start();
			InputStream is = process.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			is = process.getErrorStream();
			br = new BufferedReader(new InputStreamReader(is));
			System.out.println("[FFmpeg]Error Message: ");
			while (true)
			{
				String s = br.readLine();
				if(s == "" || s == null)
					break;
				System.out.println(br.readLine());
			}
			
			//输出提示消息换行
			System.out.println();
			
			is = process.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			System.out.println("[FFmpeg]Output Message: ");
			while (true)
			{
				String s = br.readLine();
				if(s == "" || s == null)
					break;
				System.out.println(br.readLine());
			}
			
			process.destroy();
			int exitCode = process.exitValue();
			System.out.println("[FFmpeg]Exit code: " + exitCode);
			if(exitCode != 0)
				System.out.println("[FFmpeg]Some error happened.");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void start(ArrayList<String> args)
	{
		String[] s = new String[args.size()];
		args.toArray(s);
		start(s);
	}
	
	public void startAsync(ArrayList<String> args)
	{
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				start(args);
			}
		});
		t.start();
	}
	
	
	public void createVideoFromImages()
	{
		
	}
	
	//返回16位的原始音频数据
	public byte[] getAudioDataFromFile(String filePath, int outputSampleRate)
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		
		try
		{
			NamedPipe pipe = new NamedPipe("audio_output");
			
			//启动参数
			ArrayList<String> args = new ArrayList<String>();
			args.add("-i");
			args.add(filePath);
			args.add("-f");//输出格式
			args.add("s16le");//输出格式(pcm 16 bit 小端模式)
			args.add("-ar");//音频采样率
			args.add(String.valueOf(outputSampleRate));//音频采样率
			args.add("-c:a");//编码器格式
			args.add("pcm_s16le");
			args.add(pipe.getFullPath());
			
			//启动进程
			startAsync(args);
			
			//从管道里读取数据
			pipe.waitForConnect();
			byte[] temp = new byte[1024];
			while (pipe.read(temp, temp.length))
			{
				try
				{
					output.write(temp);
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			
			//关闭管道
			pipe.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return output.toByteArray();
	}
	
	public String getFFmpegPath()
	{
		return exePath;
	}
	
	public void setFFmpegPath(String filePath) throws Exception
	{
		if(!new File(filePath).exists())
			throw new Exception("File not existed: " + filePath);
		exePath = filePath;
	}
}
