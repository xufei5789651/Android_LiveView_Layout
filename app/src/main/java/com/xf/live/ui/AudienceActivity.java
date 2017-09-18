package com.xf.live.ui;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xf.live.R;
import com.xf.live.ui.adapter.VPAdapter;

import java.util.ArrayList;
import java.util.List;

public class AudienceActivity extends AppCompatActivity {

    private List<Fragment> fragmentList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience);

        initView();
    }

    private void initView() {
        ViewPager  vp= (ViewPager) findViewById(R.id.vp);
        ChatFragment chatFragment=new ChatFragment();
        EmptyFragment emptyFragment=new EmptyFragment();
        fragmentList.add(emptyFragment);
        fragmentList.add(chatFragment);
        vp.setAdapter(new VPAdapter(getSupportFragmentManager(),fragmentList));
        vp.setCurrentItem(1);

    }


}
