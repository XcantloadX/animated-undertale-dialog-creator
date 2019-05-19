package framework.audio;

public class AudioClip implements Cloneable
{
	private int sampleRate = 0;
	private int channels = 2;
	private short[] samples;
	private byte[] buffer;
	private float positionInSec = 0f;
	private int positionInSample = 0;
	
	public AudioClip(short[] buffer, int sampleRate)
	{
		this.samples = buffer;
		this.buffer = PCMCoder.encoderShort(this.samples);
		this.sampleRate = sampleRate;
	}
	
	public AudioClip(int sampleRate)
	{
		this(new short[1], sampleRate);
	}
	
	public float getPosition()
	{
		return positionInSec;
	}
	
	public int getPositionInSample()
	{
		return positionInSample;
	}
	
	public void setPosition(float pos)
	{
		positionInSec = pos;
		positionInSample = secToSample(pos);
	}
	
	public void setPositionInSample(int pos)
	{
		positionInSample = pos;
	}
	
	public short[] getSamples()
	{
		return samples;
	}
	
	public void setSamples(short[] samples)
	{
		this.samples = samples;
	}
	
	public int getLengthInSample()
	{
		return samples.length;
	}
	
	public int getLengthInByte()
	{
		return samples.length * 4;
	}
	
	public float getLengthInSec()
	{
		return getLengthInSample() / 2 / sampleRate;
	}
	
	private int secToSample(float sec)
	{
		//样本数 ＝ 秒 * 采样率 * 通道数量
		return (int) (sec * sampleRate * channels);
	}

	public int getSampleRate()
	{
		return sampleRate;
	}
	
	public void setSampleRate(int sampleRate)
	{
		this.sampleRate = sampleRate;
	}
	
	@Override
	public AudioClip clone() throws CloneNotSupportedException
	{
		AudioClip clip = new AudioClip(0);
		clip.setSampleRate(sampleRate);
		clip.setSamples(getSamples());
		return clip;
	}
	
	public static AudioClip getClipFromFile(String filePath, int sampleRate) 
	{
		byte[] buffer = new FFmpeg().getAudioDataFromFile_(filePath, sampleRate);
		short[] data = PCMCoder.decodeShort(buffer);
		return new AudioClip(data, 48000);
	}

}
