package cn.edu.hebtu.software.sharemate.Bean;

import android.graphics.Bitmap;

import java.util.Date;

public class LikeBean {

    private  UserBean user;
    private String date;
    private int noteId;
    private String notePhotoPath;

    public LikeBean(){}

    public LikeBean(UserBean user, String date, int noteId,String notePhoto) {
        this.user = user;
        this.date = date;
        this.noteId = noteId;
        this.notePhotoPath = notePhoto;
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

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNotePhotoPath() {
        return notePhotoPath;
    }

    public void setNotePhotoPath(String notePhotoPath) {
        this.notePhotoPath = notePhotoPath;
    }
}
