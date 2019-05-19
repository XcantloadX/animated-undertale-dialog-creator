package maker.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import framework.animation.RenderListener;
import framework.animation.Renderable;
import framework.audio.AudioClip;
import framework.audio.AudioTrack;
import framework.audio.FFmpeg;
import framework.utils.FontUtil;
import maker.exporter.ImageExporter;

public class AnimationCanvas extends JPanel implements Renderable
{
	private int width = 500;
	private int height = 150;
	private static int fps = 30;
	private Color backgroundColor = Color.BLACK;
	public String texts ="* This is a test text.";
	
	private Graphics graphics;
	private Image image;
	private Face face;
	private AnimatedTextBox textBox;
	
	public String audioPath;
	private AudioTrack track;
	private AudioClip clip;
	private FFmpeg ffmpeg;
	private int sampleRate = 48000;
	
	private boolean started = false;
	private int frame = 0;
	private Font sansFont;
	
	private int borderWidth = 5;
	private int borderHeight = 5;
	private boolean loop = false;
	
	private ArrayList<RenderListener> listeners = new ArrayList<>();
	
	public static AnimationCanvas instance;
	
	public AnimationCanvas()
	{
		sansFont = FontUtil.getSansFont(20);
		if(sansFont == null)
			sansFont = FontUtil.getDefaultFont();
		
		this.setPreferredSize(new Dimension(width, height));
		face = Face.getSansFace(0);
		textBox = new AnimatedTextBox(texts, 100, 50, 40, sansFont);
		face.width = 70;
		face.height = 70;
		
		AnimationCanvas.instance = this;
	}
	
	private void init()
	{
		//初始化双缓冲
		image = this.createImage(width, height);
		graphics = image.getGraphics();
		
		//设置字体
		textBox.refreshFont(graphics);
		
		//加载音效
		ffmpeg = new FFmpeg();
		track = new AudioTrack(sampleRate);
	}
	
	@Override
	public void paint(Graphics g)
	{
		if(image == null || graphics == null)
			init();
		
		if(!started)//如果渲染未开始（预览模式）
		{
			previewImage(frame, graphics);
			g.drawImage(image, 0, 0, null);
		}			
		else //渲染开始之后
		{
			renderImage(frame,fps,graphics);
			g.drawImage(image, 0, 0, null);
			frame++;
		}
			
	}
	
	//白色边框
	private void drawBorder(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(borderWidth, borderHeight, width - borderWidth * 2, height - borderHeight * 2);
		g.setColor(backgroundColor);
		g.fillRect(borderWidth * 2, borderHeight * 2, width - borderWidth * 4, height - borderHeight * 4);
	}
	
	public void clear(Graphics g)
	{
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);
	}
	
	public void start()
	{
		started = true;
		
		//初始化渲染
		initRender(graphics);
		
		//重置
		if(!loop && frame == frameLength())
			frame = 0;
		
		//启动渲染线程
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
	
	public void pause()
	{
		started = false;
	}
	
	public void reset()
	{
		frame = 0;
	}
	
	@Override
	public void refreshPreview()
	{
		previewImage(frame, graphics);
		this.getGraphics().drawImage(image, 0, 0, null);
	}
	
	public void setPosition(int frame)
	{
		this.frame = frame;
	}
	
	public void setText(String text)
	{
		textBox.setText(text);
	}
	
	private void renderThread()
	{
		
		while (started)
		{
			try
			{
				if(frame > textBox.length())
				{
					if(isLoop())//如果启用循环播放
						frame = 0;
					else
					{
						started = false;
						frame = 0;
						break;
					}
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

	@Override
	public void renderImage(int frame, int fps, Graphics g)
	{
		if(image == null && graphics == null)
			init();
		
		//清除背景
		clear(g);
		
		//绘制边框
		drawBorder(g);
		
		//绘制人物贴图
		face.y = (height / 2) - (face.height / 2);//贴图居中显示
		face.x = 30;
		face.render(g, frame, fps);
		
		//设置颜色
		graphics.setColor(Color.WHITE);

		//绘制文字
		textBox.setPosition(face.x + face.width + 20, 50);
		textBox.render(g, frame, fps);
		
		//调用RenderListener
		for (int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).onRender(frame, fps, g);
		}
	}

	@Override
	public void previewImage(Graphics g)
	{
		renderImage(frameLength(), fps, g);
	}
	
	@Override
	public void previewImage(int frame,Graphics g)
	{
		renderImage(frame, fps, g);
	}

	@Override
	public int frameLength()
	{
		return textBox.length();
	}

	@Override
	public int lastFrame()
	{
		return frameLength();
	}

	public static int getFPS()
	{
		return fps;
	}

	@Override
	public void initRender(Graphics g)
	{
		textBox.refreshFont(g);
		
	}
	
	public void addRenderListener(RenderListener listener)
	{
		listeners.add(listener);
	}

	public boolean isLoop()
	{
		return loop;
	}

	public void setLoop(boolean loop)
	{
		this.loop = loop;
	}

	public AudioTrack getAudioTrack()
	{
		return track;
	}

}
