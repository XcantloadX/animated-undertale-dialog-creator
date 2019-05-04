package ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import animation.AnimationCanvas;

public class ControlPanel extends Panel
{
	private AnimationCanvas canvas;
	
	private JLabel position;
	
	public ControlPanel(AnimationCanvas canvas)
	{
		this.canvas = canvas;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setVisible(true);
		init();
	}
	
	private void init()
	{
		//进度条
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
		this.add(progresSlider);
		
		//当前播放位置
		position = new JLabel();
		position.setVisible(true);
		//position.setPreferredSize(new Dimension(10, 0));
		position.setText("0 / 0");
		this.add(position);
	}
}
