package audio_old;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import utils.BytesUtil;

public class AudioClip
{
	private short[] datas;
	private byte[] rawDatas;
	private int sampleRate;
	private int position = -1;
	
	/**
	 * 从音频数据创建AudioClip
	 * @param buffer 16位、双声道原始音频数据
	 */
	public AudioClip(byte[] buffer, int sampleRate)
	{
		rawDatas = buffer;
		this.sampleRate = sampleRate;
		readFromBytes(buffer);
	}
	
	private void readFromBytes(byte[] buffer)
	{
		datas = BytesUtil.bytesToShort(buffer);
	}

	
	public void updateRawDatas()
	{
		rawDatas = BytesUtil.shortToBytes(datas);
	}
	
	public short[] getDatas()
	{
		return datas;
	}

	
	public void setDatas(short[] datas)
	{
		this.datas = datas;
	}

	public byte[] getRawDatas()
	{
		return rawDatas;
	}

	public int getSampleRate()
	{
		return sampleRate;
	}

	public int getPosition()
	{
		return position;
	}
	
	public float getPositionInSec()
	{
		return position / sampleRate / 2;
	}

	public void setPosition(float positionInSecond)
	{
		//样本数 ＝ 秒 * 采样率 * 通道数量
		int pos = (int) (positionInSecond * sampleRate * 2);
		this.position = pos;
	}
	
	
}
