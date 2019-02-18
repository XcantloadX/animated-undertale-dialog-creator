package resource;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Face
{
	public int x;
	public int y;
	public int width;
	public int height;
	private Image image;
	
	public Face(String spritePath)
	{
		try
		{
			image = ImageIO.read(new File(spritePath));//从文件里读取图像
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Face(String spritePath, int width, int height)
	{
		this(spritePath);
		this.width = width;
		this.height = height;
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(image, x, y, width, height, null);
	}
}
