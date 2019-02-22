package main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import animation.AnimationPanel;

public class MainWindow extends JFrame
{
	public AnimationPanel animationPanel;
	public JPanel editorPanel;
	private int width = 600;
	private int height = 500;
	private int fps;
	private String windowTitle = "Undertale Dialog Animation Creator";
	
	private JPanel controlPanel;
	
	public MainWindow()
	{
		super();
		this.setTitle(windowTitle);
		this.setSize(width, height);
		this.setResizable(false);
		this.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
	}
	
	private void initComponents()
	{
		animationPanel = new AnimationPanel();
		this.add(animationPanel);
		animationPanel.setVisible(true);
		
		controlPanel = new JPanel();
		controlPanel.setVisible(true);
		this.add(controlPanel);
		
		editorPanel = new JPanel();
		editorPanel.setVisible(true);
		this.add(editorPanel);
		
		JButton play = new JButton("Play");
		play.setVisible(true);
		play.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				animationPanel.start();
			}
		});
		controlPanel.add(play);
		
		JButton pause = new JButton("Pause");
		pause.setVisible(true);
		controlPanel.add(pause);
		
		JTextPane textPane = new JTextPane();
		textPane.setPreferredSize(new Dimension(450, 100));
		textPane.setVisible(true);
		editorPanel.add(textPane);
	}
}
