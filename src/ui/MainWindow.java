package ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import animation.AnimationCanvas;
import animation.RenderListener;
import exporter.AnimatedImageExporter;

public class MainWindow extends JFrame
{
	private int width = 600;
	private int height = 500;
	private int fps;
	private String windowTitle = "Undertale Dialog Maker";
	
	private AnimationCanvas canvas;
	private JPanel progressPanel;
	private JPanel controlPanel;
	private JPanel editorPanel;
	private JPanel settingPanel;
	
	public MainWindow()
	{
		super();
		this.setTitle(windowTitle);
		this.setSize(width, height);
		this.setResizable(false);
		this.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		
		//窗口居中显示
		this.setLocationRelativeTo(null);
	}
	
	private void initComponents()
	{
		//-----------JPanel和Canvas-----------
		canvas = new AnimationCanvas();
		canvas.setVisible(true);
		this.add(canvas);
		
		progressPanel = new JPanel();
		progressPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		progressPanel.setPreferredSize(new Dimension(this.getWidth() - 80, 40));
		progressPanel.setVisible(true);
		this.add(progressPanel);
		
		controlPanel = new JPanel();
		controlPanel.setVisible(true);
		this.add(controlPanel);
		
		editorPanel = new JPanel();
		editorPanel.setVisible(true);
		this.add(editorPanel);
		
		settingPanel = new JPanel();
		settingPanel.setVisible(true);
		this.add(settingPanel);
		
		ControlPanel control = new ControlPanel(canvas);
		control.setVisible(true);
		this.add(control);
		
		//-----------进度条-----------
		JLabel position = new JLabel();
		position.setVisible(true);
		position.setPreferredSize(new Dimension(1000, 10));
		progressPanel.add(position);
		
		JSlider progresSlider = new JSlider(0, 20);
		progresSlider.setVisible(true);
		progresSlider.setPreferredSize(new Dimension(500, 20));
		progresSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				canvas.setPosition(progresSlider.getValue());
				canvas.refreshPreview();
			}
		});
		progressPanel.add(progresSlider);
		
		
		
		canvas.addRenderListener(new RenderListener()
		{
			@Override
			public void onRender(int frame, int fps, Graphics g)
			{
				progresSlider.setMaximum(canvas.frameLength());
				progresSlider.setValue(frame);
				
				String pos  = "Frame: " + String.format("%02d", frame) + " / " + canvas.frameLength();
				String rate = "FPS: " + canvas.getFPS();
				String s = pos + "        " + rate;
				position.setText(s);
			}
		});
		
		
		
		//-----------控制按钮-----------
		//播放按钮
		JButton play = new JButton("Play");
		play.setVisible(true);
		play.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				canvas.start();
			}
		});
		controlPanel.add(play);
		
		//停止按钮
		JButton pause = new JButton("Pause");
		pause.setVisible(true);
		pause.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				canvas.pause();
			}
		});
		controlPanel.add(pause);
		
		//是否循环播放
		JCheckBox loop = new JCheckBox("Looping");
		loop.setVisible(true);
		loop.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				canvas.setLoop(loop.isSelected());
			}
		});
		controlPanel.add(loop);
		
		JButton savePng = new JButton("Save as PNG");
		savePng.setVisible(true);
		savePng.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					if(canvas.saveAsImage("E:\\test.png"))
						System.out.println("Saved successfully at: " + "E:\\test.png");
					else
						System.err.println("Something was wrong.");
				} 
				catch (IOException e)
				{
					System.out.println("Error while saving png: ");
					e.printStackTrace();
				}
				
			}
		});
		controlPanel.add(savePng);
		
		JButton saveGif = new JButton("Save as Gif");
		saveGif.setVisible(true);
		saveGif.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				AnimatedImageExporter.export(canvas, "E:\\test_animation.gif");
				System.out.println("Saved gif to: " + "E:\\test_animation.gif");
			}
		});
		
		controlPanel.add(saveGif);
		
		//-------------文本编辑框--------------
		JTextPane textPane = new JTextPane();
		textPane.setPreferredSize(new Dimension(450, 100));
		textPane.setVisible(true);
		editorPanel.add(textPane);
		
		//------------设置面板----------------
		JButton export = new JButton("Export as...");
		export.setVisible(true);
		export.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ExportWindow window = new ExportWindow(canvas);
				window.setVisible(true);
			}
		});
		settingPanel.add(export);
		
		JButton custom = new JButton("Custom Dialog");
		custom.setVisible(true);
		settingPanel.add(custom);
		
		//------------------

	}
	
	public AnimationCanvas getCanvas()
	{
		return canvas;
	}
}
