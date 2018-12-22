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

import dao.DataBase;
import bean.CommentBean;
import bean.NoteBean;
import bean.UserBean;

public class NoteDao {
	
	/**
	 * 根据笔记id得到笔记
	 */
	public NoteBean getNoteById(int noteId) {
		NoteBean  noteBean=new NoteBean();
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		String sql="select note_title,note_detail,note_image,note_date,type_id,user_id "
				+ "from note where note_id=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, noteId);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				noteBean.setNoteId(noteId);
				noteBean.setNoteImage(rs.getString("note_image"));
//				noteBean.setNoteCollectionCount(rs.getInt("note_collection_count"));
//				noteBean.setNoteCommentCount(rs.getInt("note_comment_count"));
				noteBean.setNoteTitle(rs.getString("note_title"));
				
				//将数据库中时间戳类型转化成符合某种格式的Date对象
				Timestamp time = rs.getTimestamp("note_date");
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
				String str = sdf.format(time);
				Date date = sdf.parse(str);
				noteBean.setNoteDate(date);
				
				noteBean.setUser(new UserDao().getUserById(rs.getInt("user_id")));
				noteBean.setNoteDetail(rs.getString("note_detail"));
//				noteBean.setNoteLikeCount(rs.getInt("note_like_count"));
				noteBean.setType(new TypeDao().getTypeById(rs.getInt("type_id")));
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
		return noteBean;
	}
	
