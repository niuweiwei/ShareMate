package cn.edu.hebtu.software.sharemate.Bean;

import java.util.Date;

public class LetterBean  {

    private UserBean user;
    private String content;
    private Date date;

    public LetterBean(UserBean user, String content, Date date) {
        this.user = user;
        this.content = content;
        this.date = date;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
