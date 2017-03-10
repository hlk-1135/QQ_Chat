package cn.edu.ldu.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import cn.edu.ldu.bean.User;
import cn.edu.ldu.socket.Client;
import cn.edu.ldu.util.CommandTranser;

/**
 *用户注册界面
 */

public class RegisterUI extends JFrame implements ActionListener,ItemListener{

	private static final long serialVersionUID = 1L;
	private JFrame app;
	private JTextField rname,telephone;
	private JPasswordField passw,second_passw;
	private JRadioButton boy,girl;
	private JButton name,next,again;
	
	public RegisterUI(){
		app=new JFrame("qq账号注册");
		app.setSize(600,500);
		app.setLocation(300,140);
		app.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container c=app.getContentPane();
		c.setLayout(new GridLayout(1,2));
		JPanel p1=new JPanel();
		p1.setLayout(new GridLayout(10,1,0,10));
		
		p1.add(new JLabel("< 注册提醒   >"));
		p1.add(new JLabel("1.请按要求填写"));
		p1.add(new JLabel("2.qq号码随机生成"));
		p1.add(new JLabel("---------------------------"));
		p1.add(new JLabel("< 版权所有   >"));
		p1.add(new JLabel("  软工1403"));
		p1.add(new JLabel("    史坤"));
		p1.add(new JLabel("20142203717"));
		c.add(p1);
		JPanel p2=new JPanel();
		JPanel p3=new JPanel();
		c.add(p2);
		c.add(p3);
		p2.setLayout(new GridLayout(10,1,0,10));
		p3.setLayout(new GridLayout(10,1,0,10));
		p2.add(new JLabel("真实姓名"));
		rname=new JTextField(10);
		p3.add(rname);
		
		p2.add(new JLabel("性别"));
		ButtonGroup sex=new ButtonGroup();
		boy=new JRadioButton("男",true);
		sex.add(boy);
		girl=new JRadioButton("女",true);
		sex.add(girl);
		JPanel p4=new JPanel();
		p4.setLayout(new GridLayout(1,2));
		p4.add(boy);
		p4.add(girl);
		p3.add(p4);
		p2.add(new JLabel("密码"));
		passw=new JPasswordField(10);
		p3.add(passw);
		p2.add(new JLabel("确认密码"));
		second_passw=new JPasswordField(10);
		p3.add(second_passw);
		p2.add(new JLabel("qq账号："));
		name=new JButton();
		p3.add(name);
		p2.add(new JLabel("联系电话："));
		telephone=new JTextField(10);
		p3.add(telephone);
		next=new JButton("完成注册");
		p2.add(next);
		next.addActionListener(this);
		again=new JButton("重新生成账号");
		p3.add(again);
		
		again.addActionListener(this);
		app.setVisible(true);
		
		//qq号码是随机生成
		int n = (int) Math.round(Math.random() * 49 + 1000); ;
		String str = String.valueOf(n);
		name.setText(str);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO 自动生成的方法存根	
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==next){
			//获取输入的信息
			String realname = rname.getText().trim();
			String username = name.getText().trim();
			String password = new String(passw.getPassword()).trim();
			String second_password = new String(second_passw.getPassword()).trim();
			String phone = telephone.getText().trim();
			String sex="男";
			if(boy.isSelected())
				sex=boy.getText();
			if(girl.isSelected())
				sex=girl.getText();
			
			if ("".equals(realname) || realname == null) {
				JOptionPane.showMessageDialog(null, "请输入真实姓名！！");
				return;
			}
			if ("".equals(password) || password == null) {
				JOptionPane.showMessageDialog(this, "请输入密码！！","系统提示",JOptionPane.WARNING_MESSAGE);
				return;
			}
			if ("".equals(second_password) || second_password == null) {
				JOptionPane.showMessageDialog(this, "请再次输入密码！！","系统提示",JOptionPane.WARNING_MESSAGE);
				return;
			}
			if ("".equals(username) || username == null) {
				JOptionPane.showMessageDialog(this, "请输入帐号！！","系统提示",JOptionPane.WARNING_MESSAGE);
				return;
			}
			if ("".equals(phone) || phone == null) {
				JOptionPane.showMessageDialog(this,"请输入手机号！！","系统提示",JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(password.equals(second_password)) {
				User user = new User(username, password,realname,sex,phone);
				CommandTranser msg = new CommandTranser();
				//注册之前，先验证一下该数据库中是否存在该用户
				msg.setCmd("checkregist");
				msg.setData(user);
				msg.setReceiver(username);
				msg.setSender(username);
				//实例化客户端,并且发送数据,这个client客户端直到进程死亡
				Client client = new Client();
				client.sendData(msg);
				msg = client.getData();		
				if (msg != null) {
					if (msg.isFlag()==false) { //验证该账号未注册
						//this.dispose();
						System.out.println(msg.isFlag());
						msg.setCmd("regist");
						msg.setData(user);
						msg.setReceiver(username);
						msg.setSender(username);
						//实例化客户端并且发送数据，该client客户端直到进程死亡
						Client c = new Client();
						c.sendData(msg);
						msg = c.getData();
						if (msg.isFlag()==true) {
							this.dispose();
						JOptionPane.showMessageDialog(null,"注册成功！","系统提示",JOptionPane.WARNING_MESSAGE);
							// 显示登录界面
							app.setVisible(false);
							new StartClient();
						}
					} else {
						System.out.println(msg.isFlag());
						JOptionPane.showMessageDialog(null,"该账号已存在！","系统提示",JOptionPane.WARNING_MESSAGE);
						app.setVisible(false);
						new RegisterUI();
					}
				}
			} else {
				JOptionPane.showMessageDialog(this,"两次输入密码不同，请重新输入","系统提示",JOptionPane.WARNING_MESSAGE);
				second_passw.setText("");
				this.setVisible(false);
			}
		}
		
		if(e.getSource()==again){ //点击重新生成按钮
			app.setVisible(false);
			new RegisterUI();
		}
	}
	public static void main(String[] args) {
		new RegisterUI();
	}
}