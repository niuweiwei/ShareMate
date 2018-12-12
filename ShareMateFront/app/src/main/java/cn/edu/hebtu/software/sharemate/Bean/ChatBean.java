package cn.edu.hebtu.software.sharemate.Bean;

public class ChatBean {

    private UserBean user ;
    private String message;
    private int type;//用来标记是否是当前用户发送的消息 0:自己发出的消息 1:表示对方发出的消息

    public ChatBean(UserBean user, String message, int type) {
        this.user = user;
        this.message = message;
        this.type = type;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
