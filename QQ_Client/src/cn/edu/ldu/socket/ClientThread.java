package cn.edu.ldu.socket;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import cn.edu.ldu.util.CommandTranser;


/**
 * 客户端线程类。一旦聊天启动，为其开启一个线程 
 * I/O阻塞接收服务端发送的数据
 */
public class ClientThread extends Thread {
	private Client client;//客户端对象
	private boolean isOnline = true;//当前聊天框是否存在
	private JTextArea chat_txt;//聊天框

	public ClientThread(Client client, JTextArea chat_txt) {
		this.client = client;
		this.chat_txt = chat_txt;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	@Override
	public void run() {
		while (isOnline) {
			//I/O阻塞  接收服务端发送的数据
			CommandTranser msg = client.getData();
			if (msg != null) {
				/*
				 * 如果服务端处理数据成功，接收信息
				 * 否则弹出对方不在线的对话框
				 */
				if (msg.isFlag()) {
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat(
							"hh:mm:ss a");
					String message = msg.getSender() + "说："
							+ (String) msg.getData() + "\t" + sdf.format(date)
							+ "\n";
					// 在聊天框添加收到的信息
					chat_txt.append(message);
				} else {
					JOptionPane.showMessageDialog(chat_txt, msg.getResult());
				}

			}
		}
	}
}