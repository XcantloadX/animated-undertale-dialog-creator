package framework.audio;
import framework.utils.*;

public class PCMCoder
{
	public static final int SHORT_SIZE = 2;//是2，不是4！
	
	public static short[] decodeShort(byte[] buffer)
	{
		if(buffer.length % SHORT_SIZE != 0)
			throw new IllegalArgumentException("Invalid bytes length: " + buffer.length);
		
		short[] data = new short[buffer.length / SHORT_SIZE];
		
		//byte[]转换到short
		for (int i = 0; i < buffer.length; i += SHORT_SIZE)
		{
			data[i / SHORT_SIZE] = ByteUtil.getShort(buffer, i);
		}
		return data;
	}
	
	public static byte[] encoderShort(short[] buffer)
	{
		byte[] bytes = new byte[SHORT_SIZE * buffer.length];
		
		//short[]转换到byte[]
		for (int i = 0; i < buffer.length; i++)
		{
			ByteUtil.getBytes(buffer[i], bytes, i * SHORT_SIZE);
		}
		
		return bytes;
	}
}
