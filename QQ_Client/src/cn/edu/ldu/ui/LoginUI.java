package cn.edu.ldu.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import cn.edu.ldu.bean.User;
import cn.edu.ldu.socket.Client;
import cn.edu.ldu.util.CommandTranser;

/**
 * 客户端登录界面
 */
class LoginUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JLabel bq_North, bq_name, bq_pwd;
	private JButton login,sweep,regist;
	private JTextField text_name;
	private JPasswordField text_pwd;
	private JTabbedPane choose;
	private JCheckBox steal_login, mark_name;
	private JPanel choose1, login_South;

	public LoginUI() {
		ImageIcon logo = new ImageIcon("image/logo.png");
		logo.setImage(logo.getImage().getScaledInstance(300, 100,
				Image.SCALE_DEFAULT));
		bq_North = new JLabel(logo);
		login_South = new JPanel();
		login = new JButton();
		ImageIcon login_btn = new ImageIcon("image/login_btn.png");
		login_btn.setImage(login_btn.getImage().getScaledInstance(200, 25, Image.SCALE_DEFAULT));
		login.setIcon(login_btn);
		login.setBorderPainted(false);
		login.setBorder(null);
		login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		login_South.add(login);
		
		choose = new JTabbedPane();
		choose1 = new JPanel();
		choose.add("普通用户", choose1);
		choose1.setLayout(new GridLayout(3, 3));
		bq_name = new JLabel("QQ账号", JLabel.CENTER);
		bq_pwd = new JLabel("QQ密码", JLabel.CENTER);
		sweep = new JButton("清除号码");
		sweep.setForeground(Color.red);
		regist = new JButton("注册账号");
		text_name = new JTextField();
		text_pwd = new JPasswordField();
		regist = new JButton("注册账号");
		regist.setForeground(Color.blue);
		steal_login = new JCheckBox("隐身登陆");
		mark_name = new JCheckBox("记住密码");
		choose1.setLayout(new GridLayout(3, 3));
		choose1.add(bq_name);
		choose1.add(text_name);
		choose1.add(sweep);
		choose1.add(bq_pwd);
		choose1.add(text_pwd);
		choose1.add(regist);
		choose1.add(steal_login);
		choose1.add(mark_name);
		add(choose, BorderLayout.CENTER);
		add(bq_North, BorderLayout.NORTH);
		add(login_South, BorderLayout.SOUTH);
		login.addActionListener(this);
		sweep.addActionListener(this);
		regist.addActionListener(this);
		ImageIcon tubiao = new ImageIcon("image/2.jpg");
		setIconImage(tubiao.getImage());
		setVisible(true);
		setBounds(340, 270, 300, 280);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("史坤");
	}

	public void actionPerformed(ActionEvent e) {
		/*
		 * 如果点击了登录按钮 首先判断帐号或者密码是否为空 然后封装为CommandTranser对象 向服务器发送数据 服务器通过与数据库的比对
		 * 来验证帐号密码
		 */
		if (e.getSource() == login) {
			String username = text_name.getText().trim();
			String password = new String(text_pwd.getPassword()).trim();
			if ("".equals(username) || username == null) {
				JOptionPane.showMessageDialog(null, "请输入帐号！！");
				return;
			}
			if ("".equals(password) || password == null) {
				JOptionPane.showMessageDialog(null, "请输入密码！！");
				return;
			}
			User user = new User(username, password);
			CommandTranser msg = new CommandTranser();
			msg.setCmd("login");
			msg.setData(user);
			msg.setReceiver(username);
			msg.setSender(username);
			// 实例化客户端 并且发送数据 这个client客户端 直到进程死亡 否则一直存在
			Client client = new Client();
			client.sendData(msg);
			msg = client.getData();
			if (msg != null) {
				if (msg.isFlag()) {
					this.dispose();
					JOptionPane.showMessageDialog(null, "登陆成功！");
					// 显示好友列表界面
					new FriendsUI(username, client);
				} else {
					JOptionPane.showMessageDialog(this, msg.getResult());
				}
			}

		} else if (e.getSource() == sweep) {
			text_name.setText(null);
			text_pwd.setText(null);
		} else if(e.getSource() == regist) {
			new RegisterUI();
		}

	}

	public static void main(String[] args) {
		new LoginUI();
	}
}
