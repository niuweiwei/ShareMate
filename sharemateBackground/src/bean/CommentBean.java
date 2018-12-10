package bean;

import java.util.Date;

public class CommentBean {
	private int commentId;
	private String commentDetail;
	private Date commentDate;
	private int commentLikeCount;
	private UserBean user;
	private NoteBean note;
	public CommentBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommentBean(int commentId, String commentDetail, Date commentDate, int commentLikeCount, UserBean user,
			NoteBean note) {
		super();
		this.commentId = commentId;
		this.commentDetail = commentDetail;
		this.commentDate = commentDate;
		this.commentLikeCount = commentLikeCount;
		this.user = user;
		this.note = note;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getCommentDetail() {
		return commentDetail;
	}
	public void setCommentDetail(String commentDetail) {
		this.commentDetail = commentDetail;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	public int getCommentLikeCount() {
		return commentLikeCount;
	}
	public void setCommentLikeCount(int commentLikeCount) {
		this.commentLikeCount = commentLikeCount;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public NoteBean getNote() {
		return note;
	}
	public void setNote(NoteBean note) {
		this.note = note;
	}
	
}
