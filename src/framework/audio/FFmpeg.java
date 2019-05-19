package framework.audio;

import java.awt.List;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Arrays;

import framework.utils.NamedPipe;
import maker.main.Main;

public class FFmpeg
{
	private String exePath;
	public static FFmpeg instance;
	public static boolean debugOutput = true;
	
	public FFmpeg()
	{
		exePath = System.getProperty("user.dir") + "\\tools\\ffmpeg.exe";
		if(!new File(exePath).exists())
			System.err.println("File not found: " + exePath);
	}
	
	
	/**
	 * 以指定参数阻塞式启动 FFmpeg
	 * @param args 启动参数
	 */
	public void start(String[] args)
	{
		String[] a = new String[]{exePath};
		ArrayList<String> list = new ArrayList<String>();
		
		list.add(exePath);
		list.addAll(Arrays.asList(args));
		list.add("-y");//不经过询问直接覆盖文件
		
		//输出运行的参数
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++)
		{
			sb.append(list.get(i) + " ");
		}
		System.out.println("[FFmpeg]Args: " + sb.toString());
		
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
			
			//消息换行
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
				System.err.println("[FFmpeg]Some error happened.");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 以指定参数阻塞式启动 FFmpeg
	 * @param args 启动参数
	 */
	public void start(ArrayList<String> args)
	{
		String[] s = new String[args.size()];
		args.toArray(s);
		start(s);
	}
	
	/**
	 * 以指定参数异步启动 FFmpeg
	 * @param args 启动参数
	 */
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
		NamedPipe pipe = null;
		
		try
		{
			pipe = new NamedPipe("audio_output");
			
			//启动参数
			ArrayList<String> args = new ArrayList<String>(15);
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
			byte[] temp = new byte[64];
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
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			//关闭
			try
			{
				
				if(pipe != null)
					pipe.close();
				output.close();//一定要关闭输出流！不然会造成奇怪的问题(有噪音)！
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return output.toByteArray();
	}
	
	//返回16位的原始音频数据
	public byte[] getAudioDataFromFile_(String filePath, int outputSampleRate)
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		File file = null;
		byte[] buffer = null;
		
		try
		{
			//ffmpeg.exe -i C:\\a.mp3 -f s16le -ar 48000 -c:a pcm_s16le C:\\out.bin
			file = File.createTempFile("audio", "bin");
			
			//启动参数
			ArrayList<String> args = new ArrayList<String>(15);
			args.add("-i");
			args.add(filePath);
			args.add("-f");//输出格式
			args.add("s16le");//输出格式(pcm 16 bit 小端模式)
			args.add("-ar");//音频采样率
			args.add(String.valueOf(outputSampleRate));//音频采样率
			args.add("-c:a");//编码器格式
			args.add("pcm_s16le");
			args.add(file.toString());
			
			//启动进程
			start(args);
			
			//读取
			FileInputStream input = new FileInputStream(file);
			buffer = new byte[input.available()];
			input.read(buffer);
			input.close();
			input = null;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			//关闭
			try
			{
				output.close();
				output = null;
				
				file.deleteOnExit();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return buffer;
	}
	
	/**
	 * 导出音频数据(有符号16位小端模式)为 Wave 文件(*.wav)
	 * @param buffer 要导出的音频数据
	 * @param sampleRate 音频采样率
	 * @param sampleSizeInBits 音频位数(8,16,32位)
	 * @param channels 音频通道数
	 * @param filePath 导出到的文件路径
	 */
	public void exportToWav(byte[] buffer, int sampleRate, int sampleSizeInBits, int channels, String filePath)
	{
		//ffmpeg.exe -f s16le -ar 48000 -acodec pcm_s16le -ac 2 -i C:\test.pcm out.wav -y
		
		try
		{
			NamedPipe pipe = new NamedPipe("audio_output");
			
			//参数
			ArrayList<String> args = new ArrayList<>(15);
			//强制设置音频格式
			args.add("-f");
			args.add("s16le");//有符号16位小端模式
			//设置采样率
			args.add("-ar");
			args.add(String.valueOf(sampleRate));
			//设置音频编解码器
			args.add("-acodec");
			args.add("pcm_s16le");
			//设置通道数
			args.add("-ac");
			args.add(String.valueOf(channels));
			//设置输入文件
			args.add("-i");
			args.add(pipe.getFullPath());
			//设置输出文件
			args.add(filePath);
			
			//启动ffmpeg
			this.startAsync(args);
			
			//等待管道被连接
			pipe.waitForConnect();
			
			//发送数据
			pipe.write(buffer);
			pipe.close();//注意关闭管道！！！
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
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
