package cn.edu.hebtu.software.sharemate.Bean;

import java.util.Date;

public class CommentBean {

    public static final int COMMENT = 0;
    public static final int REPLY = 1;

    private int tag;//标志判断展示的是其他用户对“我的”笔记的评论，还是对我的评论的回复
    private UserBean user;//发布评论或者回复的人
    private Date date;//发布的时间
    private String comment;//回复的内容
    private int notePhoto;//回复的相关笔记的图片
    private String name;//被回复的人的昵称
    private String argued;//被回复的内容

    public CommentBean(int tag, UserBean user, Date date, String comment, int notePhoto, String name, String argued) {
        this.tag = tag;
        this.user = user;
        this.date = date;
        this.comment = comment;
        this.notePhoto = notePhoto;
        this.name = name;
        this.argued = argued;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getNotePhoto() {
        return notePhoto;
    }

    public void setNotePhoto(int notePhoto) {
        this.notePhoto = notePhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArgued() {
        return argued;
    }

    public void setArgued(String argued) {
        this.argued = argued;
    }
}
