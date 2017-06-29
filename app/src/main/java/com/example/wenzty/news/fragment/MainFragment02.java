package com.example.wenzty.news.fragment;

import com.example.wenzty.news.R;
import com.example.wenzty.news.base.URLManager;
import com.example.wenzty.news.bean.VideoEntity;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by wenzty on 2017/6/27.
 */

public class MainFragment02 extends BaseFragment{
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_02;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initDate() {
        getVideoDatas();
    }

    private void getVideoDatas() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, URLManager.VideoURL,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String json = responseInfo.result;
                        System.out.print("--------json" + json);

                        json = json.replace("V9LG4B3A0","result");
                        Gson gson = new Gson();
                        VideoEntity entity = gson.fromJson(json,VideoEntity.class);
                        int count = entity.getResult().size();
                        System.out.println("--------解析：" + count + " 个视频");
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("--------------"+error);
                    }
                });
    }

    @Override
    public void initListener() {

    }


}
