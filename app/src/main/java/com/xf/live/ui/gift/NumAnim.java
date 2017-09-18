package com.xf.live.ui.gift;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * 数字缩放
 * Created by Administrator on 2017/6/19 0019.
 */
public class NumAnim {
    private Animator lastAnimator = null;

    public void start(View view) {
        if (lastAnimator != null) {
            lastAnimator.removeAllListeners();
            lastAnimator.end();
            lastAnimator.cancel();
        }
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "scaleX", 1.3f, 1.0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleY", 1.3f, 1.0f);
        AnimatorSet animSet = new AnimatorSet();
        lastAnimator = animSet;
        animSet.setDuration(200);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();
    }
}
