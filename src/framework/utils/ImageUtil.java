package framework.utils;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil
{	
	public static int[][] getRGB(BufferedImage image)
	{
		int[][] rgb = new int[image.getWidth()][image.getHeight()];
		
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < image.getHeight(); y++)
			{
				rgb[x][y] = image.getRGB(x, y);
			}
		}
		
		return rgb;
		
	}
	
	public static int[][] getRGB(String file) throws IOException
	{
		BufferedImage image = ImageIO.read(new File(file));
		return getRGB(image);
	}
	
	public static BufferedImage getBufferedImage(String file)
	{
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(new File(file));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return image;
	}
	
	public static boolean saveImage(BufferedImage image, String path)
	{
		String format  = path.substring(path.lastIndexOf(".") + 1, path.length()); 
		try
		{
			ImageIO.write(image, format, new File(path));
			return true;
			
		} catch (Exception e)
		{
			System.err.println("Something wrong while writing file " + path);
			e.printStackTrace();
		}
		
		return false;
	}
}
