package maker.exporter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ExportFileFilter extends FileFilter
{
	private ExportFormat format;
	
	public ExportFileFilter(ExportFormat format)
	{
		this.format = format;
	}
	
	@Override
	public boolean accept(File f)
	{
		if(!f.isFile())
			return false;
		String name = f.getName();
		String suffix = name.substring(name.lastIndexOf(".") + 1);//获取文件后缀名，不包括“.”
		String s = format.getSuffix();
		
		if(suffix == s)
			return false;
		else
			return true;
	}

	@Override
	public String getDescription()
	{
		return format.toString();
	}

}
