package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.CAndRBean;
import bean.CommentBean;
import bean.NoteBean;
import bean.ReplyBean;
import bean.UserBean;

public class CAndRDao {
	
	/**
	 * 根据评论id得到CAndRBean对象
	 * */
	public CAndRBean getCAndRBeanByComment(int commentId) {
		
		CAndRBean item = null;
		CommentDao commentDao = new CommentDao(); 
		CommentBean comment = commentDao.getCommentBycommentId(commentId);
		if(comment != null) {
			item = new CAndRBean();
			item.setTag(CAndRBean.COMMENT);
			item.setId(commentId);
			item.setPublisher(comment.getUser());
			item.setContent(comment.getCommentDetail());
			item.setUser(new UserDao().getUserById(comment.getNote().getUser().getUserId()));//设置被回复的人
			item.setNoteImage(comment.getNote().getNoteImage());
			item.setDate(comment.getCommentDate());
			item.setArgued(null);
		}
		
		return item;
		
	}
	
	/**
	 * 根据回复id得到CAandBean对象
	 * */
	public CAndRBean getCAndRBeanByReply(int replyId) {
		CAndRBean item = null;
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet result = null ;
		ResultSet result1 = null ;
		
		try {
			String sql = "select * from reply where reply_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			result = pstmt.executeQuery();
			if(result.next()) {
				item = new CAndRBean();
				item.setTag(CAndRBean.REPLY);
				item.setId(replyId);
				item.setPublisher(new UserDao().getUserById(result.getInt("user_id")));
				item.setContent(result.getString("reply_detail"));
				item.setNoteImage(new ReplyDao().getReplyNote(replyId).getNoteImage());
				
				//从数据库中得到发布笔记的时间 Timestamp类型转化成Date对象
				Timestamp time = result.getTimestamp("reply_time");
				item.setDate(time);
				
				int reReplyId = result.getInt("re_reply_id");
				int commentId = result.getInt("comment_id");
				//查询被回复的内容以及用户
				if(reReplyId != 0 && commentId ==0) {
					//当回复的是回复时
					String sql1 = "select * from reply where reply_id = ?";
					pstmt1 = conn.prepareStatement(sql1);
					pstmt1.setInt(1, reReplyId);
					result1 = pstmt1.executeQuery();
					if(result1.next()) {
						item.setUser(new UserDao().getUserById(result1.getInt("user_id")));
						item.setArgued(result1.getString("reply_detail"));
					}
				}else if(commentId !=0 && reReplyId == 0){
					//当回复的是评论时
					CommentDao commentDao = new CommentDao();
					item.setUser(new UserDao().getUserById(commentDao.getCommentBycommentId(commentId).getUser().getUserId()));
					item.setArgued(commentDao.getCommentBycommentId(commentId).getCommentDetail());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(result1 != null)
				DataBase.close(result1);
			if(result != null)
				DataBase.close(result);
			if(pstmt1 !=null)
				DataBase.close(pstmt1);
			if(pstmt != null)
				DataBase.close(pstmt);
			DataBase.close(conn);
		}
		return item ;
	}

	/**
	 * 根据被回复的对象 得到所有回复了该回复的回复
	 * */
	public void getCAndRBeanList (ReplyBean reply,List<CAndRBean> list){
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		
		try {
			String sql = "select * from reply where re_reply_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply.getReplyId());
			result = pstmt.executeQuery();
			while(result.next()) {
				CAndRBean item = new CAndRBean();
				item.setTag(CAndRBean.REPLY);
				item.setId(result.getInt("reply_id"));
				item.setPublisher(new UserDao().getUserById(result.getInt("user_id")));
				item.setContent(result.getString("reply_detail"));
				item.setUser(reply.getUser());
				item.setNoteImage(new ReplyDao().getReplyNote(reply.getReplyId()).getNoteImage());
				item.setDate(result.getTimestamp("reply_time"));
				item.setArgued(reply.getReplyDetail());
				list.add(item);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataBase.close(result);
			DataBase.close(pstmt);
			DataBase.close(conn);
		}
	}
	
	/**
	 * 将回复下的所有回复 放入List中
	 * */
	public void putCAndRBean(int replyId,List<CAndRBean> list) {
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		ResultSet result = null;
		
		//查询该回复是否被回复
		try {
			String sql ="select * from reply where re_reply_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyId);
			result = pstmt.executeQuery();
			if(result.next()) {
				result.previous();
				while(result.next()) {
					int id = result.getInt("reply_id");
					CAndRBean item = new CAndRDao().getCAndRBeanByReply(id);
					list.add(item);
					putCAndRBean(id,list);
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
		
	}
	
	
	/**
	 * 根据用户id得到该用户发布的所有笔记的评论及所有回复
	 * */
	public List<CAndRBean> getCAndBeanList(int userId){
		List <CAndRBean> list = new ArrayList<>();
		//获得该用户发布过的所有笔记
		UserBean user=new UserDao().getUserById(userId);
		List<NoteBean> noteList = new NoteDao().getNoteList(userId);
		for(NoteBean note : noteList) {
			//获得每一个笔记下的所有的评论
			List<CommentBean> commentList = new CommentDao().getCommentsBynoteId(note.getNoteId());
			for(CommentBean comment : commentList) {
				int commentId = comment.getCommentId();
				CAndRBean commentItem = getCAndRBeanByComment(commentId); 
				list.add(commentItem);
				//获得每个评论下的所有回复
				List<ReplyBean> replyList = new ReplyDao().getRepliesBycommentId(commentId);
				for(ReplyBean reply : replyList) {
					//每一条回复的所有回复
					int replyId = reply.getReplyId();
					CAndRBean replyItem = getCAndRBeanByReply(replyId);
					list.add(replyItem);
					putCAndRBean(replyId,list);
				}
			}
		}
		
		//获取到该用户发布过的全部回复
		List<ReplyBean> replys = new ReplyDao().getReplyList(userId);
		for(ReplyBean reply:replys) {
			//针对每一条回复 找到回复该回复的所有回复
			getCAndRBeanList(reply,list);
		}
		return list;
	}
}
