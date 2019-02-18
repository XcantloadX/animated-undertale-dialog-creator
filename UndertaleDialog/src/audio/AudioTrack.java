package audio;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class AudioTrack
{
	private Clip clip;
	private AudioInputStream inputStream;
	private AudioFormat audioFormat;
	private SourceDataLine sourceDataLine;
	
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
			
			System.arraycopy(buffer, 0, data, 0, data.length);
			for (int i = 0; i < data.length; i++)
			{
				data[i] *= 2;
			}
			System.arraycopy(data, 0, newBuffer, 0, newBuffer.length);
			/*int j = 0;
			for (int i = 0; i < newBuffer.length; i++)
			{
				if(j >= buffer.length)
				{
					j=0;
				}
				newBuffer[i] = buffer[j];
				//System.out.println(j);
				j++;
			}*/
			
			File f = new File("E:\\test.pcm");
			f.createNewFile();
			OutputStream o = new FileOutputStream(f);
			o.write(newBuffer);
			o.close();
			
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
}
