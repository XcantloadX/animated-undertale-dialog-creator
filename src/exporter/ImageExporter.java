package exporter;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import animation.AnimationCanvas;
import animation.Renderable;
import utils.ImageUtil;

public class ImageExporter extends Exporter
{
	public static boolean export(Renderable render, String path)
	{
		BufferedImage image = new BufferedImage(render.getWidth(), render.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		render.initRender(g);
		render.renderImage(render.lastFrame(), AnimationCanvas.getFPS(), g);
		return ImageUtil.saveImage(image, path);
	}
}
