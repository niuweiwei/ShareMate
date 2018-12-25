package cn.edu.hebtu.software.sharemate.Bean;

import java.io.Serializable;

public class ShopBean implements Serializable{
    private int photo;
    private String name;
    private String issure;
    private String price;
    private String  youhui;

    public ShopBean(int photo, String name, String issure , String price, String youhui) {
        this.photo = photo;
        this.name = name;
        this.issure=issure;
        this.price = price;
        this.youhui = youhui;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssure() {
        return issure;
    }

    public void setIssure(String issure) {
        this.issure = issure;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getYouhui() {
        return youhui;
    }

    public void setYouhui(String youhui) {
        this.youhui = youhui;
    }
}
