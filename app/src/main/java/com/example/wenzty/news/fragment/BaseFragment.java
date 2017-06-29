package com.example.wenzty.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by wenzty on 2017/6/27.
 */

public abstract class BaseFragment extends Fragment{
    /**  Fragment要显示的界面*/
    public View mRootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, 
                             @Nullable ViewGroup container, 
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            mRootView = LayoutInflater.from(getContext()).inflate(
                    getLayoutRes(),container,false
            );
            initView();
            initListener();
            initDate();
        }
        return mRootView;
    }

    protected abstract int getLayoutRes();
    public abstract void initView();
    public abstract void initDate();
    public abstract void initListener();

    private Toast mToast;

    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
