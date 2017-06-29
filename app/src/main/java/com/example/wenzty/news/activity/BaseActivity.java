package com.example.wenzty.news.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity  extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        initView();
        initListener();
        initDate();
    }
    /**  設置fragment顯示的佈局文件*/
    protected abstract int getLayoutRes();
    /** 控件的监听器*/
    protected abstract void initListener();
    /** 初始化数据*/
    protected abstract void initDate();
    /** 查找子控件*/
    protected abstract void initView();

    private Toast mToast;

    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }


}
