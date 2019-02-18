package utils;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

public class NamedPipe
{
	private String name;
	private HANDLE hwnd;
	
	public NamedPipe(String pipeName) throws Exception
	{
		name = pipeName;
		System.out.println("[Pipe]New pipe: " + getFullPath());
		hwnd = Kernel32.INSTANCE.CreateNamedPipe(
				getFullPath(),
				0x00000003,//dwOpenMode (0x00000003 ˫��ܵ�)
				0x00000000,//dwPipeMode (0x00000000 PIPE_TYPE_BYTE)
				1,//nMaxInstances (����Ϊ�˹ܵ����������ʵ���� 1-255)
				512,//nOutBufferSize (�����������С)
				512,//nInBufferSize (���뻺������С)
				0,//nDefaultTimeOut (��ʱʱ�䣬0ΪĬ��ֵ50ms)
				null//lpSecurityAttributes (null)
				);
		if(hwnd == Kernel32.INVALID_HANDLE_VALUE)
			throw new Exception("Can't create pipe: invalid handle value");//�������Ч��
	}
	
	/**
	 * �ȴ��ܵ������ӣ�û�б�����֮ǰ���᷵��
	 */
	public void waitForConnect()
	{
		Kernel32.INSTANCE.ConnectNamedPipe(
				hwnd,
				null
				);
	}
	
	/**
	 * ��ܵ���д������
	 * @param buffer Ҫд�������
	 * @return �Ƿ�д��ɹ�
	 */
	public boolean write(byte[] buffer)
	{
		return Kernel32.INSTANCE.WriteFile(
				hwnd,//hFile (���)
				buffer,//lpBuffer (Ҫд�������)
				buffer.length,//nNumberOfBytesToRead
				new IntByReference(),//lpNumberOfBytesRead
				null//lpOverlapped
				);
	}
	
	/**
	 * �ӹܵ����������
	 * @param buffer ���������
	 * @param length Ҫ����ĳ���
	 * @return �Ƿ����ɹ�
	 */
	public boolean read(byte[] buffer, int length)
	{
		return Kernel32.INSTANCE.ReadFile(hwnd, buffer, length, new IntByReference(), null);
	}
	
	/**
	 * �رչܵ�
	 */
	public void close()
	{
		Kernel32.INSTANCE.CloseHandle(hwnd);//�رչܵ�
	}
	
	/**
	 * ��ȡ�ܵ�������
	 * @return �ܵ�����
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * ��ȡ�ܵ���ʵ��·�������硰\\.\pipe\name��
	 * @return �ܵ���ʵ��·��
	 */
	public String getFullPath()
	{
		return "\\\\.\\pipe\\" + getName();
	}
}
