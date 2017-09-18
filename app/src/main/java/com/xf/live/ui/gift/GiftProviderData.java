package com.xf.live.ui.gift;

import com.xf.live.R;
import com.xf.live.ui.bean.GiftBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class GiftProviderData {

    public static int[] resID = {//图片资源id
            R.mipmap.gift_0, R.mipmap.gift_1, R.mipmap.gift_2, R.mipmap.gift_3,
            R.mipmap.gift_4, R.mipmap.gift_5, R.mipmap.gift_6, R.mipmap.gift_7,
            R.mipmap.gift_8, R.mipmap.gift_9, R.mipmap.gift_10, R.mipmap.gift_11,
            R.mipmap.gift_12, R.mipmap.gift_13, R.mipmap.gift_14, R.mipmap.gift_15,
            R.mipmap.gif_16, R.mipmap.gif_17, R.mipmap.gif_18, R.mipmap.gif_19,
            R.mipmap.gif_20, R.mipmap.gif_21, R.mipmap.gif_22, R.mipmap.gif_23,
    };

    private static String[] giftContent={
            "我送你一颗爱心","我送你一根星光棒","我送你一支玫瑰","我送你一只金话筒",
            "我送你一个吻","我送你一个时间胶囊","我送你一份披萨","我送你一颗金麦穗",
            "我送你一只宠物猫","我送你一个电风扇","我送你一块金砖","我送你一个梨花",
            "我送你一只宠物狗","我送你一个签名","我送你稻香满园","我送你一个地球心",
            "我送你一只兔耳朵","我送你一条项链","我送你满满爱心","我送你爱的信盏",
            "我送你满天星","我送你无限星光","我送你天使","我送你啤酒",
    };

    private static String coin[] = {//金币数量
            "1", "9", "39", "99",
            "1", "9", "39", "99",
            "1", "9", "39", "99",
            "5", "19", "69", "199",
            "5", "19", "69", "199",
            "5", "19", "69", "199"
    };
    private static String giftID[] = {//礼物id
            "1", "2", "3", "4",
            "5", "6", "7", "8",
            "9", "10", "11", "12",
            "13", "14", "15", "16",
            "17", "18", "19", "20",
            "21", "22", "23", "24"
    };


    public static List<GiftBean> getGiftList() {
        List<GiftBean> giftList = new ArrayList<>();
        for (int i = 0; i < resID.length; i++) {
            GiftBean gift = new GiftBean();
            gift.setCoin(coin[i]);
            gift.setImage(resID[i]);
            gift.setGid(giftID[i]);
            gift.setMsgcontent(giftContent[i]);
            gift.setSelected("0");
            giftList.add(gift);
        }

        return giftList;
    }


}
