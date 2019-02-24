package animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.swing.JPanel;

import com.sun.jna.platform.win32.Netapi32Util.User;

import audio.Audio;
import utils.Resource;

public class AnimationPanel extends JPanel
{
	private int width = 500;
	private int height = 150;
	public int fps = 15;
	private Color backgroundColor = Color.BLACK;
	public String texts ="This is a test text.";
	private Graphics graphics;
	private Audio audio;
	private Image image;
	private Face face;
	private AnimatedTextBox textBox;
	
	private boolean renderStarted = false;
	private int currentChar = 0;
	
	private int borderWidth = 5;
	private int borderHeight = 5;
	int i = 0;
	
	public AnimationPanel()
	{
		this.setPreferredSize(new Dimension(width, height));
		audio = new Audio(Resource.getResFolder() + "\\voices\\asriel.wav");
		face = Face.getSansFace(0);
		textBox = new AnimatedTextBox(texts,100, 50, 100);
		face.width = 70;
		face.height = 70;
	}
	
	private void init()
	{
		image = this.createImage(width, height);
		graphics = image.getGraphics();
	}
	
	@Override
	public void paint(Graphics g)
	{
		render(g);
	}
	
	private void render(Graphics g)
	{
		if(image == null && graphics == null)
			init();
		
		//Çå³ý±³¾°
		clear(g, image);
		
		drawBorder();
		face.y = (height / 2) - (face.height / 2);//ÌùÍ¼¾ÓÖÐÏÔÊ¾
		face.x = 30;
		face.draw(graphics);
		
		
		//Ë«»º³å
		graphics.setColor(Color.WHITE);
		try
		{
			graphics.setFont(Fonts.getSansFont(20));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//»æÖÆÎÄ×Ö
		textBox.setX(face.x + face.width + 20);
		textBox.setY(50);
		textBox.draw(graphics);
		
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g1 = bi.createGraphics();
		g1.drawImage(image, 0, 0, null);

		/*try
		{
			ImageIO.write(bi, "png", new File("E:\\" +i+".png" ));
		} catch (IOException e)
		{
			e.printStackTrace();
		}*/
		i++;
		g.drawImage(image, 0, 0, null);
	}
	
	//°×É«±ß¿ò
	private void drawBorder()
	{
		graphics.setColor(Color.WHITE);
		graphics.fillRect(borderWidth, borderHeight, width - borderWidth * 2, height - borderHeight * 2);
		graphics.setColor(backgroundColor);
		graphics.fillRect(borderWidth * 2, borderHeight * 2, width - borderWidth * 4, height - borderHeight * 4);
	}
	
	public void clear(Graphics g, Image img)
	{
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);
		
		Graphics graphics = img.getGraphics();
		graphics.setColor(backgroundColor);
		graphics.fillRect(0, 0, width, height);
	}
	
	public void start()
	{
		renderStarted = true;
		Thread t = new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				renderThread();
			}
		});
		t.start();
	}
	
	public void setText(String text)
	{
		textBox.setText(text);
	}
	
	public void saveAsImage(String path) throws IOException
	{
		textBox.lastChar();
		
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.createGraphics();
		paint(g);
		
		ImageIO.write(bi, "png", new File(path));
		
		textBox.firstChar();
	}
	
	private void renderThread()
	{
		while (true)
		{
			try
			{
				if(textBox.isFinished())
				{
					renderStarted = false;
					break;
				}
				this.repaint();
				
				Thread.sleep(1000 / fps);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
