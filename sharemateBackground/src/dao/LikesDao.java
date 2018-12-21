package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.LikesBean;
import bean.NoteBean;
import bean.UserBean;
import dao.DataBase;

public class LikesDao {
	
	/**
	 * 查询某个笔记的赞的数量
	 */
	public int selectLike(int noteId) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="select count(*) c from likes where note_id=?";
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
	 * 点赞（笔记）
	 */
	public void addLike(int userId,int noteId) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="insert into likes values(?,?,NOW())";
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
	 * 取消赞（笔记）
	 */
	public void deleteLike(int userId,int noteId) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="delete from likes where user_id=? and note_id=?";
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
	 * 查询用户点赞过的所有笔记
	 */
	public List<NoteBean> getLikeNoteList(UserBean userbean){
		List<NoteBean> noteList = new ArrayList<NoteBean>();
		NoteDao notedao = new NoteDao();
		Connection con = DataBase.getConnection();
		ResultSet rs = null;
		String sql = "select note_id from likes where user_id=?";
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
	
	/**
	 * 根据当前用户的Id查询当前用户的所有笔记被谁赞过
	 * */
	public List<LikesBean> getLikeList(int userId) {
		List<LikesBean> likeList = new ArrayList<>();
		//1.通过userId查询该用户发过的所有笔记
		NoteDao noteDao = new NoteDao();
		UserBean userBean=new UserDao().getUserById(userId);
		List<NoteBean> noteList = noteDao.getNoteList(userId);
	
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		
		try {
			//2.对每一个笔记查询被哪些用户赞过
			for(NoteBean note:noteList) {
				String sql = "select * from likes where note_id = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, note.getNoteId());
				result = pstmt.executeQuery();
				while(result.next()) {
					LikesBean like = new LikesBean();
					like.setNote(note);
					int id = result.getInt("user_id");
					UserDao userDao = new UserDao();
					UserBean user = userDao.getUserById(id);
					like.setUser(user);
					java.sql.Date sqlDate = result.getDate("zan_time");
					java.util.Date date = new Date(sqlDate.getTime());
					like.setDate(date);
					likeList.add(like);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(result);
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		return likeList;
	}
}
