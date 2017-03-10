package cn.edu.ldu.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import cn.edu.ldu.bean.User;
import cn.edu.ldu.service.UserService;
import cn.edu.ldu.util.CommandTranser;
import cn.edu.ldu.util.SocketList;

/**
 *  服务器线程
 */
public class ServerThread extends Thread {
	private Socket socket;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		// 时刻监听 客户端发送来的数据
		while (socket != null) {
			try {
				ois = new ObjectInputStream(socket.getInputStream());
				CommandTranser msg = (CommandTranser) ois.readObject();
				// 处理客户端发送来的信息
				msg = execute(msg);
				if ("message".equals(msg.getCmd())) {
					/*
					 * 如果 msg.ifFlag即 服务器处理成功,可以向该好友发送信息;如果服务器处理信息失败,信息发送给发送者本人
					 */
					if (msg.isFlag()) {
						oos = new ObjectOutputStream(SocketList.getSocket(
								msg.getReceiver()).getOutputStream());
					} else {
						oos = new ObjectOutputStream(socket.getOutputStream());
					}
				}
				// 如果是登录请求 发送给发送者本人
				if ("login".equals(msg.getCmd())) {
					oos = new ObjectOutputStream(socket.getOutputStream());
				}
				if ("checkregist".equals(msg.getCmd())) {
					System.out.println("验证成功");
					oos = new ObjectOutputStream(socket.getOutputStream());
				}
				if ("regist".equals(msg.getCmd())) {
					System.out.println("注册成功");
					oos = new ObjectOutputStream(socket.getOutputStream());
				}
				oos.writeObject(msg);
			} catch (IOException e) {
				socket = null;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	// 处理客户端发送的信息
	private CommandTranser execute(CommandTranser msg) {
		// 如果是注册验证请求
		if ("checkregist".equals(msg.getCmd())) {
			UserService userService = new UserService();
			User user = (User) msg.getData();
			userService.checkregistUser(user);
			msg.setFlag(userService.checkregistUser(user));
			if(msg.isFlag()) {
				msg.setResult("用户已存在！");
			} else {
				System.out.println("允许注册！");
			}
		}	
		// 如果是完成注册请求
		if ("regist".equals(msg.getCmd())) {
			UserService userService = new UserService();
			User user = (User) msg.getData();
			userService.registUser(user);
			msg.setFlag(true);
			msg.setResult("注册成功");
		}
		// 如果是登录请求
		if ("login".equals(msg.getCmd())) {
			UserService userService = new UserService();
			User user = (User) msg.getData();
			msg.setFlag(userService.checkUser(user));
			/*
			 * 如果登陆成功，将该客户端加入已经连接成功的map集合里面,并且开启此用户的接受线程
			 */
			if (msg.isFlag()) {
				// 将该线程加入连接成功的map集合
				SocketThread socketThread = new SocketThread();
				socketThread.setName(msg.getSender());
				socketThread.setSocket(socket);
				SocketList.addSocket(socketThread);
				msg.setResult("登陆成功");
			} else {
				msg.setResult("账号密码输入错误！");
			}
		}

		// 如果是发送消息的指令 判断当前用户是否在线
		if ("message".equals(msg.getCmd())) {
			// 如果要发送的用户在线 发送信息
			if (SocketList.getSocket(msg.getReceiver()) != null) {
				msg.setFlag(true);
			} else {
				msg.setFlag(false);
				msg.setResult("当前用户不在线");
			}
		}
		
		return msg;
	}
}