package maker.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import framework.animation.AnimatedObject;
import framework.audio.AudioClip;
import framework.audio.AudioTrack;
import framework.audio.FFmpeg;
import framework.audio.PCMCoder;
import framework.utils.Resource;

public class AnimatedTextBox extends AnimatedObject
{
	private String text;
	/**
	 * 每两个字符之间的间隔，单位帧数
	 */
	private int interval = 2;
	/**
	 * 是否跳过空格的输出
	 */
	private boolean skipSpace = true;
	private Font font;
	
	private AudioTrack track;
	private AudioClip clip;
	private FFmpeg fFmpeg;
	private int sampleRate = 48000;
	private String audioPath;
	
	public AnimatedTextBox()
	{
		this.interval = 2;
		
		//加载音效
		/*fFmpeg = new FFmpeg();
		audioPath = Resource.getResFolder() + "\\voices\\asriel.wav";
		byte[] buffer = fFmpeg.getAudioDataFromFile(audioPath, sampleRate);
		short[] data = PCMCoder.decodeShort(buffer);
		clip = new AudioClip(data, sampleRate);*/
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
		this.setPosition(x, y);
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
	
	/**
	 * 获取当前文本的长度
	 * @return 文本的长度，如果文本为空，则返回 -1
	 */
	public int textLength()
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
	public void paint(Graphics g)
	{
		this.render(g, this.length(), AnimationCanvas.getFPS());
	}

	@Override
	public void render(Graphics g, int frame, int fps)
	{
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		
		//计算当前字符
		int index = (int)(frame / interval);
		if(index > textLength())
			index = textLength();
		
		int start = (index - 1 >= 0) ? index -1 : index;
		String character = text.substring(start, index);
		if(character == " ")
			index++;
		
		String s = text.substring(0, index);
		g.setColor(Color.WHITE);
		g.drawString(s, x, y);
	}

	@Override
	public int length()
	{
		return textLength() * interval;
	}
	
}
