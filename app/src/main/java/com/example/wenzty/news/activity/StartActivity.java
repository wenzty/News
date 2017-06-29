package com.example.wenzty.news.activity;

import android.content.Intent;
import android.os.SystemClock;

import com.example.wenzty.news.R;

public class StartActivity extends BaseActivity {


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_start;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initDate() {
            new Thread() {
                @Override
                public void run() {
                    SystemClock.sleep(1500);
                    enterGuideActivity();
                }
            }.start();
    }
/** 进入引导页*/
    private void enterGuideActivity() {
        Intent intent = new Intent(this,GuideActivity.class);
        startActivity(intent);
        finish();
    }
    private void enterMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void initView() {

    }
}
