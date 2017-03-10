package cn.edu.ldu.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import cn.edu.ldu.socket.Client;

/**
 * 好友列表界面
 */
public class FriendsUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel friend_pal, stranger_pal;
	private JScrollPane jsp;
	private String owner;
	private JLabel nickname[];
	private JTabbedPane jtp;
	private Client client;

	public FriendsUI(String owner, Client client) {
		this.owner = owner;
		this.client = client;
		init();
		setTitle("Hi," + owner);
		setSize(220, 600);
		setLocation(1100, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	private void init() {
		// TODO Auto-generated method stub
		jtp = new JTabbedPane();
		friend_pal = new JPanel();
		stranger_pal = new JPanel();
		friend_pal.setLayout(new GridLayout(50, 1, 4, 4));
		nickname = new JLabel[50];
		
		for (int i = 1000; i < nickname.length + 1000; i++) {
			Random r=new Random();
			int j=r.nextInt(5);
			ImageIcon icon = new ImageIcon("image/head"+j+".jpg");
			icon.setImage(icon.getImage().getScaledInstance(60, 60,
					Image.SCALE_DEFAULT));
			nickname[i - 1000] = new JLabel(i + "", icon, JLabel.CENTER);
			nickname[i - 1000].addMouseListener(new MyMouseListener());
			friend_pal.add(nickname[i - 1000]);

		}
		jsp = new JScrollPane(friend_pal);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jtp.add("我的好友", jsp);
		jtp.add("在线好友", stranger_pal);
		add(jtp);
	}

	class MyMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			// 如果双击了两次 我的好友 弹出与这个好友的聊天框
			if (e.getClickCount() == 2) {
				JLabel label = (JLabel) e.getSource();
				new ChatUI(owner, label.getText(), client);
			}
		}

		// 如果鼠标进入我的好友列表 背景色变色
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			JLabel label = (JLabel) e.getSource();
			label.setOpaque(true);
			label.setBackground(new Color(255, 240, 230));
		}

		// 如果鼠标退出我的好友列表 背景色变色
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			JLabel label = (JLabel) e.getSource();
			label.setOpaque(false);
			label.setBackground(Color.WHITE);
		}
	}
}