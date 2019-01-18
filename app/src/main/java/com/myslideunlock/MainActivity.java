package com.myslideunlock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * 测试一下能不能上传
 */
public class MainActivity extends AppCompatActivity {

    private SlideUnlockView slideUnlockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        slideUnlockView = (SlideUnlockView) findViewById(R.id.slideUnlockView);
        slideUnlockView.setOnUnLockListener(new SlideUnlockView.OnUnLockListener() {
            @Override
            public void unLock(boolean lock) {
                Toast.makeText(MainActivity.this, "解锁成功", Toast.LENGTH_SHORT).show();
                Intent intent  = new Intent();
                intent.setClass(MainActivity.this,Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