	/**
	 * 添加笔记
	 */
	public void addNote1(NoteBean notebean) {
		Connection conn=DataBase.getConnection();
		PreparedStatement pstmt=null;
		//笔记插入时去除那三列
		String sql="insert into note(note_id,note_title,note_detail,note_image,note_date,type_id,user_id) values (0,?,?,?,NOW(),?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, notebean.getNoteTitle());
			pstmt.setString(2, notebean.getNoteDetail());
			pstmt.setString(3, notebean.getNoteImage());
			pstmt.setInt(4, notebean.getType().getTypeId());
			pstmt.setInt(5, notebean.getUser().getUserId());
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
	 * 得到所有笔记
	 */
	public List<NoteBean> getAllNote(){
		List<NoteBean> notelist = new ArrayList<NoteBean>();
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		String sql="select * from note";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				NoteBean  noteBean=new NoteBean();
				noteBean.setNoteId(rs.getInt("note_id"));
				noteBean.setNoteImage(rs.getString("note_image"));
				noteBean.setNoteCollectionCount(rs.getInt("note_collection_count"));
				noteBean.setNoteCommentCount(rs.getInt("note_comment_count"));
				noteBean.setNoteTitle(rs.getString("note_title"));
				
				//将数据库中时间戳类型转化成符合某种格式的Date对象
				Timestamp time = rs.getTimestamp("note_date");
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
				String str = sdf.format(time);
				Date date = sdf.parse(str);
				noteBean.setNoteDate(date);
				
				noteBean.setUser(new UserDao().getUserById(rs.getInt("user_id")));
				noteBean.setNoteDetail(rs.getString("note_detail"));
				noteBean.setNoteLikeCount(rs.getInt("note_like_count"));
				noteBean.setType(new TypeDao().getTypeById(rs.getInt("type_id")));
				notelist.add(noteBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DataBase.close(pstmt);
			DataBase.close(conn);
		    }
		return notelist;
	}
	
	/**
	 * 根据typeId取笔记
	 */
	public List<NoteBean> getNoteBytypeId(int typeId){
		List<NoteBean> notelist = new ArrayList<NoteBean>();
		UserBean user=new UserBean();
		UserDao userdao = new UserDao();
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		String sql="select * from note where type_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, typeId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				NoteBean  noteBean=new NoteBean();
				noteBean.setNoteId(rs.getInt("note_id"));
				noteBean.setNoteImage(rs.getString("note_image"));
				noteBean.setNoteCollectionCount(rs.getInt("note_collection_count"));
				noteBean.setNoteCommentCount(rs.getInt("note_comment_count"));
				noteBean.setNoteTitle(rs.getString("note_title"));
				noteBean.setNoteDate(rs.getDate("note_date"));
				noteBean.setUser(new UserDao().getUserById(rs.getInt("user_id")));
				noteBean.setNoteDetail(rs.getString("note_detail"));
				noteBean.setNoteLikeCount(rs.getInt("note_like_count"));
				noteBean.setType(new TypeDao().getTypeById(rs.getInt("type_id")));
				notelist.add(noteBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DataBase.close(pstmt);
			DataBase.close(conn);
		    }
		return notelist;
	}
	
	/**
	 * 根据三个type_id取笔记
	 */
	public List<NoteBean>getNoteByThreetypeId(int id1,int id2,int id3){
		List<NoteBean> notelist = new ArrayList<NoteBean>();
		UserBean user=new UserBean();
		UserDao userdao = new UserDao();
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		String sql="select * from note where type_id = ? or type_id = ? or type_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id1);
			pstmt.setInt(2, id2);
			pstmt.setInt(3, id3);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				NoteBean  noteBean=new NoteBean();
				noteBean.setNoteId(rs.getInt("note_id"));
				noteBean.setNoteImage(rs.getString("note_image"));
				noteBean.setNoteCollectionCount(rs.getInt("note_collection_count"));
				noteBean.setNoteCommentCount(rs.getInt("note_comment_count"));
				noteBean.setNoteTitle(rs.getString("note_title"));
				noteBean.setNoteDate(rs.getDate("note_date"));
				noteBean.setUser(new UserDao().getUserById(rs.getInt("user_id")));
				noteBean.setNoteDetail(rs.getString("note_detail"));
				noteBean.setNoteLikeCount(rs.getInt("note_like_count"));
				noteBean.setType(new TypeDao().getTypeById(rs.getInt("type_id")));
				notelist.add(noteBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DataBase.close(pstmt);
			DataBase.close(conn);
		    }
		return notelist;
	}
	
	/**
	 * 根据四个type_id取笔记
	 */
	public List<NoteBean>getNoteByFourtypeId(int id1,int id2,int id3,int id4){
		List<NoteBean> notelist = new ArrayList<NoteBean>();
		UserBean user=new UserBean();
		UserDao userdao = new UserDao();
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		String sql="select * from note where type_id = ? or type_id = ? or type_id = ? or type_id = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id1);
			pstmt.setInt(2, id2);
			pstmt.setInt(3, id3);
			pstmt.setInt(4, id4);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				NoteBean  noteBean=new NoteBean();
				noteBean.setNoteId(rs.getInt("note_id"));
				noteBean.setNoteImage(rs.getString("note_image"));
				noteBean.setNoteCollectionCount(rs.getInt("note_collection_count"));
				noteBean.setNoteCommentCount(rs.getInt("note_comment_count"));
				noteBean.setNoteTitle(rs.getString("note_title"));
				noteBean.setNoteDate(rs.getDate("note_date"));
				noteBean.setUser(new UserDao().getUserById(rs.getInt("user_id")));
				noteBean.setNoteDetail(rs.getString("note_detail"));
				noteBean.setNoteLikeCount(rs.getInt("note_like_count"));
				noteBean.setType(new TypeDao().getTypeById(rs.getInt("type_id")));
				notelist.add(noteBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DataBase.close(pstmt);
			DataBase.close(conn);
		    }
		return notelist;
	}
	
	/**
	 * 根据五个type_id取笔记
	 */
	public List<NoteBean>getNoteByFivetypeId(int id1,int id2,int id3,int id4,int id5){
		List<NoteBean> notelist = new ArrayList<NoteBean>();
		UserBean user=new UserBean();
		UserDao userdao = new UserDao();
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		String sql="select * from note where type_id = ? or type_id = ? or type_id = ? or type_id = ? or type_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id1);
			pstmt.setInt(2, id2);
			pstmt.setInt(3, id3);
			pstmt.setInt(4, id4);
			pstmt.setInt(5, id5);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				NoteBean  noteBean=new NoteBean();
				noteBean.setNoteId(rs.getInt("note_id"));
				noteBean.setNoteImage(rs.getString("note_image"));
				noteBean.setNoteCollectionCount(rs.getInt("note_collection_count"));
				noteBean.setNoteCommentCount(rs.getInt("note_comment_count"));
				noteBean.setNoteTitle(rs.getString("note_title"));
				noteBean.setNoteDate(rs.getDate("note_date"));
				noteBean.setUser(new UserDao().getUserById(rs.getInt("user_id")));
				noteBean.setNoteDetail(rs.getString("note_detail"));
				noteBean.setNoteLikeCount(rs.getInt("note_like_count"));
				noteBean.setType(new TypeDao().getTypeById(rs.getInt("type_id")));
				notelist.add(noteBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DataBase.close(pstmt);
			DataBase.close(conn);
		    }
		return notelist;
	}
	
	/**
	 * 根据登录人的user_id取他关注的人的所有笔记
	 */
	public List<NoteBean>getNoteByuserId(int userId){
		List<NoteBean> notelist = new ArrayList<NoteBean>();
		CommentDao com=new CommentDao();
		UserDao userdao = new UserDao();
		Connection conn = DataBase.getConnection();
		PreparedStatement pstmt = null;
		String sql="select * from note where user_id in"
				+ "(SELECT user_id from follow where follow_id = ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				UserBean user=new UserBean();
				List<CommentBean> commentlist = new ArrayList<CommentBean>();
				NoteBean  noteBean=new NoteBean();
				noteBean.setNoteId(rs.getInt("note_id"));
				noteBean.setNoteImage(rs.getString("note_image"));
				noteBean.setNoteCollectionCount(rs.getInt("note_collection_count"));
				noteBean.setNoteCommentCount(rs.getInt("note_comment_count"));
				noteBean.setNoteTitle(rs.getString("note_title"));
				noteBean.setNoteDate(rs.getDate("note_date"));
				noteBean.setUser(new UserDao().getUserById(rs.getInt("user_id")));
				noteBean.setNoteDetail(rs.getString("note_detail"));
				noteBean.setNoteLikeCount(rs.getInt("note_like_count"));
				noteBean.setType(new TypeDao().getTypeById(rs.getInt("type_id")));
				commentlist=com.getCommentsBynoteId(rs.getInt("note_id"));
				noteBean.setComment(commentlist);
			    notelist.add(noteBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DataBase.close(pstmt);
			DataBase.close(conn);
		    }
		return notelist;
	}
	
	/**
	 * 查询用户获得的总赞数
	 */
	public int getLikeCount(UserBean userbean) {
		int likeCount = 0;
		LikesDao likesdao = new LikesDao();
		Connection con = DataBase.getConnection();
		ResultSet rs = null;
		String sql = "select * from note where user_id = ?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setInt(1,userbean.getUserId());
			rs = ptmt.executeQuery();
			while(rs.next()) {
				likeCount+= likesdao.selectLike(rs.getInt(1));
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return likeCount;
	}
		
	/**
	 * 获取用户发过的笔记
	 */
	public List<NoteBean> getNoteList(int userId){
		List<NoteBean> noteList = new ArrayList<>();
		Connection con = DataBase.getConnection();
		String sql = "select note_id from note where user_id=?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setInt(1,userId);
			ResultSet rs = ptmt.executeQuery();
			while(rs.next()) {
				noteList.add(this.getNoteById(rs.getInt("note_id")));
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
	 * 获得用户收藏的所有的笔记
	 */
	public List<NoteBean> getCollectList(UserBean userbean){
		List<NoteBean> collectList = new ArrayList<>();
		Connection con = DataBase.getConnection();
		String sql = "select note_id from collect where user_id=?";
		try {
			PreparedStatement ptmt = con.prepareStatement(sql);
			ptmt.setInt(1,userbean.getUserId());
			ResultSet rs = ptmt.executeQuery();
			while(rs.next()) {
				collectList.add(this.getNoteById(rs.getInt("note_id")));
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return collectList;
	}
}
