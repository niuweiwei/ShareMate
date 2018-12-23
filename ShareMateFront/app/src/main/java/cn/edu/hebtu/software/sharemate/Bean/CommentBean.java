package cn.edu.hebtu.software.sharemate.Bean;

import java.util.Date;

public class CommentBean {

    public static final int COMMENT = 0;
    public static final int REPLYCOMMENT = 1;
    public static final int REPLYREPLY =2;

    private int tag;//标志判断展示的是其他用户对“我的”笔记的评论，还是对评论的回复
    private int id;
    private UserBean user;//发布评论或者回复的人
    private String date;//发布的时间
    private String comment;//回复的内容
    private int noteId;//回复的相关笔记的id
    private int notePhoto;//回复的相关笔记的图片
    private String notePhotoPath;
    private int userId;
    private String name;//被回复的人的昵称
    private String argued;//被回复的内容
    private int arguedId;//被回复的评论id或被回复的回复id
    private boolean isLike;//当前用户是否对该评论进行点赞

    public CommentBean(){}

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public String getNotePhotoPath() {
        return notePhotoPath;
    }

    public void setNotePhotoPath(String notePhotoPath) {
        this.notePhotoPath = notePhotoPath;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getArguedId() {
        return arguedId;
    }

    public void setArguedId(int arguedId) {
        this.arguedId = arguedId;
    }
}
