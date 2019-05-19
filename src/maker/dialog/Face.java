package maker.dialog;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import framework.animation.AnimatedObject;
import framework.audio.AudioTrack;
import framework.utils.Resource;

public class Face extends AnimatedObject
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
			image = ImageIO.read(new File(spritePath));//从文件里读入
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
	
	public static Face getSansFace(int index)
	{
		Face face = null;
		String path = Resource.getFolder() + "\\sprites\\sans\\" + index + ".png";
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
			temp = Face.getSansFace(i);
			if(temp != null)
				faces.add(temp);
			else 
				break;
		}
		Face[] f = new Face[faces.size()];
		return faces.toArray(f);
	}

	private void draw(Graphics g)
	{
		g.drawImage(image, x, y, width, height, null);
	}
	
	@Override
	public void paint(Graphics g)
	{
		this.draw(g);
		
	}

	@Override
	public void render(Graphics g, int frame, int fps)
	{
		this.draw(g);
		
	}

	@Override
	public int length()
	{
		return 0;
	}
}
