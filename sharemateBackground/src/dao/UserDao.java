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
	 * 查询用户点赞的所有评论id(新增)
	 */
	public List<Integer> getLikeComment(int userId){
		List<Integer> list=new ArrayList<Integer>();
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		String sql="select comment_id from like_comment where user_id=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt("comment_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
	
		return list;
	}
	/**
	 * 查询用户点赞的所有回复id(新增)
	 */
	public List<Integer> getLikeReply(int userId){
		List<Integer> list=new ArrayList<Integer>();
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		String sql="select reply_id from like_reply where user_id=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt("reply_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
	
		return list;
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
	 * 修改用户的姓名
	 */
	public void setUserName(UserBean userbean,String name) {
		Connection con = DataBase.getConnection();
		String sql = "update user set user_name = ? where user_id = ?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setString(1,name);
			ptmt.setInt(2,userbean.getUserId());
			int row = ptmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改用户的性别
	 */
	public void setUserSex(UserBean userbean,String sex) {
		Connection con = DataBase.getConnection();
		String sql = "update user set user_sex = ? where user_id = ?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setString(1,sex);
			ptmt.setInt(2,userbean.getUserId());
			int row = ptmt.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改用户的生日
	 */
	public void setUserBirth(UserBean userbean,Date date) {
		Connection con = DataBase.getConnection();
		String sql = "update user set user_birth = ? where user_id = ?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setDate(1,new java.sql.Date(date.getTime()));
			ptmt.setInt(2,userbean.getUserId());
			int row = ptmt.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改用户的地址
	 */
	public void setUserAddress(UserBean userbean,String address) {
		Connection con = DataBase.getConnection();
		String sql = "update user set user_address = ? where user_id = ?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setString(1,address);
			ptmt.setInt(2,userbean.getUserId());
			int row = ptmt.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改用户的个性签名o
	 */
	public void setUserIntro(UserBean userbean,String intro) {
		Connection con = DataBase.getConnection();
		String sql = "update user set user_intro = ? where user_id = ?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setString(1,intro);
			ptmt.setInt(2,userbean.getUserId());
			int row = ptmt.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改用户的头像（保存的是头像在安卓手机数据库中的路径）
	 */
	public void setUserHead(UserBean userbean,String head) {
		Connection con = DataBase.getConnection();
		String sql = "update user set user_photo = ? where user_id = ?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setString(1,head);
			ptmt.setInt(2,userbean.getUserId());
			int row = ptmt.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
