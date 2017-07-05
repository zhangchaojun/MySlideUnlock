package com.myslideunlock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private SlideUnlockView2 slideUnlockView2;
    private LinearLayout activity_main2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        slideUnlockView2 = (SlideUnlockView2) findViewById(R.id.slideUnlockView2);
        activity_main2 = (LinearLayout) findViewById(R.id.activity_main2);

        slideUnlockView2.setOnUnLockListener(new SlideUnlockView.OnUnLockListener() {
            @Override
            public void unLock(boolean lock) {
                Toast.makeText(Main2Activity.this,"解锁",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
