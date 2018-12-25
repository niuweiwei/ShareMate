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
		String sql = "insert into user(user_id,user_name,user_password,user_photo,user_sex,user_phone,user_birth,user_address,user_intro) values(0,?,?,?,?,?,?,?,?)";
	PreparedStatement pstmt = null;
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getUserPassword());
		pstmt.setString(3, user.getUserPhoto());
		pstmt.setString(4, user.getUserSex());
		pstmt.setString(5, user.getUserPhone());
		pstmt.setDate(6, (Date) user.getUserBirth());
		pstmt.setString(7, user.getUserAddress());
		pstmt.setString(8, user.getUserIntro());
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
	 * 得到用户数量
	 */
	public int getUserCount() {
		int count = 0;
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) u from user";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				count = rs.getInt("u");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(rs);
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		return count;
	}
	/**
	 * 得到新插入的用户的userId
	 */
	public int getLastUserId() {
		int userId = 0;
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select max(user_id) from user";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				userId = rs.getInt("max(user_id)");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(rs);
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		return userId;
	}
	/**
	 * 根据得到的新插入用户的userId插入
	 */
	public void insertUserNext(UserBean user,int userId) {
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		String sql = "update user set user_sex=?,user_birth=? where user_id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserSex());
			pstmt.setDate(2, new java.sql.Date(user.getUserBirth().getTime()));
			pstmt.setInt(3, userId);
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
	 * 根据用户名和手机号判断用户是否注册过; 注册过返回true，没有注册过返回false
	 */
	public boolean isRegisterByNameOrPhone(UserBean user) {
		int userId = 0;
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select user_id from user where user_name=? or user_phone=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getUserPhone());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				userId = rs.getInt("user_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(rs);
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		if(userId != 0) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 根据输入的手机号查询用户是否存在
	 */
	public boolean isUserExistByPhone(String userPhone) {
		int userId = 0;
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select user_id from user where user_phone=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userPhone);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				userId = rs.getInt("user_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(rs);
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		if(userId != 0) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 根据输入的手机号和密码查询用户是否存在
	 */
	public boolean isUserExistByPhoneAndPassword(String userPhone,String userPassword) {
		int userId = 0;
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select user_id from user where user_phone=? and user_password=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userPhone);
			pstmt.setString(2, userPassword);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				userId = rs.getInt("user_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(rs);
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		if(userId != 0) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 往title表中插入数据
	 */
	public void insertTitle(int userId,int typeId) {
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		String sql = "insert into title(user_id,type_id) values(?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, typeId);
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
	 * 根据输入的手机号查询userId
	 */
	public int getUserIdByPhone(String userPhone) {
		int userId = 0;
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select user_id from user where user_phone=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userPhone);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				userId = rs.getInt("user_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(rs);
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		return userId;
	}
	/**
	 * 根据输入的手机号和密码查询userId
	 */
	public int getUserIdByPhoneAndPassword(String userPhone,String userPassword) {
		int userId = 0;
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select user_id from user where user_phone=? and user_password=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userPhone);
			pstmt.setString(2, userPassword);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				userId = rs.getInt("user_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(rs);
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		return userId;
	}
	/**
	 * 删除Title表中的数据
	 */
	public void deleteTitle(int userId,int typeId) {
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		String sql = "delete from title where user_id=? and type_id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, typeId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
	}
}

