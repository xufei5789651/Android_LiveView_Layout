package com.xf.live.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xf.live.R;

/**
 * Created by Administrator on 2017/6/23 0023.
 */
public class HZAdapter extends BaseAdapter {
    private Context mContext;

    public HZAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        convertview = View.inflate(mContext, R.layout.item_hz, null);
        return convertview;
    }
}
