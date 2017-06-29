package com.example.wenzty.news.activity;

import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.wenzty.news.R;
import com.example.wenzty.news.bean.NewsEntity;

/**
 * Created by wenzty on 2017/6/29.
 */

public class NewsDetailActivity extends BaseActivity {
    private WebView mWebView;
    private ProgressBar mProgressBar;
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initDate() {
        // 上一个界面传过来的新闻数据
        NewsEntity.ResultBean newsBean = (NewsEntity.ResultBean)
                getIntent().getSerializableExtra("news");

        String newUrl = newsBean.getUrl();
        mWebView.loadUrl(newUrl);
    }

    @Override
    protected void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.pb_01);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            initWebView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100){
                    mProgressBar.setVisibility(View.GONE);
                }else {
                    mProgressBar.setProgress(newProgress);
                }

                super.onProgressChanged(view, newProgress);
            }
        });
    }
}
