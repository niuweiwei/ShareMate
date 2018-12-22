package cn.edu.hebtu.software.sharemate.Bean;

import java.io.Serializable;

public class UserBean implements Serializable {

    private String userName;
    private String userPassword;
    private String userPhone;
    private int userPhoto;
    private int userId;
    private String userSex;
    private String userAddress;
    private String userBirth;
    private String userIntroduce;

    public UserBean(){}
    public UserBean(Object object){}
    public UserBean(String userName, int userPhoto) {
        this.userName = userName;
        this.userPhoto = userPhoto;
    }
    public UserBean(int userId, int userPhoto, String userName, String userSex,
                    String userAddress, String userBirth, String userIntroduce) {
        this.userId = userId;
        this.userPhoto = userPhoto;
        this.userName = userName;
        this.userSex = userSex;
        this.userAddress = userAddress;
        this.userBirth = userBirth;
        this.userIntroduce = userIntroduce;
    }
    public UserBean(String userName, String userPassword, String userPhone, int userPhoto,
                    int userId, String userSex, String userAddress, String userBirth, String userIntroduce) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userPhoto = userPhoto;
        this.userId = userId;
        this.userSex = userSex;
        this.userAddress = userAddress;
        this.userBirth = userBirth;
        this.userIntroduce = userIntroduce;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(int userPhoto) {
        this.userPhoto = userPhoto;
    }

    public int getUserId() {return userId;}

    public void setUserId(int userId) {this.userId = userId;}

    public String getUserSex() {return userSex;}

    public void setUserSex(String userSex) {this.userSex = userSex;}

    public String getUserAddress() {return userAddress;}

    public void setUserAddress(String userAddress) {this.userAddress = userAddress;}

    public String getUserBirth() {return userBirth;}

    public void setUserBirth(String userBirth) {this.userBirth = userBirth;}

    public String getUserIntroduce() {return userIntroduce;}

    public void setUserIntroduce(String userIntroduce) {this.userIntroduce = userIntroduce;}

    @Override
    public String toString() {
        return "UserBean{" +
                "userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userPhoto=" + userPhoto +
                '}';
    }
}
