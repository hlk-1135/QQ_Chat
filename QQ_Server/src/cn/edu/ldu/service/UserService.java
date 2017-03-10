package cn.edu.ldu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cn.edu.ldu.bean.User;
import cn.edu.ldu.util.DBHelper;

/**
 * 客户端的操作处理
 */
public class UserService {
	
	//登录时，查询是否存在该账号
	public boolean checkUser(User user) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		conn = DBHelper.getConnection();
		String sql = "select * from user where username=? and password =?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	//注册时，查询是否已经注册该账号
	public boolean checkregistUser(User user) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		conn = DBHelper.getConnection();
		String sql = "select * from user where username=?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	//完成用户的注册
	public boolean registUser(User user) {
		PreparedStatement stmt = null;
		Connection conn = null;
		conn = DBHelper.getConnection();
		String sql = "insert into user(username,password,realname,sex,phone) values (?,?,?,?,?)";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getRealname());
			stmt.setString(4, user.getSex());
			stmt.setString(5, user.getPhone());
			stmt.executeUpdate();;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
}