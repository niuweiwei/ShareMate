package cn.edu.hebtu.software.sharemate.Bean;

import java.util.Date;

public class FollowBean {
    private UserBean user;
//    private Date date;
    private int currentUser;//记录当前用户的id
    private String date;
    private boolean isFollow;

    public FollowBean() {
    }

    public FollowBean(UserBean user, int currentUser, String date, boolean isFollow) {
        this.user = user;
        this.currentUser = currentUser;
        this.date = date;
        this.isFollow = isFollow;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(int currentUser) {
        this.currentUser = currentUser;
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
}
