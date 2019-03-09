package animation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class AnimatedTextBox
{
	private String text;
	private int currentCharacter = 0;
	private int interval;//毫秒
	private boolean skipSpace = false;
	private Font font;
	private int x;
	private int y;
	private long startTime = 0;
	private long endTime = 0;
	
	public AnimatedTextBox()
	{
		interval = 100;
	}
	
	public AnimatedTextBox(String text)
	{
		this();
		this.text = text;
	}
	
	public AnimatedTextBox(String text, int interval)
	{
		this(text);
		this.interval = interval;
	}
	
	public AnimatedTextBox(String text, int x, int y)
	{
		this(text);
		this.x = x;
		this.y = y;
	}
	
	public AnimatedTextBox(String text, int x, int y, int interval)
	{
		this(text, x, y);
		this.interval = interval;
	}
	
	//下一个字符
	public void nextChar()
	{
		if(isFinished())
			return;
		currentCharacter++;
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
	
	public void draw(Graphics g)
	{
		if(startTime == 0)
			startTime = System.currentTimeMillis();
		endTime = System.currentTimeMillis();
		
		//计算两次绘制的间隔时间是否大于interval
		if(endTime - startTime >= interval)
		{
			nextChar();
			startTime = System.currentTimeMillis();
		}
		
		String temp = text.substring(0, currentCharacter);
		System.out.println(temp);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(temp, x, y);

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
	
}
