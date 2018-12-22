package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dao.DataBase;
import bean.UserBean;

public class UserDao {
	
	/**
	 * 根据用户id得到用户
	 */
	public UserBean getUserById(int userId) {
		UserBean user = new UserBean();
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		String sql="select * from user where user_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				user = new UserBean(
						userId,
						rs.getString("user_name"),
						rs.getString("user_password"),
						rs.getString("user_photo"),
						rs.getString("user_sex"),
						rs.getString("user_phone"),
						rs.getString("user_address"),
						rs.getDate("user_birth"),
						rs.getString("user_intro")
					);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DataBase.close(pstmt);
			DataBase.close(conn);
		    }
		return user;
	}
	
	/**
	 * 得到所有用户
	 */
	public List<UserBean> getAllUser(){
		List<UserBean> userlist = new ArrayList<UserBean>();
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		String sql="select * from user";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				UserBean userBean = new UserBean(
						rs.getInt("user_id"),
						rs.getString("user_name"),
						rs.getString("user_password"),
						rs.getString("user_photo"),
						rs.getString("user_sex"),
						rs.getString("user_phone"),
						rs.getString("user_address"),
						rs.getDate("user_birth"),
						rs.getString("user_intro")
					);
			    userlist.add(userBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DataBase.close(pstmt);
			DataBase.close(conn);
		    }
		return userlist;
	}
	
	/**
	 * 插入用户
	 */
	public void insertUser(UserBean user) {
		Connection conn = DataBase.getConnection();
		String sql = "insert into user(user_id,user_name,user_password,user_photo,user_sex,user_phone,user_birth) values(0,?,?,?,?,?,?)";
	PreparedStatement pstmt = null;
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getUserPassword());
		pstmt.setString(3, user.getUserPhoto());
		pstmt.setString(4, user.getUserSex());
		pstmt.setString(5, user.getUserPhone());
		pstmt.setDate(6, (Date) user.getUserBirth());
		pstmt.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
	}
	
	/**
	 * 修改用户的信息
	 */
	public void setUserInfom(UserBean user) {
		Connection con = DataBase.getConnection();
		String sql = "update user set user_name = ?,user_sex=?,user_birth=?,user_address=?,user_intro=? where user_id = ?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setString(1,user.getUserName());
			ptmt.setString(2,user.getUserSex());
			ptmt.setDate(3, (Date)user.getUserBirth());
			ptmt.setString(4, user.getUserAddress());
			ptmt.setString(5, user.getUserIntro());
			ptmt.setInt(6, user.getUserId());
			int row = ptmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改用户的头像（保存的是头像在安卓手机数据库中的路径）
	 */
	public void setUserHead(int userId,String head) {
		Connection con = DataBase.getConnection();
		String sql = "update user set user_photo = ? where user_id = ?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setString(1,head);
			ptmt.setInt(2,userId);
			int row = ptmt.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
