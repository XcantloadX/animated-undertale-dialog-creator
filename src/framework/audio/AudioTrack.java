package framework.audio;

import java.util.ArrayList;

public class AudioTrack
{
	private ArrayList<AudioClip> clips = new ArrayList<AudioClip>();
	private int sampleRate = 0;
	private float lastTime = 0;
	private int lastFrame = 0;
	
 	public AudioTrack(int sampleRate)
	{
		this.sampleRate = sampleRate;
	}
	
	public AudioTrack(AudioClip clip, int sampleRate)
	{
		this(sampleRate);
		this.addClip(clip);
	}
	
	public void addClip(AudioClip clip)
	{
		//System.out.println("lasttime="+lastFrame);
		clips.add(clip);
		lastTime = clip.getPosition() + clip.getLengthInSec();
		lastFrame = clip.getPositionInSample() + clip.getLengthInSample();
	}
	
	public void addClipAt(AudioClip clip, float pos)
	{
		clip.setPosition(pos);
		this.addClip(clip);
	}
	
	/**
	 * 在上次添加的 clip 后隔指定时间添加 AudioClip
	 * @param sec 指定时间（单位：秒）
	 */
	public void addClipAfter(AudioClip clip, float sec)
	{
		//必须先设置新坐标在加入Track，要不然会出现坐标计算错误（数组越界）
		clip.setPositionInSample(lastFrame);
		addClip(clip);
		//clip.setPosition(lastTime + sec);
	}
	
	/**
	 * 循环添加指定 AudioClip
	 * @param clip 要添加的 AudioClip
	 * @param repeatCount 循环次数
	 * @param interval 循环间隔（单位：秒）
	 */
	public void addClipRepeat(AudioClip clip, int repeatCount, float interval)
	{
		for(int i = 1; i <= repeatCount; i++)
		{
			this.addClipAfter(AudioTrack.copyClip(clip), interval);
		}
	}
	
	public static AudioClip copyClip(AudioClip clip)
	{
		return new AudioClip(clip.getSamples(), clip.getSampleRate());
	}
	
	public short[] getSamples()
	{
		//合并所有clip为一个音频文件
		short[] buffer = new short[getLengthInSample()];
		int pos = 0;
		//System.out.println("Length: " + this.getLengthInSample());
		for (int i = 0; i < clips.size(); i++)
		{
			AudioClip clip = clips.get(i);
			
			short[] s = clip.getSamples();

			System.arraycopy(s, 0, buffer, pos, s.length);
			pos = clip.getPositionInSample() + clip.getLengthInSample();
		}
		
		return buffer;
	}
	
	public float getLengthInSec()
	{
		//秒数 = 单声道样本数 / 采样率
		return getLengthInSample() / 2 / sampleRate;
	}
	
	public int getLengthInSample()
	{
		//找到时间排在最后面的clip
		AudioClip clip = getLastClip();
		int maxTime = clip.getPositionInSample();
		
		//音频总长度 = 排在最后的clip的位置 + 该clip的长度
		int length = maxTime + clip.getLengthInSample();
		//System.out.println("length: " + length);
		return length;
	}
	
	
	
	/**
	 * 获取时间位置排在最后的 AudioClip
	 * @return
	 */
	private AudioClip getLastClip()
	{
		AudioClip clip = null;
		int maxTime = 0;
		
		for (int i = 0; i < clips.size(); i++)
		{
			
			int time = clips.get(i).getPositionInSample();
			if(time >= maxTime)
			{
				maxTime = time;
				clip = clips.get(i);
			}
			//System.out.println(i+"-"+time);
		}
		//System.out.println("last=" + maxTime);
		return clip;
	}
}
