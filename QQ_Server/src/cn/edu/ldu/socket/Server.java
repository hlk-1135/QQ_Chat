package cn.edu.ldu.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器端
 */
public class Server {
	public void startService() {
		try {
			ServerSocket ss = new ServerSocket(8081);
			Socket socket = null;
			// 循环监听客户端的连接,每连接一个客户端 就为其实例化一个线程
			// 在该线程 I/O阻塞监听客户端发送的信息,不然只能收到一次信息~
			while (true) {
				socket = ss.accept();
				ServerThread thread = new ServerThread(socket);
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}