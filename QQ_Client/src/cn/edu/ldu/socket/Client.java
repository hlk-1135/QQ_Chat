package cn.edu.ldu.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import cn.edu.ldu.util.CommandTranser;

/**
 * 客户端代码
 */
public class Client {
	private int port = 8081;
	private String address = "127.0.0.1";
	private Socket socket;

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	// 实例化时 建立连接
	public Client() {
		try {
			socket = new Socket(address, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "服务端未开启");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "服务端未开启");
		}
	}

	// 向服务端发送数据
	public void sendData(CommandTranser msg) {
		ObjectOutputStream oos = null;
		try {
			if (socket == null)
				return;
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(msg);
		} catch (UnknownHostException e1) {
			JOptionPane.showMessageDialog(null, "服务端未开启");
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "服务端未开启");
		}
	}

	// 向服务端接收数据
	public CommandTranser getData() {
		ObjectInputStream ois = null;
		CommandTranser msg = null;
		if (socket == null)
			return null;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			msg = (CommandTranser) ois.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
		if ("message".equals(msg.getCmd()))
			System.out.println((String) msg.getData());
		return msg;
	}
}