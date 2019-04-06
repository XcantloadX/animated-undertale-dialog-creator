package animation;

import java.awt.Graphics;

public interface RenderListener
{
	public void onRender(int frame, int fps, Graphics g);
	
}
