package animation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class AnimatedTextBox
{
	private String text;
	private int currentCharacter = 0;
	private int interval;//∫¡√Î
	private boolean skipSpace = false;
	private Font font;
	private int x;
	private int y;
	
	
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
	
	public void nextChar()
	{
		interval++;
		if(text.substring(currentCharacter, currentCharacter+1) == " ")
			interval++;
	}
	
	public void draw(Graphics g)
	{
		String temp = text.substring(0, currentCharacter);
		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString(text, x, y);
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
	
}
