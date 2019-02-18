package audio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import utils.Bytes;

public class AudioClip
{
	private short[] datas;
	private byte[] rawDatas;
	private int sampleRate;
	private int position = -1;
	
	/**
	 * ����Ƶ���ݴ���AudioClip
	 * @param buffer 16λ��˫����ԭʼ��Ƶ����
	 */
	public AudioClip(byte[] buffer, int sampleRate)
	{
		rawDatas = buffer;
		this.sampleRate = sampleRate;
		readFromBytes(buffer);
	}
	
	private void readFromBytes(byte[] buffer)
	{
		datas = Bytes.bytesToShort(buffer);
	}

	
	public void updateRawDatas()
	{
		rawDatas = Bytes.shortToBytes(datas);
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

	public void setPosition(int positionInSecond)
	{
		//������ �� �� * ������ * ͨ������
		int pos = positionInSecond * sampleRate * 2;
		this.position = pos;
	}
	
}
