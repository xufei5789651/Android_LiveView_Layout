package com.xf.live.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xf.live.R;

/**
 * Created by Administrator on 2017/6/13 0013.
 */
public class EmptyFragment extends Fragment {


    private static final String TAG = EmptyFragment.class.getSimpleName();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_empty, null);
//        Log.e(TAG,"--------------onCreateView");
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        ImageView close= (ImageView) inflate.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Log.e(TAG,"--------------onActivityCreated");

    }

    @Override
    public void onStart() {
        super.onStart();
//        Log.e(TAG,"--------------onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.e(TAG,"--------------onResume");
    }


    @Override
    public void onPause() {
        super.onPause();
//        Log.e(TAG,"--------------onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
//        Log.e(TAG,"--------------onStop");

    }

}
