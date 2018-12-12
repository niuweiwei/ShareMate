package cn.edu.hebtu.software.sharemate.Bean;

import java.util.Date;

public class FollowBean {
    private UserBean user;
    private Date date;

    public FollowBean(UserBean user, Date date) {
        this.user = user;
        this.date = date;
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
}
