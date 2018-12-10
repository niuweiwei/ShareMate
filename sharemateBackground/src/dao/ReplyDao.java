package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import dao.DataBase;
import bean.CommentBean;
import bean.NoteBean;
import bean.ReplyBean;

public class ReplyDao {
	/**
	 * 根据评论id得到评论的回复列表(包括回复的回复和评论的回复)
	 */
	public List<ReplyBean> getRepliesBycommentId(int commentId){
		List<ReplyBean> replyList=new ArrayList<ReplyBean>();
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="select reply_id,user_id,reply_detail,reply_time,reply_like_count from reply where comment_id=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, commentId);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				ReplyBean reply=new ReplyBean();
				reply.setReplyId(rs.getInt("reply_id"));
				CommentDao commentDao=new CommentDao();
				reply.setComment(commentDao.getCommentBycommentId(commentId));
				reply.setUser(new UserDao().getUserById(rs.getInt("user_id")));
				reply.setReplyDetail(rs.getString("reply_detail"));
				
				//将数据库中时间戳类型转化成符合某种格式的Date对象
				Timestamp time = rs.getTimestamp("reply_time");
				Date replyTime = time;
				reply.setReplyTime(replyTime);
				
				reply.setReplyLikeCount(rs.getInt("reply_like_count"));
				replyList.add(reply);
				
				getRepliesByreplyId(rs.getInt("reply_id"),replyList);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		return replyList;
	}
	
	/**
	 * 根据回复id得到回复列表（回复的回复）·
	 */
	public void getRepliesByreplyId(int replyId,List<ReplyBean> replyList){
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="select reply_id,user_id,reply_detail,reply_time,reply_like_count from reply where re_reply_id=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				ReplyBean reply=new ReplyBean();
				reply.setReplyId(rs.getInt("reply_id"));
				reply.setUser(new UserDao().getUserById(rs.getInt("user_id")));
				reply.setReplyDetail(rs.getString("reply_detail"));
				reply.setReReplyId(replyId);
				
				Timestamp timestamp=rs.getTimestamp("reply_time");
				Date date=new Date();
				date=timestamp;
				reply.setReplyTime(date);
				
				reply.setReplyLikeCount(rs.getInt("reply_like_count"));
				replyList.add(reply);
				 getRepliesByreplyId(rs.getInt("reply_id"),replyList);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		return;
	}
	
	/**
	 * 得到某个评论的回复总数（包括评论的回复和回复的回复）                                             
	 */
	public int getReplyCount(int commentId) {
		List<ReplyBean> replyList=getRepliesBycommentId(commentId);
		return replyList.size();
	}
	
	/**
	 * 根据回复id得到该回复所针对的笔记对象
	 * */
	public NoteBean getReplyNote(int replyId) {
		NoteBean note = null;
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		
		try {
			String sql = "select * from reply where reply_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			result = pstmt.executeQuery();
			if(result.next()) {
				int reReplyId = result.getInt("re_reply_id");
				int commentId = result.getInt("comment_id");
				if(reReplyId == 0 && commentId != 0) {
					//代表该回复回复的是评论
					note = new CommentDao().getCommentBycommentId(commentId).getNote();
				}else if(reReplyId !=0 && commentId == 0) {
					//代表该回复回复的是回复
					note = getReplyNote(reReplyId);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(result);
			DataBase.close(result);
			DataBase.close(conn);
		}
		return note;
	}
	
	/**
	 * 获得指定用户发布的全部回复
	 * */
	public List<ReplyBean> getReplyList(int userId){
		List<ReplyBean> replyList = new ArrayList<>();
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		
		try {
			String sql = "select * from reply where user_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			result = pstmt.executeQuery();
			while(result.next()) {
				ReplyBean reply = new ReplyBean();
				reply.setReplyId(result.getInt("reply_id"));
				CommentBean comment=new CommentDao().getCommentBycommentId(result.getInt("comment_id"));
				reply.setComment(comment);
				reply.setReReplyId(result.getInt("re_reply_id"));
				reply.setUser(new UserDao().getUserById(userId));
				reply.setReplyDetail(result.getString("reply_detail"));
				reply.setReplyTime(result.getTimestamp("reply_time"));
				replyList.add(reply);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(result);
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
		return replyList;
	}
	
	/**
	 * 添加评论的回复
	 */
	public void addCommentReply(ReplyBean reply) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="insert into reply(reply_id,comment_id,re_reply_id,user_id,reply_detail,reply_time,reply_like_count) values(0,?,null,?,?,NOW(),0)";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, reply.getComment().getCommentId());
			pstmt.setInt(2, reply.getUser().getUserId());
			pstmt.setString(3, reply.getReplyDetail());
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
	 * 添加回复的回复
	 */
	public void addReReply(ReplyBean reply) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="insert into reply(reply_id,comment_id,re_reply_id,user_id,reply_detail,reply_time,reply_like_count) values(0,null,?,?,?,NOW(),0)";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, reply.getReReplyId());
			pstmt.setInt(2, reply.getUser().getUserId());
			pstmt.setString(3, reply.getReplyDetail());
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
	 * 删除他人的回复
	 * */
	public void removeReply(int replyId) {
		Connection connection = DataBase.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			String sql = "delete from reply where reply_id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(pstmt);
			DataBase.close(connection);
		}
	}
	
	/**
	 * 点赞回复
	 */
	public void clickLike(int replyId) {
			Connection conn=DataBase.getConnection();
			PreparedStatement pstmt=null;
			String sql="update reply set reply_like_count=reply_like_count+1 where reply_id=?"	;
			try {
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1,replyId);
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
	 * 取消回复点赞
	 */
	public void cancelLike(int replyId) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="update reply set reply_like_count=reply_like_count-1 where reply_id=?"	;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,replyId);
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
