package framework.animation;

import java.awt.Graphics;

public abstract class AnimatedObject
{
	protected Vector2D position;
	
	/**
	 * 显示此 AnimatedObject，用于预览效果和静态图片
	 * @param g 渲染到此 Graphics 上
	 */
	public abstract void paint(Graphics g);
	
	/**
	 * 渲染此 AnimatedObject，用于导出为视频
	 * @param g 渲染到此 Graphics 上
	 * @param frame 当前渲染的帧数
	 * @param fps 当前渲染的帧率
	 */
	public abstract void render(Graphics g, int frame, int fps);
	
	/**
	 * 此 AnimatedObject 动画的总长度，单位帧
	 * @return
	 */
	public abstract int length();
	
	/**
	 * 设置此 AnimatedObject 的位置
	 * @param vector2d 位置坐标
	 */
	public void setPosition(Vector2D vector2d)
	{
		this.position = vector2d;
	}
	
	/**
	 * 设置此 AnimatedObject 的位置
	 * @param x x 坐标
	 * @param y y 坐标
	 */
	public void setPosition(int x, int y)
	{
		this.position = new Vector2D(x, y);
	}
	
	/**
	 * 返回此 AnimatedObject 的位置
	 */
	public Vector2D getPosition()
	{
		return this.position;
	}
}
