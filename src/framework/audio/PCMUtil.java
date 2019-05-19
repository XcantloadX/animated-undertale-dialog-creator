package framework.audio;

public class PCMUtil
{
	/**
	 * 检查 short 类型的数据溢出，并限制数据大小到 short 类型的范围
	 * @param l 要检查的数据
	 * @return 检查后的数据
	 */
	public static short checkShort(long l)
	{
		if(l > Short.MAX_VALUE)
			l = Short.MAX_VALUE;
		if(l < Short.MIN_VALUE)
			l = Short.MIN_VALUE;
		return (short)l;
	}
	
	/**
	 * 将多个 short 音频数据混合
	 * @param s 音频数据
	 * @return 混合后的数据
	 */
	public static short mixShort(short... s) 
	{
		long temp = 0;
		for (int i = 0; i < s.length; i++)
		{
			temp += s[i];
		}
		temp = PCMUtil.checkShort(temp);
		return (short)temp;
	}
}
