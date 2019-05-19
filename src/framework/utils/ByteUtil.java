package framework.utils;

public class ByteUtil
{
	/**
	 * 转换 short 到 byte[]
	 * @param s 待转换的 short 类型数据
	 * @param bytes 转换后得到的 byte[]
	 * @param index bytes 的偏移量
	 */
	public static void getBytes(short s, byte[] bytes, int index)
	{
        bytes[index + 1] = (byte) (s >> 8);
        bytes[index + 0] = (byte) (s >> 0);
	}
	
	/**
	 * 将 byte[] 转换到 short
	 * @param b 待转换的byte[]
	 * @param offset 偏移位置，将会从这里读取4字节来转换
	 * @return 转换后的short
	 */
	public static short getShort(byte[] b, int offset)
	{
		//TODO 写更多注释（理解）
	    return (short) (((b[offset + 1] << 8) | b[offset + 0] & 0xff));
	}
}
