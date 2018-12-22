package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.CommentBean;

public class CommentDao {
	
	/**
	 * 根据评论id得到某个评论
	 */
	public CommentBean getCommentBycommentId(int commentId) {
		CommentBean commentBean=new CommentBean();
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="select comment_detail,comment_date,comment_like_count,user_id,note_id from comment where comment_id=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, commentId);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				commentBean.setCommentId(commentId);
				commentBean.setCommentDetail(rs.getString("comment_detail"));
				
				//将数据库中时间戳类型转化成符合某种格式的Date对象
				Timestamp time = rs.getTimestamp("comment_date");
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
				String str = sdf.format(time);
				Date date = sdf.parse(str);
				commentBean.setCommentDate(date);
				
				commentBean.setCommentLikeCount(rs.getInt("comment_like_count"));
				commentBean.setUser(new UserDao().getUserById(rs.getInt("user_id")));
				commentBean.setNote(new NoteDao().getNoteById(rs.getInt("note_id")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		return commentBean;
	}
	
	/**
	 * 根据笔记id得到评论列表（所有评论）
	 */
	public List<CommentBean> getCommentsBynoteId(int noteId){
		List<CommentBean> commentList=new ArrayList<CommentBean>();
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="select comment_id,comment_detail,comment_date,comment_like_count,user_id from comment where note_id=? order by comment_date desc";
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, noteId);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				CommentBean commentBean=new CommentBean();
				commentBean.setCommentId(rs.getInt("comment_id"));
				commentBean.setCommentDetail(rs.getString("comment_detail"));
				
				//将数据库中时间戳类型转化成符合某种格式的Date对象
				Timestamp time = rs.getTimestamp("comment_date");
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
				String str = sdf.format(time);
				Date date = sdf.parse(str);
				commentBean.setCommentDate(date);
				
				commentBean.setCommentLikeCount(rs.getInt("comment_like_count"));
				commentBean.setUser(new UserDao().getUserById(rs.getInt("user_id")));
				commentBean.setNote(new NoteDao().getNoteById(noteId));
				commentList.add(commentBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(rs);
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		
		return commentList;
	}
	
	/**
	 * 计算某个笔记id的评论总数
	 */
	public int getCommentCount(int noteId) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="select count(*) c from comment where note_id=?";
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
	 * 添加评论(修改)
	 */
	public void addComment(CommentBean comment) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="insert into comment(comment_id,comment_detail,comment_date,user_id,note_id) values(0,?,NOW(),?,?)"	;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, comment.getCommentDetail());
			pstmt.setInt(2, comment.getUser().getUserId());
			pstmt.setInt(3, comment.getNote().getNoteId());
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
	 * 删除评论
	 * */
	public void removeComment(int commentId) {
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			String sql = "delete from comment where comment_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentId);
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
	}

	/**
	 * 点赞评论(修改)
	 */
	public void clickLike(int userId,int commentId) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="insert into like_comment(user_id,comment_id) values(?,?)";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,userId);
			pstmt.setInt(2, commentId);
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
	 * 取消评论点赞(修改)
	 */
	public void cancelLike(int userId,int commentId) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="delete from like_comment where user_id =? and comment_id=?"	;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,userId);
			pstmt.setInt(2, commentId);
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
	 * 查看该用户是否点赞了该评论
	 * */
	public boolean isLike(int userId,int commentId) {
		boolean is = false;
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		try {
			String sql = "select * from like_comment where user_id =? and comment_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, commentId);
			res = pstmt.executeQuery();
			if(res.next())
				is = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(res);
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		return is;
	}

	/**
	 * 得到某个评论总的赞数(新增)
	 */
	public int getLikeCount(int commentId) {
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		ResultSet res = null;
		String sql="select count(*) c from like_comment where comment_id=?";
		int count=0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, commentId);
			res=pstmt.executeQuery();
			if(res.next()) {
				count=res.getInt("c");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DataBase.close(res);
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		return count;
	}
	
}
