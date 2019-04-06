package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import animation.AnimationCanvas;

public class ExportWindow extends JFrame
{
	private AnimationCanvas canvas;
	private JPanel settingpaPanel;
	private JPanel previewpPanel;
	
	public ExportWindow(AnimationCanvas canvas)
	{
		//this.canvas =canvas;
		this.setTitle("Export as");
		this.setSize(550, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		init();
	}
	
	private void init()
	{
		this.setLayout(new BorderLayout());
		
		settingpaPanel = new JPanel();
		settingpaPanel.setVisible(true);
		settingpaPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(settingpaPanel);
		
		/*previewpPanel = new JPanel();
		previewpPanel.setVisible(true);
		this.add(previewpPanel);*/
		
		//-----------------
		JComboBox<String> format = new JComboBox<>();
		format.setVisible(true);
		format.addItem("Image File (*.png)");
		format.addItem("Animated Gif File (*.gif)");
		format.addItem("Video with sound (*.mp4)");
		format.addItem("Wave sound (*.wav)");
		settingpaPanel.add(format);
		
	}
}
