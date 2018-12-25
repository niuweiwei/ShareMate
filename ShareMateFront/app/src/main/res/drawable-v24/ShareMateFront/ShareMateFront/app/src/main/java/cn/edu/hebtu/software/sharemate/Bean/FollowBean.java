package cn.edu.hebtu.software.sharemate.Bean;

import java.util.Date;

public class FollowBean {
    private int currentUser;//记录当前用户的id
    private UserBean user;
    private String date;
    private boolean isFollow;

    public FollowBean(){}

    public FollowBean(int currentUser, UserBean user, String date, boolean isFollow) {
        this.currentUser = currentUser;
        this.user = user;
        this.date = date;
        this.isFollow = isFollow;
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

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public int getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(int currentUser) {
        this.currentUser = currentUser;
    }
}
