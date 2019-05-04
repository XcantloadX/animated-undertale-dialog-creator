package animation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import audio.AudioClip;
import audio.AudioTrack;
import audio.FFmpeg;
import audio.PCMCoder;
import utils.Resource;

public class AnimatedTextBox implements Renderable
{
	private String text;
	private int currentCharacter = 0;
	private int interval;//毫秒
	private int intervalFPS = 1;
	private boolean skipSpace = false;
	private Font font;
	private int x;
	private int y;
	
	private AudioTrack track;
	private AudioClip clip;
	private FFmpeg fFmpeg;
	private int sampleRate = 48000;
	private String audioPath;
	
	public AnimatedTextBox()
	{
		interval = 100;
		
		//加载音效
		fFmpeg = new FFmpeg();
		audioPath = Resource.getResFolder() + "\\voices\\asriel.wav";
		byte[] buffer = fFmpeg.getAudioDataFromFile(audioPath, sampleRate);
		short[] data = PCMCoder.decodeShort(buffer);
		clip = new AudioClip(data, sampleRate);
	}
	
	public AnimatedTextBox(String text, Font font)
	{
		this();
		this.text = text;
		this.font = font;
	}
	
	public AnimatedTextBox(String text, int x, int y, int interval, Font font)
	{
		this(text, font);
		this.x = x;
		this.y = y;
		
		this.interval = interval;
	}
	

	
	//设置到最后一个字符
	public void lastChar()
	{
		currentCharacter = text.length();
	}
	
	//设置到第一个字符
	public void firstChar()
	{
		currentCharacter = 0;
	}

	public boolean isFinished()
	{
		if(currentCharacter >= text.length())
			return true;
		return false;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public boolean isSkipSpace()
	{
		return skipSpace;
	}

	public void setSkipSpace(boolean skipSpace)
	{
		this.skipSpace = skipSpace;
	}

	public Font getFont()
	{
		return font;
	}

	public void setFont(Font font)
	{
		this.font = font;
	}
	public int getInterval()
	{
		return interval;
	}
	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
	
	/**
	 * 获取当前文本的长度
	 * @return 文本的长度，如果文本为空，则返回 -1
	 */
	public int length()
	{
		if(text == null)
			return -1;
		return text.length();
	}
	
	/**
	 * 刷新当前字体，在开始渲染前调用
	 */
	public void refreshFont(Graphics g)
	{
		g.setFont(font);
	}
	
	@Override
	public void renderImage(int frame, int fps, Graphics g)
	{
		//计算当前字符
		int chars = (int)(frame / intervalFPS);
		if(chars > length())
			chars = length();
		
		String temp = text.substring(0, chars);
		g.setColor(Color.WHITE);
		//g.setFont(font);
		g.drawString(temp, x, y);
	}

	@Override
	public void previewImage(Graphics g)
	{
		renderImage(lastFrame(), AnimationCanvas.getFPS(), g);
	}

	@Override
	public int frameLength()
	{
		return length() * intervalFPS;
	}

	@Override
	public void reset()
	{
		
	}

	@Override
	public int lastFrame()
	{
		return frameLength();
	}

	@Override
	public int getWidth()
	{
		return -1;
	}

	@Override
	public int getHeight()
	{
		return -1;
	}

	@Override
	public void initRender(Graphics g)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void previewImage(int frame, Graphics g)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshPreview()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRenderAudio()
	{
		return true;
	}

	@Override
	public void renderAudio(AudioTrack track, int fps)
	{
		double time = (double)intervalFPS / fps;
		track.addClipRepeat(clip, this.length(), (float)time);
	}
	
}
