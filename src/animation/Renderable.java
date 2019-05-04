package animation;

import java.awt.Graphics;

import audio.AudioTrack;

public interface Renderable
{
	/**
	 * 预览单帧图片
	 * @param frame 第几帧
	 * @param fps 帧率
	 * @param g 渲染到此 Graphics 上
	 */
	public void renderImage(int frame, int fps, Graphics g);
	
	/**
	 * 预览单帧图片
	 * @param g 渲染到此 Graphics 上
	 */
	public void previewImage(Graphics g);
	
	public void previewImage(int frame, Graphics g);
	
	/**
	 * 计算一共有多少帧
	 * @return 返回总共的帧数，-1 表示无限制
	 */
	public int frameLength();
	
	/**
	 * 重置到开始位置
	 */
	public void reset();
	
	/**
	 * 重画当前预览帧
	 */
	public void refreshPreview();
	
	/**
	 * 获取最后一帧
	 * @return 最后一帧的帧数
	 */
	public int lastFrame();
	
	/**
	 * 初始化渲染
	 * @param g 要初始化的 Graphics
	 */
	public void initRender(Graphics g);
	
	
	public int getWidth();
	
	public int getHeight();

	/**
	 * 是否渲染音频
	 */
	public boolean isRenderAudio();
	
	/**
	 * 渲染音频
	 * @param track 渲染到此 AudioTrack 上
	 */
	public void renderAudio(AudioTrack track, int fps);
}
