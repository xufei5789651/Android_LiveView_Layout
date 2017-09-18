package com.xf.live.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xf.live.R;
import com.xf.live.ui.view.GiftDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void onbtnclick(View view) {
        if (view.getId() == R.id.audience) {
            Intent intent=new Intent(this,AudienceActivity.class);
            startActivity(intent);
        } else {

        }

    }
}
