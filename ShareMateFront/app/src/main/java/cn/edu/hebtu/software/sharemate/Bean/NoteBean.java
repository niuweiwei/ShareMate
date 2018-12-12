package cn.edu.hebtu.software.sharemate.Bean;

import java.util.List;

public class NoteBean {
    private int noId;
    private int noPhoto;
    private String title;
    private int noteImage;
    private String noteDetail,noteTitle;
    private UserBean user;
    private String noteTime;
    private List<CommentBean> comment;
    private int zancount,sharecount,collectcount,pingluncount;

    public NoteBean(int noteImage, String noteDetail, String noteTitle, UserBean user, String noteTime, int zancount, int sharecount, int collectcount, int pingluncount) {
        this.noteImage = noteImage;
        this.noteDetail = noteDetail;
        this.noteTitle = noteTitle;
        this.user = user;
        this.noteTime = noteTime;
        this.zancount = zancount;
        this.sharecount = sharecount;
        this.collectcount = collectcount;
        this.pingluncount = pingluncount;
    }
    public NoteBean(int noteImage, String noteDetail, String noteTitle,
                    UserBean user, String noteTime, List<CommentBean> comment,
                    int zancount, int sharecount, int collectcount, int pingluncount) {
        this.noteImage = noteImage;
        this.noteDetail = noteDetail;
        this.noteTitle = noteTitle;
        this.user = user;
        this.noteTime = noteTime;
        this.comment = comment;
        this.zancount = zancount;
        this.sharecount = sharecount;
        this.collectcount = collectcount;
        this.pingluncount = pingluncount;
    }
    public NoteBean(int noPhoto, String title) {
        this.noPhoto = noPhoto;
        this.title = title;
    }

    public int getNoPhoto() {
        return noPhoto;
    }

    public void setNoPhoto(int noPhoto) {
        this.noPhoto = noPhoto;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String content) {
        this.title = content;
    }

    public int getNoId() {
        return noId;
    }

    public void setNoId(int noId) {
        this.noId = noId;
    }

    public int getNoteImage() {
        return noteImage;
    }

    public void setNoteImage(int noteImage) {
        this.noteImage = noteImage;
    }

    public String getNoteDetail() {
        return noteDetail;
    }

    public void setNoteDetail(String noteDetail) {
        this.noteDetail = noteDetail;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public int getZancount() {
        return zancount;
    }

    public void setZancount(int zancount) {
        this.zancount = zancount;
    }

    public int getSharecount() {
        return sharecount;
    }

    public void setSharecount(int sharecount) {
        this.sharecount = sharecount;
    }

    public int getCollectcount() {
        return collectcount;
    }

    public void setCollectcount(int collectcount) {
        this.collectcount = collectcount;
    }

    public int getPingluncount() {
        return pingluncount;
    }

    public void setPingluncount(int pingluncount) {
        this.pingluncount = pingluncount;
    }
}
