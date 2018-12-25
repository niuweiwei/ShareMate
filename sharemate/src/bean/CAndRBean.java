package bean;

import java.util.Date;

/**
 * 在消息模块中将 评论以及回复合并到一起 便于显示
 * */
public class CAndRBean {

	public final static int COMMENT=0;
	public final static int REPLY=1;
	
	private int tag;//tag 0:表示当前为评论 1:表示当前为回复
	private int id;//commentId或者是replyId
	private UserBean publisher;//发布评论或回复的用户对象
	private String content;//评论或回复的内容
	private UserBean user;//被评论或被回复者的用户id
	private String noteImage;//相关笔记的图片的路径
	private Date date;//评论或回复的时间
	private String argued;//被回复的内容 若是评论 该值为null
	
	
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	public UserBean getPublisher() {
		return publisher;
	}
	public void setPublisher(UserBean publisher) {
		this.publisher = publisher;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public String getNoteImage() {
		return noteImage;
	}
	public void setNoteImage(String noteImage) {
		this.noteImage = noteImage;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getArgued() {
		return argued;
	}
	public void setArgued(String argued) {
		this.argued = argued;
	}
	public static int getComment() {
		return COMMENT;
	}
	public static int getReply() {
		return REPLY;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
