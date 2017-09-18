package com.xf.live.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xf.live.R;
import com.xf.live.ui.bean.GiftBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private List<GiftBean> giftList;

    public GridViewAdapter(Context context,List<GiftBean> giftList) {
        this.context = context;
        this.giftList=giftList;
    }

    @Override
    public int getCount() {
        return giftList.size();
    }

    @Override
    public GiftBean getItem(int position) {
        return giftList.get( position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup viewGroup) {
        ViewHold hold=null;

        if (convertview==null){
            hold=new ViewHold();
            convertview= View.inflate(context, R.layout.item_gridview, null);
            hold.iv_gift= (ImageView) convertview.findViewById(R.id.iv_gift);
            hold.iv_select= (ImageView) convertview.findViewById(R.id.iv_select);
            hold.tv_coin= (TextView) convertview.findViewById(R.id.tv_coin);
            convertview.setTag(hold);
        }else {
            hold= (ViewHold) convertview.getTag();
        }

        hold.iv_gift.setImageResource(getItem(position).getImage());

        if (getItem(position).getSelected().equals("0")){
            hold.iv_select.setVisibility(View.INVISIBLE);
        }else {
            hold.iv_select.setVisibility(View.VISIBLE);
        }

        hold.tv_coin.setText(getItem(position).getCoin());

        return convertview;
    }


    static class ViewHold{
         ImageView iv_gift;
         ImageView iv_select;
         TextView tv_coin;
    }




}
