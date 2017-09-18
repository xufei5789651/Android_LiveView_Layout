package com.xf.live.ui.gift;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xf.live.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/19 0019.
 */
public class GiftUtil {

    private LinearLayout layout;
    private String tag;
    private List<View> giftViewCollection = new ArrayList<View>();
    private static GiftUtil giftUtil;
    private Activity context;
    private TranslateAnimation inAnim;
    private TranslateAnimation outAnim;
    private NumAnim giftNumAnim;
    private int resid;
    private Timer timer;

    private GiftUtil() {

    }

    public static GiftUtil getInstance() {
        if (giftUtil == null) {
            giftUtil = new GiftUtil();
        }
        return giftUtil;
    }

    public GiftUtil setLayoutWithTag(LinearLayout layout, String tag, Activity context, int resid) {
        this.layout = layout;
        this.tag = tag;
        this.context = context;
        this.resid = resid;
        initAnim();
        if (giftViewCollection.size()!=0){
            giftViewCollection.clear();
        }
        return this;
    }

    private void initAnim() {
        inAnim = (TranslateAnimation) AnimationUtils.loadAnimation(context, R.anim.gift_in);
        outAnim = (TranslateAnimation) AnimationUtils.loadAnimation(context, R.anim.gift_out);
        giftNumAnim = new NumAnim();

    }

    public GiftUtil showGiftView() {

        View giftView = layout.findViewWithTag(tag);
        if (giftView == null) {//该类型礼物不在礼物列表显示
            if (layout.getChildCount() > 2) {//如果正在显示的礼物的个数超过两个,移除显示时间最长
                View giftView1 = layout.getChildAt(0);
                ImageView iv_gift1 = (ImageView) giftView1.findViewById(R.id.iv_gift);
                long lastTime1 = (Long) iv_gift1.getTag();

                View giftView2 = layout.getChildAt(1);
                ImageView iv_gift2 = (ImageView) giftView2.findViewById(R.id.iv_gift);
                long lastTime2 = (Long) iv_gift2.getTag();

                if (lastTime1 > lastTime2) {/*如果第二个View显示的时间比较长*/
                    removeGiftView(1);
                } else {/*如果第一个View显示的时间长*/
                    removeGiftView(0);
                }
            }

            giftView = addGiftView();
            giftView.setTag(tag);
            final TextView tv_gift_num=initViewData(true,giftView);
            layout.addView(giftView);//将礼物的View添加到礼物的ViewGroup中
            layout.invalidate();//刷新该view
            giftView.startAnimation(inAnim);

            inAnim.setAnimationListener(new Animation.AnimationListener() {//显示动画的监听
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    giftNumAnim.start(tv_gift_num);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

        } else {
            TextView tv_gift_num_1=initViewData(false,giftView);
            giftNumAnim.start(tv_gift_num_1);
        }

        return this;
    }


    /**
     * 移除礼物
     *
     * @param index
     */

    private void removeGiftView(final int index) {
        final View remove_giftview = layout.getChildAt(index);
        outAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layout.removeViewAt(index);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    remove_giftview.startAnimation(outAnim);

            }
        });
    }

    private TextView initViewData(boolean isFirst, View giftView) {
        if (isFirst) {
            TextView tv_gift_num = (TextView) giftView.findViewById(R.id.tv_gift_num);
            ImageView iv_gift = (ImageView) giftView.findViewById(R.id.iv_gift);
            iv_gift.setImageResource(resid);
            iv_gift.setTag(System.currentTimeMillis());
            tv_gift_num.setText("x1");//设置礼物数量
            tv_gift_num.setTag(1);//给数量控件设置标记
            return tv_gift_num;
        } else {
            TextView tv_gift_num_1 = (TextView) giftView.findViewById(R.id.tv_gift_num);
            ImageView iv_gift = (ImageView) giftView.findViewById(R.id.iv_gift);
            iv_gift.setImageResource(resid);
            iv_gift.setTag(System.currentTimeMillis());
            tv_gift_num_1.setText("x" + ((int) tv_gift_num_1.getTag() + 1));
            tv_gift_num_1.setTag(((int) tv_gift_num_1.getTag() + 1));

            return tv_gift_num_1;
        }

    }

    /**
     * 获取礼物的View的布局
     *
     * @return
     */
    private View addGiftView() {
        View view = null;
        if (giftViewCollection.size() == 0) {//如果垃圾回收中没有view,则生成一个
            view = LayoutInflater.from(context).inflate(R.layout.gift_item_layout, null);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = 10;
            view.setLayoutParams(lp);
            layout.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View view) {
                }

                @Override
                public void onViewDetachedFromWindow(View view) {
                    giftViewCollection.add(view);
                }
            });

        } else {
            view = giftViewCollection.get(0);
            giftViewCollection.remove(view);
        }

        return view;
    }


    public GiftUtil clearGift(final LinearLayout layout) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int count = layout.getChildCount();
                for (int i = 0; i < count; i++) {
                    View view = layout.getChildAt(i);
                    ImageView iv_gift = (ImageView) view.findViewById(R.id.iv_gift);
                    long nowtime = System.currentTimeMillis();
                    long upTime = (Long) iv_gift.getTag();
                    if ((nowtime - upTime) >= 3000) {
                        removeGiftView(i);
                        return;
                    }
                }
            }
        };

        timer = new Timer();
        timer.schedule(task, 0, 3000);

        return this;
    }

    public void cancle(){
        timer.cancel();
        timer=null;
    }
}
