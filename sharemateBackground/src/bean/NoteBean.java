package bean;

import java.util.Date;
import java.util.List;

public class NoteBean {
	private int noteId;
	private String noteTitle;
	private String noteDetail;
	private String noteImage;
	private Date noteDate;
	private int noteCommentCount;
	private int noteCollectionCount;
	private int noteLikeCount;
	private TypeBean type;
	private UserBean user;
	private List<CommentBean> comment;
	private int isLike,isCollect,isFollow;
	private UserBean userContent;
	public NoteBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NoteBean(int noteId, String noteTitle, String noteDetail, String noteImage, Date noteDate, TypeBean type, UserBean user) {
		super();
		this.noteId = noteId;
		this.noteTitle = noteTitle;
		this.noteDetail = noteDetail;
		this.noteImage = noteImage;
		this.noteDate = noteDate;
		this.type = type;
		this.user = user;
	}
	public NoteBean(int noteId, String noteTitle, String noteDetail, String noteImage, Date noteDate,
			int noteCommentCount, int noteCollectionCount, int noteLikeCount, TypeBean type, UserBean user) {
		super();
		this.noteId = noteId;
		this.noteTitle = noteTitle;
		this.noteDetail = noteDetail;
		this.noteImage = noteImage;
		this.noteDate = noteDate;
		this.noteCommentCount = noteCommentCount;
		this.noteCollectionCount = noteCollectionCount;
		this.noteLikeCount = noteLikeCount;
		this.type = type;
		this.user = user;
	}

	public int getNoteId() {
		return noteId;
	}
	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}
	public String getNoteTitle() {
		return noteTitle;
	}
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}
	public String getNoteDetail() {
		return noteDetail;
	}
	public void setNoteDetail(String noteDetail) {
		this.noteDetail = noteDetail;
	}
	public String getNoteImage() {
		return noteImage;
	}
	public void setNoteImage(String noteImage) {
		this.noteImage = noteImage;
	}
	public Date getNoteDate() {
		return noteDate;
	}
	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}
	public int getNoteCommentCount() {
		return noteCommentCount;
	}
	public void setNoteCommentCount(int noteCommentCount) {
		this.noteCommentCount = noteCommentCount;
	}
	public int getNoteCollectionCount() {
		return noteCollectionCount;
	}
	public void setNoteCollectionCount(int noteCollectionCount) {
		this.noteCollectionCount = noteCollectionCount;
	}
	public int getNoteLikeCount() {
		return noteLikeCount;
	}
	public void setNoteLikeCount(int noteLikeCount) {
		this.noteLikeCount = noteLikeCount;
	}

	public TypeBean getType() {
		return type;
	}

	public void setType(TypeBean type) {
		this.type = type;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public List<CommentBean> getComment() {
		return comment;
	}

	public void setComment(List<CommentBean> comment) {
		this.comment = comment;
	}
	public int isLike() {
		return isLike;
	}

	public void setLike(int isLike) {
		this.isLike = isLike;
	}

	public int getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(int isCollect) {
		this.isCollect = isCollect;
	}

	public int getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(int isFollow) {
		this.isFollow = isFollow;
	}

	public UserBean getUserContent() {
		return userContent;
	}

	public void setUserContent(UserBean userContent) {
		this.userContent = userContent;
	}
}
