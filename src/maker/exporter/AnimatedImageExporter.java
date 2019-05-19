package maker.exporter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import framework.animation.Renderable;
import framework.utils.AnimatedGifEncoder;
import maker.dialog.AnimationCanvas;

public class AnimatedImageExporter extends Exporter
{
	public boolean export(Renderable render, String path)
	{
		int i = 0;
		AnimatedGifEncoder gif = new AnimatedGifEncoder();
		gif.setFrameRate(AnimationCanvas.getFPS());
		gif.setSize(render.getWidth(), render.getHeight());
		gif.setRepeat(0);//循环播放
		gif.start(path);
		
		BufferedImage bi = new BufferedImage(render.getWidth(), render.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		render.initRender(g);
		
		while (i <= render.frameLength())
		{
			render.renderImage(i, AnimationCanvas.getFPS(), g);
			gif.addFrame(bi);
			i++;
		}
		
		//播放完之后停顿一会在重新播放
		for (int j = 0; j < AnimationCanvas.getFPS() / 2; j++)
		{
			gif.addFrame(bi);
		}
		
		gif.finish();
		
		return true;
	}
}
