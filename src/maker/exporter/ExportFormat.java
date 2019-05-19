package maker.exporter;

/**
 * 导出动画时使用的格式
 * @author XcantloadX
 *
 */
public enum ExportFormat
{
	PNG("Picture(*.png)", 0),
	GIF("Animated Picture(*.gif)", 1),
	MP4("Video(*.mp4)", 2),
	WAV("Audio(*.wav)", 3);
	
	private String value;
	private int index;
	
	private ExportFormat(String value, int index)
	{
		this.value = value;
		this.index = index;
	}
	
	@Override
	public String toString()
	{
		return value;
	}
	
	/**
	 * 获取该格式的文件后缀名
	 * @return 返回文件后缀名，不包括“.”
	 */
	public String getSuffix()
	{
		switch (this)
		{
		case PNG:
			return "png";
		case GIF:
			return "gif";
		case MP4:
			return "mp4";
		case WAV:
			return "wav";

		default:
			return null;
		}
	}
}
