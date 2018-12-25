package cn.edu.hebtu.software.sharemate.Bean;

import java.util.Date;

public class LikeBean {

    private  UserBean user;
    private String date;
    private int noteId;
    private String notePhotoPath;

    public LikeBean() {
    }

    public LikeBean(UserBean user, String date, int noteId, String notePhotoPath) {
        this.user = user;
        this.date = date;
        this.noteId = noteId;
        this.notePhotoPath = notePhotoPath;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotePhotoPath() {
        return notePhotoPath;
    }

    public void setNotePhotoPath(String notePhotoPath) {
        this.notePhotoPath = notePhotoPath;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }
}
