package com.xf.live.ui.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class GiftBean implements Serializable {

    private String gid;//礼物id
    private String name;//名字
    private String coin;//金币数量
    private String msgcontent;//礼物内容
    private String num;//礼物数量
    private String imageUrl;//图片的url
    private int image;
    private String selected;//0 未选中，1选中

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getMsgcontent() {
        return msgcontent;
    }

    public void setMsgcontent(String msgcontent) {
        this.msgcontent = msgcontent;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
