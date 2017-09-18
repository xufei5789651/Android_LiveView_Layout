package com.xf.live.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xf.live.R;
import com.xf.live.ui.adapter.HZAdapter;
import com.xf.live.ui.adapter.MessageAdapter;
import com.xf.live.ui.adapter.SoftKeyBoardListener;
import com.xf.live.ui.bean.GiftBean;
import com.xf.live.ui.bean.TextMessage;
import com.xf.live.ui.gift.GiftUtil;
import com.xf.live.ui.view.GiftDialog;
import com.xf.live.ui.view.HorizontialListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tyrantgit.widget.HeartLayout;

/**
 * Created by Administrator on 2017/6/13 0013.
 */
public class ChatFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = ChatFragment.class.getSimpleName();
    private InputMethodManager imm;
    private LinearLayout ll_send_msg;
    private EditText et_msg;

    private AnimatorSet animatorSetHide = new AnimatorSet();
    private AnimatorSet animatorSetShow = new AnimatorSet();
    private LinearLayout ll_top_image;
    private RelativeLayout rl_num;
    private RelativeLayout rl_bottom_layout;
    private LinearLayout ll_gift;
    private GiftUtil giftUtil;
    private ListView lv_msg;
    private TextView tv_send;
    List<TextMessage> dataList =new ArrayList<>();
    private MessageAdapter adapter;
    private Random mRandom;
    private HeartLayout heart_layout;
    private ImageView iv_switch;
    private boolean isOn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_chat, null);
        initView(inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_send_msg.getVisibility() == View.VISIBLE) {
                    hideKeyboard();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        softKeyboardListnenr();
    }

    private void softKeyboardListnenr() {
        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {//软键盘显示，头部动画隐藏
//                Log.e(TAG,"keyBoardShow-----------------"+height);
                animateToHide();
                dynamicChangeGiftParentH(true);
                dynamicChangeListviewH(100);

            }

            @Override
            public void keyBoardHide(int height) {//软键盘隐藏，头部动画显示
//                Log.e(TAG,"keyBoardHide---------"+height);

                ll_send_msg.setVisibility(View.GONE);
                animateToShow();
                dynamicChangeGiftParentH(false);
                dynamicChangeListviewH(150);

            }
        });
    }

    private void initView(View inflate) {
        mRandom = new Random();
        ImageView iv_send_msg = (ImageView) inflate.findViewById(R.id.iv_send_msg);
        ImageView message = (ImageView) inflate.findViewById(R.id.message);
        ImageView gift = (ImageView) inflate.findViewById(R.id.gift);
        ImageView share = (ImageView) inflate.findViewById(R.id.share);
        ImageView close = (ImageView) inflate.findViewById(R.id.close);

        iv_send_msg.setOnClickListener(this);
        message.setOnClickListener(this);
        gift.setOnClickListener(this);
        share.setOnClickListener(this);
        close.setOnClickListener(this);

        HorizontialListView hzlv= (HorizontialListView) inflate.findViewById(R.id.hzlv);
        ll_send_msg = (LinearLayout) inflate.findViewById(R.id.ll_send_msg);
        ll_gift = (LinearLayout) inflate.findViewById(R.id.ll_gift);
        ll_top_image = (LinearLayout) inflate.findViewById(R.id.ll_top_image);
        rl_num = (RelativeLayout) inflate.findViewById(R.id.rl_num);
        rl_bottom_layout = (RelativeLayout) inflate.findViewById(R.id.rl_bottom_layout);
        et_msg = (EditText) inflate.findViewById(R.id.et_msg);
        lv_msg = (ListView) inflate.findViewById(R.id.lv_msg);
        heart_layout = (HeartLayout) inflate.findViewById(R.id.heart_layout);
        tv_send = (TextView) inflate.findViewById(R.id.tv_send);
        tv_send.setOnClickListener(this);

        iv_switch = (ImageView) inflate.findViewById(R.id.iv_switch);
        iv_switch.setOnClickListener(this);

        giftUtil = GiftUtil.getInstance();
        giftUtil.clearGift(ll_gift);

        adapter = new MessageAdapter(getContext(),dataList);
        lv_msg.setAdapter(adapter);
        lv_msg.setSelection(dataList.size());

        hzlv.setAdapter(new HZAdapter(getContext()));

    }


    Handler heartHandler=new Handler();
    Runnable heartRunnable=new Runnable() {
        @Override
        public void run() {
            heart_layout.post(new Runnable() {
                @Override
                public void run() {
                    heart_layout.addHeart(randomColor());
                }
            });
            heartHandler.postDelayed(this, 1000);
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_send_msg:
                showChat();
                break;
            case R.id.message:

                break;
            case R.id.gift:
                showGift();
                break;
            case R.id.share:

                break;
            case R.id.close:
                getActivity().finish();
                break;
            case R.id.tv_send:
                sendMessage();
                break;
            case R.id.iv_switch:
                if (isOn){
                    iv_switch.setImageResource(R.mipmap.barrage_view_toggle_btn_unchecked);
                    isOn=false;
                }else {
                    iv_switch.setImageResource(R.mipmap.barrage_view_toggle_btn_checked);
                    isOn=true;
                }
                break;
        }
    }

    /**
     * 发送消息
     */
    private void sendMessage() {
        if (!TextUtils.isEmpty(et_msg.getText().toString().trim())) {
            TextMessage msg=new TextMessage();
            msg.setContent(et_msg.getText().toString().trim());
            msg.setLevel("12");
            msg.setName("JACK");
            msg.setIsfans(true);
            msg.setImg(0);
            adapter.addMsg(msg);
            lv_msg.setSelection(dataList.size());
            et_msg.setText("");
        }
    }

    /**
     * 展示礼物列表
     */
    private void showGift() {
        new GiftDialog().setOnItemGiftClickListener(new GiftDialog.OnItemGiftClickListener() {
            @Override
            public void onItemGiftClick(GiftBean gift) {
                giftUtil.setLayoutWithTag(ll_gift, gift.getGid(), getActivity(), gift.getImage())
                        .showGiftView();

                TextMessage msg=new TextMessage();
                msg.setContent(gift.getMsgcontent());
                msg.setLevel("12");
                msg.setName("JACK");
                msg.setIsfans(false);
                msg.setImg(gift.getImage());
                adapter.addMsg(msg);
                lv_msg.setSelection(dataList.size());

            }
        }).setOnHideListener(new GiftDialog.OnHideListener() {
            @Override
            public void onHide() {
                giftShowAnim();
                lv_msg.setVisibility(View.VISIBLE);
            }
        }).show(getFragmentManager(), "giftdialog");
        lv_msg.setVisibility(View.INVISIBLE);
        giftHideAnim();
    }

    /**
     * 底部礼物菜单隐藏
     */
    private void giftHideAnim() {
        ObjectAnimator bottomHideAnim = ObjectAnimator.ofFloat(rl_bottom_layout, "translationY", 0, rl_bottom_layout.getHeight());
        bottomHideAnim.setDuration(300);
        bottomHideAnim.start();
    }

    /**
     * 底部礼物菜单显示
     */
    private void giftShowAnim() {
        ObjectAnimator bottomShowAnim = ObjectAnimator.ofFloat(rl_bottom_layout, "translationY", rl_bottom_layout.getHeight(), 0);
        bottomShowAnim.setDuration(300);
        bottomShowAnim.start();
    }

    private void showChat() {
        ll_send_msg.setVisibility(View.VISIBLE);
        ll_send_msg.requestFocus();
        imm.showSoftInput(et_msg, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_msg.getWindowToken(), 0);
    }


    /**
     * 头部布局隐藏
     */
    private void animateToHide() {

        ObjectAnimator leftAnim = ObjectAnimator.ofFloat(rl_num, "translationX", 0, -rl_num.getWidth());
        ObjectAnimator topAnim = ObjectAnimator.ofFloat(ll_top_image, "translationY", 0, -ll_top_image.getHeight());
        animatorSetHide.playTogether(leftAnim, topAnim);
        animatorSetHide.setDuration(300);
        animatorSetHide.start();
    }


    /**
     * 头部布局显示
     */
    private void animateToShow() {
        ObjectAnimator leftAnim = ObjectAnimator.ofFloat(rl_num, "translationX", -rl_num.getWidth(), 0);
        ObjectAnimator topAnim = ObjectAnimator.ofFloat(ll_top_image, "translationY", -ll_top_image.getHeight(), 0);
        animatorSetShow.playTogether(leftAnim, topAnim);
        animatorSetShow.setDuration(300);
        animatorSetShow.start();

    }

    /**
     * 动态修改礼物父布局的高度
     *
     * @param showhide
     */
    private void dynamicChangeGiftParentH(boolean showhide) {
        if (showhide) {//如果软键盘显示中
            if (ll_gift.getChildCount() != 0) {//判断是否有礼物显示，如果有就修改父布局高度，如果没有就不作任何操作

                ViewGroup.LayoutParams layoutParams = ll_gift.getLayoutParams();
                layoutParams.height = ll_gift.getChildAt(0).getHeight();
                ll_gift.setLayoutParams(layoutParams);
            }
        } else {//如果软键盘隐藏中,就将装载礼物的容器的高度设置为包裹内容
            ViewGroup.LayoutParams layoutParams = ll_gift.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            ll_gift.setLayoutParams(layoutParams);
        }
    }

    /**
     * 动态的修改listview的高度
     * @param heightPX
     */
    private void dynamicChangeListviewH(int heightPX) {
        ViewGroup.LayoutParams layoutParams = lv_msg.getLayoutParams();
        layoutParams.height= DisplayUtil.dip2px(getActivity(),heightPX);
        lv_msg.setLayoutParams(layoutParams);
    }

    private int randomColor() {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
    }


    @Override
    public void onResume() {
        super.onResume();
        heartHandler.postDelayed(heartRunnable, 2000);

    }

    @Override
    public void onPause() {
        super.onPause();
        heartHandler.removeCallbacks(heartRunnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        giftUtil.cancle();
    }
}
