package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.NoteBean;
import bean.UserBean;

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
	/**
	 * 判断用户是否收藏过某个笔记   (新增)
	 */
	public boolean isCollect(int userId,int noteId) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="select * from collect where user_id=? and note_id=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, noteId);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//后添加
		/**
		 * 查询用户收藏过的所有笔记
		 */
		public List<NoteBean> getCollectNoteList(UserBean userbean){
			List<NoteBean> noteList = new ArrayList<NoteBean>();
			NoteDao notedao = new NoteDao();
			Connection con = DataBase.getConnection();
			ResultSet rs = null;
			String sql = "select note_id from collect where user_id=?";
			try {
				PreparedStatement ptmt = con.prepareStatement(sql);
				ptmt.setInt(1,userbean.getUserId());
				rs = ptmt.executeQuery();
				while(rs.next()) {
					noteList.add(notedao.getNoteById(rs.getInt("note_id")));
				}
				rs.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return noteList;
		}
}
