package bean;

import java.util.Date;
import java.util.List;

public class LikesBean {
	private UserBean user;
	private NoteBean note;
	private Date date;
	private List<NoteBean> noteList;
	public LikesBean() {
		super();
		// TODO Auto-generated constructor stub
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
	public List<NoteBean> getNoteList() {
		return noteList;
	}
	public void setNoteList(List<NoteBean> noteList) {
		this.noteList = noteList;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
