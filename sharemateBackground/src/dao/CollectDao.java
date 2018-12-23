package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CollectDao {
	
	/**
	 * 查询某个笔记的收藏的数量
	 */
	public int selectCollectCount(int noteId) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="select count(*) c from collect where note_id=?";
		int count=0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, noteId);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				count=rs.getInt("c");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		return count;
	}
	
	/**
	 * 增加笔记收藏
	 */
	public void addCollect(int userId,int noteId) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="insert into collect values(?,?)";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, noteId);
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
	 * 取消笔记收藏
	 */
	public void deleteCollect(int userId,int noteId) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="delete from collect where user_id=? and note_id=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, noteId);
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
