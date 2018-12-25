package bean;

import java.util.Date;

public class ReplyBean {
	private int replyId;
	private CommentBean comment;
	private int reReplyId;
	private UserBean user;
	private String replyDetail;
	private Date replyTime;
	private int replyLikeCount;
	
	public ReplyBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReplyBean(int replyId, CommentBean comment, int reReplyId, UserBean user, String replyDetail,
			Date replyTime, int replyLikeCount) {
		super();
		this.replyId = replyId;
		this.comment = comment;
		this.reReplyId = reReplyId;
		this.user = user;
		this.replyDetail = replyDetail;
		this.replyTime = replyTime;
		this.replyLikeCount = replyLikeCount;
	}


	public CommentBean getComment() {
		return comment;
	}

	public void setComment(CommentBean comment) {
		this.comment = comment;
	}

	public int getReReplyId() {
		return reReplyId;
	}

	public void setReReplyId(int reReplyId) {
		this.reReplyId = reReplyId;
	}

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}


	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public String getReplyDetail() {
		return replyDetail;
	}

	public void setReplyDetail(String replyDetail) {
		this.replyDetail = replyDetail;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public int getReplyLikeCount() {
		return replyLikeCount;
	}

	public void setReplyLikeCount(int replyLikeCount) {
		this.replyLikeCount = replyLikeCount;
	}
	
	

}
