package framework.utils;

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
				0x00000003,//dwOpenMode (0x00000003 双向管道)
				0x00000000,//dwPipeMode (0x00000000 PIPE_TYPE_BYTE)
				1,//nMaxInstances (可以为此管道创建的最大实例数 1-255)
				512,//nOutBufferSize (输出缓冲区大小)
				512,//nInBufferSize (输入缓冲区大小)
				0,//nDefaultTimeOut (超时时间，0为默认值50ms)
				null//lpSecurityAttributes (null)
				);
		if(hwnd == Kernel32.INVALID_HANDLE_VALUE)
			throw new Exception("Can't create pipe: invalid handle value");//检测句柄有效性
	}
	
	/**
	 * 等待管道被连接，没有被连接之前不会返回
	 */
	public void waitForConnect()
	{
		Kernel32.INSTANCE.ConnectNamedPipe(
				hwnd,
				null
				);
	}
	
	/**
	 * 向管道里写入数据
	 * @param buffer 要写入的数据
	 * @return 是否写入成功
	 */
	public boolean write(byte[] buffer)
	{
		return Kernel32.INSTANCE.WriteFile(
				hwnd,//hFile (句柄)
				buffer,//lpBuffer (要写入的内容)
				buffer.length,//nNumberOfBytesToRead
				new IntByReference(),//lpNumberOfBytesRead
				null//lpOverlapped
				);
	}
	
	/**
	 * 从管道里读入数据
	 * @param buffer 读入的数据
	 * @param length 要读入的长度
	 * @return 是否读入成功
	 */
	public boolean read(byte[] buffer, int length)
	{
		return Kernel32.INSTANCE.ReadFile(hwnd, buffer, length, new IntByReference(), null);
	}
	
	/**
	 * 关闭管道
	 */
	public void close()
	{
		Kernel32.INSTANCE.CloseHandle(hwnd);//关闭管道
	}
	
	/**
	 * 获取管道的名称
	 * @return 管道名称
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * 获取管道的实际路径，例如“\\.\pipe\name”
	 * @return 管道的实际路径
	 */
	public String getFullPath()
	{
		return "\\\\.\\pipe\\" + getName();
	}
}
