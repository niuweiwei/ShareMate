package cn.edu.hebtu.software.sharemate.Bean;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.List;

import cn.edu.hebtu.software.sharemate.R;

public class NoteBean {
    private int noId;
    private int noPhoto;
    private Bitmap noteImage1;
    private String title;
    private int noteImage;
    private String noteDetail,noteTitle;
    private UserBean user;
    private String noteTime;
    private CommentBean commentBean;
    private List<CommentBean> comment;
    private int sharecount,collectcount,pingluncount;
    private String zancount;
    private String commentDetail = "";
    private int zan=R.drawable.xin;
    private int col=R.drawable.xingxing;
    private int fol=R.drawable.yiguanzhu;
    private int islike=-1;
    private int iscollect=-1;
    private int isfollow;
    private UserBean userContent;
    public NoteBean(int noteImage, String noteDetail, String noteTitle, UserBean user, String noteTime, String zancount, int sharecount, int collectcount, int pingluncount) {
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

    public NoteBean() {
    }

    public Bitmap getNoteImage1() {
        return noteImage1;
    }

    public void setNoteImage1(Bitmap noteImage1) {
        this.noteImage1 = noteImage1;
    }

    public NoteBean(int noteImage, String noteDetail, String noteTitle,
                    UserBean user, String noteTime, List<CommentBean> comment,
                    String zancount, int sharecount, int collectcount, int pingluncount) {
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

    public void setCommentBean(CommentBean commentBean) {
        this.commentBean = commentBean;
    }

    public CommentBean getCommentBean() {
        return commentBean;
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

    public String getCommentDetail() {
        return commentDetail;
    }

    public void setCommentDetail(String commentDetail) {
        this.commentDetail = commentDetail;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public String getZancount() {
        return zancount;
    }

    public void setZancount(String zancount) {
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

    public int getZan() {

        return zan;
    }

    public void setZan(int zan) {

        this.zan = zan;
    }

    public int getCol() {
        return col;
    }

    public int getFol() {
        return fol;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setFol(int fol) {
        this.fol = fol;
    }

    public int isIslike() {
        return islike;
    }

    public void setIslike(int islike) {
        this.islike = islike;
    }
    public int getIscollect() {
        return iscollect;
    }

    public int getIsfollow() {
        return isfollow;
    }

    public void setIscollect(int iscollect) {
        this.iscollect = iscollect;
    }

    public void setIsfollow(int isfollow) {
        this.isfollow = isfollow;
    }

    public UserBean getUserContent() {
        return userContent;
    }

    public void setUserContent(UserBean userContent) {
        this.userContent = userContent;
    }
}
