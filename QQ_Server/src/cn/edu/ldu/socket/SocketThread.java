package cn.edu.ldu.socket;

import java.net.Socket;

/**
 * 所有成功连接的socket实体类 包括一个socket，一个用户名(即账号)
 */
public class SocketThread {
	private Socket socket;
	private String name;

	public SocketThread() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SocketThread(Socket socket, String name) {
		super();
		this.socket = socket;
		this.name = name;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}