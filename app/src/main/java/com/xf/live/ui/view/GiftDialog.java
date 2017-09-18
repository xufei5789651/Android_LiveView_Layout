package com.xf.live.ui.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xf.live.R;
import com.xf.live.ui.gift.GiftProviderData;
import com.xf.live.ui.adapter.GridViewAdapter;
import com.xf.live.ui.bean.GiftBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class GiftDialog extends DialogFragment {
    private static final String TAG = GiftDialog.class.getSimpleName();
    private List<GridView> gridViews = new ArrayList<>();
    private RadioGroup rgroup;
    private GridViewAdapter adapter_0;
    private GridViewAdapter adapter_1;
    private GridViewAdapter adapter_2;
    int lastIndex = 0;
    private List<List<GiftBean>> gifts;
    private List<GridViewAdapter> adapters;
    private GiftBean giftSelected;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_gift, null);
        Dialog dialog = initDialogStyle(inflate);
        initView(inflate);
        return dialog;
    }

    private void initView(View inflate) {
        ViewPager viewpager = (ViewPager) inflate.findViewById(R.id.viewpager);
        rgroup = (RadioGroup) inflate.findViewById(R.id.rgroup);
        RadioButton radioButton = (RadioButton) rgroup.getChildAt(0);
        radioButton.setChecked(true);
        Button btn_send = (Button) inflate.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null && giftSelected != null) {
                    listener.onItemGiftClick(giftSelected);
                }
            }
        });

        List<GiftBean> giftList = GiftProviderData.getGiftList();
        final List<GiftBean> giftList_0 = new ArrayList<>();
        final List<GiftBean> giftList_1 = new ArrayList<>();
        final List<GiftBean> giftList_2 = new ArrayList<>();
        for (int i = 0; i < giftList.size(); i++) {
            if (i < 8) {
                giftList_0.add(giftList.get(i));
            } else if (i < 16) {
                giftList_1.add(giftList.get(i));
            } else {
                giftList_2.add(giftList.get(i));
            }
        }
        gifts = new ArrayList<>();
        gifts.add(giftList_0);
        gifts.add(giftList_1);
        gifts.add(giftList_2);


        //第一个gridview
        GridView gridView0 = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.gridview_gift, null);
        adapter_0 = new GridViewAdapter(getActivity(), giftList_0);
        gridView0.setAdapter(adapter_0);
        gridView0.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                notifyDataSelect(giftList_0, i, adapter_0);
            }
        });


        //第二个gridview
        GridView gridView1 = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.gridview_gift, null);
        adapter_1 = new GridViewAdapter(getActivity(), giftList_1);
        gridView1.setAdapter(adapter_1);
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                notifyDataSelect(giftList_1, i, adapter_1);
            }
        });


        //第三个gridview
        GridView gridView2 = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.gridview_gift, null);
        adapter_2 = new GridViewAdapter(getActivity(), giftList_2);
        gridView2.setAdapter(adapter_2);
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                notifyDataSelect(giftList_2, i, adapter_2);

            }
        });

        gridViews.add(gridView0);
        gridViews.add(gridView1);
        gridViews.add(gridView2);

        adapters = new ArrayList<>();
        adapters.add(adapter_0);
        adapters.add(adapter_1);
        adapters.add(adapter_2);

        viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return gridViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(gridViews.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(gridViews.get(position));
                return gridViews.get(position);
            }
        });


        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton radioButton = (RadioButton) rgroup.getChildAt(position);
                radioButton.setChecked(true);
                notifyNOselect(gifts.get(position), adapters.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private Dialog initDialogStyle(View view) {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogStyle);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    private void notifyDataSelect(List<GiftBean> list, int position, GridViewAdapter adapter) {
        list.get(position).setSelected("1");
        if (position != lastIndex) {
            list.get(lastIndex).setSelected("0");
        }
        lastIndex = position;
        adapter.notifyDataSetChanged();
        giftSelected = list.get(position);
    }

    private void notifyNOselect(List<GiftBean> list, GridViewAdapter adapter) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSelected("0");
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onStop() {
        super.onStop();
        if (onHideListener != null) {
            onHideListener.onHide();
        }
    }


    private OnHideListener onHideListener;

    public interface OnHideListener {
        void onHide();
    }

    public GiftDialog setOnHideListener(OnHideListener onHideListener) {
        this.onHideListener = onHideListener;
        return this;
    }

    private OnItemGiftClickListener listener;

    public interface OnItemGiftClickListener {
        void onItemGiftClick(GiftBean gift);
    }

    public GiftDialog setOnItemGiftClickListener(OnItemGiftClickListener listener) {
        this.listener = listener;
        return this;
    }
}
