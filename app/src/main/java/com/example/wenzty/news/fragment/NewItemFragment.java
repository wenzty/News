package com.example.wenzty.news.fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.wenzty.news.R;
import com.example.wenzty.news.activity.NewsDetailActivity;
import com.example.wenzty.news.adapter.NewsAdapter;
import com.example.wenzty.news.base.URLManager;
import com.example.wenzty.news.bean.NewsEntity;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.MeituanHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

/**
 * Created by wenzty on 2017/6/27.
 */

public class NewItemFragment extends BaseFragment {
    private TextView mTextView;
    private ListView listView;
    private   List<NewsEntity.ResultBean> listDatas;

    /**
     * 新闻类别id
     */
    private String newsCategoryId;


    /**
     * 设置新闻类别id
     */
    public void setNewsCategoryId(String newsCategoryId) {
        this.newsCategoryId = newsCategoryId;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.news_fragment;
    }

    @Override
    public void initView() {
        mTextView = (TextView) mRootView.findViewById(R.id.tv_01);
        listView = (ListView) mRootView.findViewById(R.id.list_view);
        //mTextView.setText("id:" + newsCategoryId);
            initSpringView();
    }

    private void initSpringView() {
        final SpringView springView = (SpringView) mRootView.findViewById(R.id.spring_view);
        springView.setHeader(new MeituanHeader(getContext()));
        springView.setFooter(new DefaultFooter(getContext()));

        springView.setType(SpringView.Type.FOLLOW);

        // 设置监听器
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {       // 下拉，刷新第一页数据
                showToast("下拉");

                // 请求服务器第一页数据,然后刷新
                // ...

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 2秒后，隐藏springView控件上拉和下拉提示
                        springView.onFinishFreshAndLoad();
                    }
                }, 2000);
            }

            @Override
            public void onLoadmore() {      // 上拉，加载下一页数据
                showToast("上拉");

                // 请求服务器下一页数据
                // ...

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 2秒后，隐藏springView控件上拉和下拉提示
                        springView.onFinishFreshAndLoad();
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void initDate() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        String newsUrl = URLManager.getUrl(newsCategoryId);
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, newsUrl, new RequestCallBack<String>() {
            @Override   // 请求成功
            public void onSuccess(ResponseInfo<String> responseInfo) {
                // （1）服务器返回的json数据
                String json = responseInfo.result;
                System.out.println("---------json: " + json);

                // （2）解析json数据
                // 替换json字符串中的新闻类别id
                json = json.replace(newsCategoryId, "result");
                Gson gson = new Gson();         // 使用到反射  fastjson
                NewsEntity newsEntity = gson.fromJson(json, NewsEntity.class);
                int count = newsEntity.getResult().size();
                System.out.println("--------解析：" + count + " 条新闻");
                // 列表显示的数据集合
                List<NewsEntity.ResultBean> listDatas = newsEntity.getResult();

                // （3）显示列表(数据，列表项布局，适配器BaseAdapter)
                showListView(listDatas);
            }

            @Override   // 请求失败
            public void onFailure(HttpException error, String msg) {
                System.out.println("---------error: " + error);
            }
        });
       /* String newsUrl = URLManager.getUrl(newsCategoryId);
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, newsUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                // 服务器返回的json数据
                String json = responseInfo.result;
                System.out.println("---------json: " + json);

                // 替换json字符串中的新闻类别id
                json = json.replace(newsCategoryId, "result");
                Gson gson = new Gson();
                // 使用到反射
                NewsEntity newsEntity = gson.fromJson(json, NewsEntity.class);
               System.out.println("--------解析：" + newsEntity.getResult().size() + " " +
                        "条新闻");

                List<NewsEntity.ResultBean> listDatas = newsEntity.getResult();

                showListView(listDatas);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
            }
        });*/
    }

    private void showListView(List<NewsEntity.ResultBean> listDatas) {
        NewsEntity.ResultBean firstNews = listDatas.get(0);
        if (firstNews.getAds() != null && firstNews.getAds().size() > 0) {
            View headerView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_header, listView, false);
            // 查找轮播图控件
            SliderLayout sliderLayout = (SliderLayout)
                    headerView.findViewById(R.id.slider_layout);
            // 准备轮播图要显示的数据
            List<NewsEntity.ResultBean.AdsBean> ads = firstNews.getAds();
            // 添加轮播图子界面
            for (int i = 0; i < ads.size(); i++) {
                NewsEntity.ResultBean.AdsBean bean = ads.get(i);

                // 一个TextSliderView表示一个子界面
                TextSliderView textSliderView = new TextSliderView(getContext());
                textSliderView.description(bean.getTitle())  // 显示标题
                        .image(bean.getImgsrc());      // 显示图片

                sliderLayout.addSlider(textSliderView);       // 添加一个子界面
            }

            // 添加到轮播图到列表的头部
            listView.addHeaderView(headerView);

        } else {
            // 没有轮播图的情况
        }

        // （2）显示列表
        NewsAdapter newsAdapter = new NewsAdapter(listDatas , getContext());
        listView.setAdapter(newsAdapter);
    }




   /* private void showDatas(NewsEntity newsEntity) {
        if (newsEntity == null || newsEntity.getResult() == null || newsEntity.getResult().size() == 0){
            System.out.print("---------------nothing news Data");
            return;
        }

        NewsAdapter newsAdapter = new NewsAdapter(, newsEntity.getResult());

        listView.setAdapter(newsAdapter);
    }*/

    @Override
    public void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               int index = position;

                if (listView.getHeaderViewsCount() >0){
                    index = index -1;
                }


                //NewsEntity.ResultBean news = listDatas.get(position);
                NewsEntity.ResultBean news = (NewsEntity.ResultBean)
                                 parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("news", news);
                startActivity(intent);
            }
        });
    }
}
