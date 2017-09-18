package com.xf.live.ui.bean;

/**
 * Created by Administrator on 2017/6/22 0022.
 */
public class TextMessage {
    private String name;//名字
    private String content;//内容
    private int img;//礼物
    private String level;//等级
    private boolean isfans;//是否为粉丝

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean isfans() {
        return isfans;
    }

    public void setIsfans(boolean isfans) {
        this.isfans = isfans;
    }
}
