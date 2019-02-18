package main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame
{
	public AnimationPanel animationPanel;
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
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setVisible(true);
		controlPanel.add(editorPane);
	}
}
