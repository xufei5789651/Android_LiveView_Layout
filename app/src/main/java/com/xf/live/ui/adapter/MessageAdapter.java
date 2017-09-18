package com.xf.live.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xf.live.R;
import com.xf.live.ui.bean.TextMessage;

import java.util.List;

/**
 * Created by Administrator on 2017/6/22 0022.
 */
public class MessageAdapter extends BaseAdapter {

    private Context mContext;

    private List<TextMessage> dataList;

    public MessageAdapter(Context mContext, List<TextMessage> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        ViewHold viewHold=null;
        if (convertview==null){
            viewHold=new ViewHold();
            convertview=LayoutInflater.from(mContext).inflate(R.layout.item_message,null);
            viewHold.iv_fans= (ImageView) convertview.findViewById(R.id.iv_fans);
            viewHold.iv_gift= (ImageView) convertview.findViewById(R.id.iv_gift);
            viewHold.tv_content= (TextView) convertview.findViewById(R.id.tv_content);
            viewHold.tv_level= (TextView) convertview.findViewById(R.id.tv_level);
            convertview.setTag(viewHold);
        }else {
            viewHold= (ViewHold) convertview.getTag();
        }
        if (dataList.get(i).isfans()==true){
            viewHold.iv_fans.setVisibility(View.VISIBLE);
        }else {
            viewHold.iv_fans.setVisibility(View.GONE);
        }
        viewHold.tv_content.setText(dataList.get(i).getName()+":"+dataList.get(i).getContent());
        SpannableStringBuilder builder = new SpannableStringBuilder(viewHold.tv_content.getText().toString());

        ForegroundColorSpan pinkSpan  = new ForegroundColorSpan(mContext.getResources().getColor(R.color.pink_ff34b3));
        builder.setSpan(pinkSpan, 0, dataList.get(i).getName().length()+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (dataList.get(i).getImg()==0){
            viewHold.iv_gift.setVisibility(View.GONE);
            ForegroundColorSpan whiteSpan = new ForegroundColorSpan(Color.WHITE);
            builder.setSpan(whiteSpan,dataList.get(i).getName().length()+1, builder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        }else {
            viewHold.iv_gift.setVisibility(View.VISIBLE);
            viewHold.iv_gift.setImageResource(dataList.get(i).getImg());
            ForegroundColorSpan yellowSpan = new ForegroundColorSpan(Color.YELLOW);
            builder.setSpan(yellowSpan,dataList.get(i).getName().length()+1, builder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        }
        viewHold.tv_content.setText(builder);
        viewHold.tv_level.setText(dataList.get(i).getLevel());

        return convertview;
    }


    static class ViewHold {
        TextView tv_level;
        TextView tv_content;
        ImageView iv_fans;
        ImageView iv_gift;
    }

    public void addMsg(TextMessage msg) {
        dataList.add(msg);
        notifyDataSetChanged();
    }

}
