package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import bean.FollowBean;
import bean.UserBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FollowDao {
	
	/**
	 * 增加关注
	 */
	public void addFollow(int followId,int followedId) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="insert into follow values(?,?,NOW())"	;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,followId);
			pstmt.setInt(2, followedId);
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
	 * 取消关注
	 */
	public void deleteFollow(int followId,int followedId) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="delete from follow where follow_id=? and user_id=?"	;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,followId);
			pstmt.setInt(2, followedId);
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
	 * 获得关注的数量
	 */
	public int getFollowCount(int userId) {
		int followCount = 0;
		Connection con = DataBase.getConnection();
		String sql = "select count(*) c from follow where follow_id = ?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setInt(1,userId);
			ResultSet rs = ptmt.executeQuery();
			if(rs.next()) {
				followCount = rs.getInt("c");
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return followCount;
	}
	
	/**
	 * 获得关注用户的信息
	 */
	public List<FollowBean> getFollow(int userId) {
		List<FollowBean> followList = new ArrayList<>();
		UserDao userdao = new UserDao();
		Connection con = DataBase.getConnection();
		String sql = "select * from follow where follow_id = ?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setInt(1,userId);
			ResultSet rs = ptmt.executeQuery();
			while(rs.next()) {
				FollowBean followbean = new FollowBean();
				followbean.setUserbean(userdao.getUserById(rs.getInt("user_id")));
				followbean.setFollowId(rs.getInt("follow_id"));
				boolean b = this.eachFollow(followbean);
				followbean.setStatus(b);
				followList.add(followbean);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return followList;
	}
	
	/**
	 * 获取粉丝的数量
	 */
	public int getFunCount(int userId) {
		int funCount = 0;
		Connection con = DataBase.getConnection();
		String sql = "select count(*) c from follow where user_id = ?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setInt(1,userId);
			ResultSet rs = ptmt.executeQuery();
			if(rs.next()) {
				funCount = rs.getInt("c");
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return funCount;
	}
	/**
	 * 获得粉丝列表
	 */
		
	public List<FollowBean> getFanList(int userId){
		List<FollowBean> fanList = new ArrayList<>();
		UserDao userdao = new UserDao();
		Connection con = DataBase.getConnection();
		String sql = "select * from follow where user_id = ?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setInt(1,userId);
			ResultSet rs = ptmt.executeQuery();
			while(rs.next()) {
				FollowBean followbean = new FollowBean();
				followbean.setUserbean(userdao.getUserById(rs.getInt("follow_id")));
				followbean.setFollowId(rs.getInt("user_id"));
				boolean b = this.eachFan(followbean);
				followbean.setStatus(b);
				fanList.add(followbean);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fanList;
	}
	
	/**
	 * 判断是否是互相关注
	 */
	public boolean eachFollow(FollowBean followbean) {
		Connection con = DataBase.getConnection();
		String sql = "select * from follow where user_id = ? and follow_id = ?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setInt(1,followbean.getFollowId());
			ptmt.setInt(2,followbean.getUserbean().getUserId());
			ResultSet rs = ptmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean eachFan(FollowBean followbean) {
		Connection con = DataBase.getConnection();
		System.out.print(followbean.getFollowId()+"  "+followbean.getUserbean().getUserId());
		String sql = "select * from follow where follow_id = ? and user_id = ?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setInt(1,followbean.getFollowId());
			ptmt.setInt(2,followbean.getUserbean().getUserId());
			ResultSet rs = ptmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
		
	/**
	 * 根据当前用户的id 查询TA的粉丝有哪些 并且是否互相关注
	 * */
	public HashMap<FollowBean,Boolean> getFollows(int userId) {
		HashMap  <FollowBean,Boolean> fans = new HashMap<>();
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet result = null;
		ResultSet result1 = null;
		
		try {
			String sql = "select * from follow where user_id = ? order by follow_date desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			result = pstmt.executeQuery();
			while(result.next()) {
				int followId = result.getInt("follow_id");
				FollowBean follow = new FollowBean();
				follow.setFollowId(followId);
				follow.setUserbean(new UserDao().getUserById(userId));
				follow.setDate(result.getDate("follow_date"));
				FollowDao followDao=new FollowDao();
				boolean isFollow = followDao.eachFollow(follow);
				
				fans.put(follow, isFollow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(result);
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		return fans;
	}
}
