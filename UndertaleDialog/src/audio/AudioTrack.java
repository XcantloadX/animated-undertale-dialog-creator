package audio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import utils.Bytes;

public class AudioTrack
{
	private Clip clip;
	private AudioInputStream inputStream;
	private AudioFormat audioFormat;
	private SourceDataLine sourceDataLine;
	private ArrayList<AudioClip> audioClips;
	protected int sampleRate;
	
	public AudioTrack(int sampleRate)
	{
		this.sampleRate = sampleRate;
		audioClips = new ArrayList<AudioClip>();
	}
	
	public void play(String file)
	{
		try
		{
			inputStream = AudioSystem.getAudioInputStream(new File(file));
			
			//获取音频格式
			audioFormat = inputStream.getFormat();
			System.out.println("Audio Format: " + audioFormat.toString());
			
			//信息
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat,AudioSystem.NOT_SPECIFIED);
			
			sourceDataLine = (SourceDataLine)AudioSystem.getLine(info);//
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();
			
			byte[] buffer = new byte[(int) inputStream.getFrameLength()];
			int[] data = new int[buffer.length / 2];
			byte[] newBuffer = new byte[(int) inputStream.getFrameLength()];
			inputStream.read(buffer, 0, buffer.length);
			inputStream = AudioSystem.getAudioInputStream(new File(file));
			
			ByteArrayInputStream byteInput = new ByteArrayInputStream(newBuffer);
			byte[] temp = new byte[64];
			while (byteInput.read(temp,0,temp.length) != -1)
			{
				sourceDataLine.write(temp, 0, temp.length);
				//System.out.println(byteInput.available() + "|" + buffer.length);
			}
			

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void addAudioClip(AudioClip clip) throws Exception
	{
		if(clip.getSampleRate() != sampleRate)
			throw new Exception("AudioClip's sample rate doesn't match this AudioTrack's sample rate.");
		audioClips.add(clip);
	}
	
	public byte[] toRawData()
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		//计算数据大小
		//获取最大音频长度
		int max = 0;
		for (int i = 0; i < audioClips.size(); i++)
		{
			int num = audioClips.get(i).getPosition() + audioClips.get(i).getDatas().length;
			if(num > max)
				max = num;
		}
		
		//每声道样本数 = 音频数据大小 / 每样本大小(即BitsPerSample/8) / 通道数
		//音频数据大小 =  样本数 * 每样本大小(即BitsPerSample/8)
		//数据量=(采样频率×采样位数×声道数×时间)/8
		//BitsPerSample = 16位
		short[] temp = new short[max];
		
		int current = 0;
		for (int i = 0; i < audioClips.size(); i++)
		{
			short[] buffer = audioClips.get(i).getDatas();
			if(audioClips.get(i).getPosition() == -1)
			{
				System.arraycopy(buffer, 0, temp, current, buffer.length);
				current += buffer.length;
			}
			else
			{
				System.arraycopy(buffer, 0, temp, audioClips.get(i).getPosition(), buffer.length);
				current = audioClips.get(i).getPosition() + buffer.length;
			}
		}
		
		return Bytes.shortToBytes(temp);
	}
	
	public void close()
	{
		try
		{
			inputStream.close();
			sourceDataLine.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private short mixAudio(short data1, short data2)
	{
		long num = data1 + data2;
		if(num > Short.MAX_VALUE)
			return Short.MAX_VALUE;
		else if(num < Short.MIN_VALUE)
			return Short.MIN_VALUE;
		else 
			return (short)num;
	}
}
