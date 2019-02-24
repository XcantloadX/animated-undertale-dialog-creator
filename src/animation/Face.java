package animation;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import utils.Resource;

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
	
	public static Face getSansFace(int index)
	{
		Face face = null;
		String path = Resource.getResFolder() + "\\sprites\\sans\\" + index + ".png";
		if(!new File(path).exists())
			return null;
		face = new Face(path);
		return face;
	}
	
	public static Face[] getAllSansFaces()
	{
		ArrayList<Face> faces = new ArrayList<Face>();
		int i = 0;
		Face temp = null;

		while(true)
		{
			temp = getSansFace(i);
			if(temp != null)
				faces.add(temp);
			else 
				break;
		}
		Face[] f = new Face[faces.size()];
		return faces.toArray(f);
	}
}
