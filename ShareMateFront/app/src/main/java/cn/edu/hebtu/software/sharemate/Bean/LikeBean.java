package cn.edu.hebtu.software.sharemate.Bean;

import java.util.Date;

public class LikeBean {

   private UserBean user;
    private Date date;
    private int noteId;

    public LikeBean(UserBean user, Date date, int noteId) {
        this.user = user;
        this.date = date;
        this.noteId = noteId;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }
}
