package maker.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;

import maker.dialog.AnimationCanvas;
import maker.exporter.AnimatedImageExporter;
import maker.exporter.ExportFileFilter;
import maker.exporter.ExportFormat;
import maker.exporter.Exporter;
import maker.exporter.ImageExporter;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.JProgressBar;


public class ExportWindow extends JDialog
{

	private JPanel contentPane;
	private JTextField txtPath;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ExportWindow frame = new ExportWindow();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ExportWindow()
	{
		setTitle("Export");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 320, 313);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelSettings = new JPanel();
		panelSettings.setBounds(10, 13, 278, 205);
		contentPane.add(panelSettings);
		panelSettings.setLayout(null);
		
		//"Format"标签
		JLabel label1 = new JLabel("Format");
		label1.setBounds(0, 0, 72, 18);
		panelSettings.add(label1);
		
		//格式选择框
		JComboBox<ExportFormat> formatBox = new JComboBox<ExportFormat>();
		formatBox.setBounds(86, 0, 169, 24);
		panelSettings.add(formatBox);
		formatBox.setModel(new DefaultComboBoxModel(ExportFormat.values()));

		//"Export to"标签
		JLabel label2 = new JLabel("Export to");
		label2.setBounds(0, 37, 72, 18);
		panelSettings.add(label2);

		//导出路径文本编辑框
		txtPath = new JTextField();
		txtPath.setBounds(87, 33, 137, 24);
		panelSettings.add(txtPath);
		txtPath.setColumns(10);
		
		//选择路径按钮
		JButton btnOpen = new JButton("...");
		btnOpen.setBounds(226, 33, 29, 24);
		btnOpen.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser();
				ExportFormat format = (ExportFormat)formatBox.getSelectedItem();
				fileChooser.setFileFilter(new ExportFileFilter(format));
				
				if(fileChooser.showSaveDialog(getContentPane()) != JFileChooser.CANCEL_OPTION)
				{
					txtPath.setText(fileChooser.getSelectedFile().toString() + "." + format.getSuffix());
				}
			}
		});
		panelSettings.add(btnOpen);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(14, 165, 250, 27);
		panelSettings.add(progressBar);

		JPanel panel = new JPanel();
		panel.setBounds(0, 231, 302, 37);
		contentPane.add(panel);
		
		//取消
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(51, 4, 81, 27);
		btnCancel.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		
		//保存
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(161, 4, 81, 27);
		btnSave.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ExportFormat format = (ExportFormat) formatBox.getSelectedItem();
				AnimationCanvas canvas = AnimationCanvas.instance;
				String path = txtPath.getText();
				Exporter exporter = null;
				
				//寻找合适的 exporter
				switch (format)
				{
				case PNG:
					exporter = new ImageExporter();
					break;
				case GIF:
					exporter = new AnimatedImageExporter();
					break;
				default:
					break;
				}
				
				//如果未找到合适的 exporter
				if(exporter == null)
				{
					System.err.println("Unknown format: " + format.getSuffix());
					return;
				}
				
				//导出
				exporter.export(canvas, path);
			}
		});
		
		panel.setLayout(null);
		panel.add(btnCancel);
		panel.add(btnSave);
	}
}
