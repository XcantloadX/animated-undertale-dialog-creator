package audio_old;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import utils.BytesUtil;

public class AudioPlayer
{
	private Clip clip;
	private AudioInputStream inputStream;
	private ByteArrayInputStream input;
	private AudioFormat audioFormat;
	private SourceDataLine sourceDataLine;
	private ArrayList<AudioClip> audioClips;
	protected int sampleRate;
	
	public AudioPlayer(byte[] buffer, int sampleRate)
	{
		this.sampleRate = sampleRate;
		audioClips = new ArrayList<AudioClip>();
		input = new ByteArrayInputStream(buffer);
		play();
	}
	
	public AudioPlayer(String filePath)
	{
		play(filePath);
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
			
			sourceDataLine = (SourceDataLine)AudioSystem.getLine(info);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();
			
			input = audioToBytes(inputStream);
			
			byte[] buffer = new byte[512];
			
			while (input.read(buffer,0,buffer.length) != -1)
			{
				sourceDataLine.write(buffer, 0, buffer.length);
				//System.out.println(byteInput.available() + "|" + buffer.length);
			}
			

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void play()
	{
		try
		{
		} 
		catch (Exception e)
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
		
		return BytesUtil.shortToBytes(temp);
	}
	
	public void close()
	{
		try
		{
			if(inputStream != null)
				inputStream.close();
			if(sourceDataLine != null)
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
	
	public ByteArrayInputStream audioToBytes(AudioInputStream audioInput) throws IOException
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[2048];
		
		while(audioInput.read(buffer, 0, buffer.length) != -1)
		{
			output.write(buffer);
		}
		
		return new ByteArrayInputStream(output.toByteArray());
	}
}
