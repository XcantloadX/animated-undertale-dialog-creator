package resource;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Fonts
{
	public static Font getSansFont(int fontSize) throws Exception
	{
		return getFont(System.getProperty("user.dir") + "\\fonts\\sans.ttf", Font.PLAIN, fontSize);
	}
	
	public static Font getPapyrusFontChinese(int fontSize) throws Exception
	{
		return getFont(System.getProperty("user.dir") + "\\fonts\\papyrus-zh.ttf", Font.PLAIN, fontSize);
	}
	
	public static Font getGasterFont(int fontSize) throws Exception
	{
		return getFont(System.getProperty("user.dir") + "\\fonts\\gaster.ttf", Font.PLAIN, fontSize);
	}
	
	public static Font getFont(String fontFilePath, int style, float size) throws Exception
	{
		InputStream is = new FileInputStream(new File(fontFilePath));
		Font f = Font.createFont(Font.TRUETYPE_FONT, is);
		f = f.deriveFont(size);
		return f;
	}
}
