package cn.edu.ldu.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.edu.ldu.socket.Client;
import cn.edu.ldu.socket.ClientThread;
import cn.edu.ldu.util.CommandTranser;

/**
 * 聊天对话框
 */
public class ChatUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextArea chat_txt;
	private JTextField message_txt;
	private JButton send_btn;
	private JPanel panel;
	private String owner;
	private String friend;
	private Client client;
	private ClientThread thread;// 接收信息线程

	public ChatUI(String owner, String friend, Client client) {
		this.owner = owner;
		this.friend = friend;
		this.client = client;
		init();
		setTitle("与" + friend + "聊天中");
		setSize(350, 350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		// 开启客户端接收信息线程
		thread = new ClientThread(client, chat_txt);
		thread.start();
	}

	private void init() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		panel = new JPanel();
		message_txt = new JTextField(20);
		send_btn = new JButton("发送");
		panel.add(message_txt);
		panel.add(send_btn);
		chat_txt = new JTextArea();
		chat_txt.setEditable(false);
		chat_txt.add(new JScrollBar(JScrollBar.VERTICAL));
		add(chat_txt, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		send_btn.addActionListener(this);
		// 添加窗口关闭事件
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				thread.setOnline(false);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				thread.setOnline(false);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// 如果点击了发送按钮
		if (e.getSource() == send_btn) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");

			String message = "你说：" + message_txt.getText() + "\t"
					+ sdf.format(date) + "\n";
			// 在本地文本区追加发送的信息
			chat_txt.append(message);
			// msg为客户端向服务器发送的数据
			CommandTranser msg = new CommandTranser();
			msg.setCmd("message");
			msg.setSender(owner);
			msg.setReceiver(friend);
			msg.setData(message_txt.getText());

			client.sendData(msg);
			// 发送信息完毕 写信息的文本框设空
			message_txt.setText(null);
		}
	}

}