package framework.test;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import framework.audio.AudioClip;
import framework.audio.AudioTrack;
import framework.audio.FFmpeg;
import framework.audio.PCMCoder;
import framework.utils.Resource;

public class SpeakerTest extends Tester
{	
	public static void main(String[] args)
	{
		try
		{
			audioTest();
		} catch (Exception e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	private static void audioTest() throws Exception
	{
		String filePath = Resource.getFolder() + "\\voices\\asriel.wav";
		//String filePath = "D:\\1.wav";
		int sampleRate = 48000;
		
		FFmpeg f = new FFmpeg();
		byte[] buffer = f.getAudioDataFromFile_(filePath, sampleRate);
		
		short[] samples = PCMCoder.decodeShort(buffer);
		
		
		AudioTrack track = new AudioTrack(sampleRate);
		AudioClip clip = new AudioClip(samples, sampleRate);
		AudioClip clip2 = clip.clone();
		track.addClip(clip);
		track.addClipRepeat(clip, 20, 0);
		
		System.out.println("[Test]Clip length = " + clip.getLengthInSec());
		System.out.println("[Test]Track length = " + track.getLengthInSec());
		
		//测试导出
		byte[] b = PCMCoder.encoderShort(track.getSamples());
		/*write(b, "C:\\test.pcm");
		f.exportToWav(b, sampleRate, 16, 2, "C:\\test.wav");*/
		
		//测试播放
		AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sampleRate, 16, 2, 4, 48000, false);
		Clip clip3 = AudioSystem.getClip();
		clip3.open(format, b, 0, b.length);
		//clip3.open(format, buffer, 0, buffer.length);
		clip3.start();
		
		waitForExit();
		
		clip3.stop();
		clip3.close();
	}
}
