package cn.edu.hebtu.software.sharemate.Bean;

import java.io.Serializable;

public class UserBean implements Serializable{
    private int userPhoto;
    private String userName;
    private String userPhotoPath;
    private String userId;
    private String userSex;
    private String userAddress;
    private String userBirth;
    private String userIntroduce;
    private int followCount;
    private int fanCount;
    private int likeCount;
    private int noteCount;
    private boolean states;

    public UserBean() { }

    public UserBean( String userName,int userPhoto) {
        this.userPhoto = userPhoto;
        this.userName = userName;
    }

    public int getNoteCount() {return noteCount;}

    public void setNoteCount(int noteCount) {this.noteCount = noteCount;}

    public int getFollowCount() {return followCount;}

    public void setFollowCount(int followCount) {this.followCount = followCount;}

    public int getFanCount() {return fanCount;}

    public void setFanCount(int fanCount) {this.fanCount = fanCount;}

    public int getLikeCount() {return likeCount;}

    public void setLikeCount(int likeCount) {this.likeCount = likeCount;}
    public boolean isStates() {return states;}

    public void setStates(boolean states) {this.states = states;}

    public int getUserPhoto() { return userPhoto; }

    public void setUserPhoto(int userPhoto) { this.userPhoto = userPhoto; }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhotoPath() {
        return userPhotoPath;
    }

    public void setUserPhotoPath(String userPhoto) {
        this.userPhotoPath= userPhoto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public String getUserIntroduce() {
        return userIntroduce;
    }

    public void setUserIntroduce(String userIntroduce) {
        this.userIntroduce = userIntroduce;
    }

}