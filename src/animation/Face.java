package animation;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import utils.Resource;

public class Face implements Renderable
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
	
	public void draw(Graphics g)
	{
		g.drawImage(image, x, y, width, height, null);
	}
	
	@Override
	public void renderImage(int frame, int fps, Graphics g)
	{
		draw(g);
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

	@Override
	public void previewImage(Graphics g)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int frameLength()
	{
		return -1;
	}

	@Override
	public void reset()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int lastFrame()
	{
		return 0;
	}

	@Override
	public int getWidth()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight()
	{
		// TODO Auto-generated method stub
		return 0;
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
}
