package com.example.wenzty.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wenzty.news.R;
import com.example.wenzty.news.bean.NewsEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by wenzty on 2017/6/28.
 */

public class NewsAdapter extends BaseAdapter{
    private Context context;
    private List<NewsEntity.ResultBean> listDatas;
    // 列表项类型：有1张图片的列表项
    private static final int ITEM_TYPE_1_IMAGE = 0;
    // 列表项类型：有3张图片的列表项
    private static final int ITEM_TYPE_3_IMAGE = 1;

    public NewsAdapter(List<NewsEntity.ResultBean> listDatas, Context context){
        this.context =  context;
        this.listDatas = listDatas;
    }


    @Override
    public int getCount() {
        return (listDatas == null) ? 0 : listDatas.size();
    }




    @Override
    public Object getItem(int position) {
        return listDatas.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        if (type == ITEM_TYPE_1_IMAGE) {        // 显示1张图片的item
            // 1. 创建列表项item视图
            if (convertView == null) {  // 为空时才创建列表项，提高列表效率
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.item_news_1, parent,false);
            }

            // 2. 查找列表项中的子控件
            ImageView iv01 = (ImageView) convertView.findViewById(R.id.iv_icon);
            TextView tvNewsTitle = (TextView) convertView.findViewById(R.id.tv_title);
            TextView tvNewsFrom = (TextView) convertView.findViewById(R.id.tv_source);
            TextView tvCommentCount = (TextView) convertView.findViewById(R.id.tv_comment);

            // 3. 获取列表项对应的数据（javabean）
            NewsEntity.ResultBean news = (NewsEntity.ResultBean) getItem(position);

            // 4. 显示列表项中的子控件
            tvNewsTitle.setText(news.getTitle());           // 显示标题
            tvNewsFrom.setText(news.getSource());           // 新闻来源
            tvCommentCount.setText(news.getReplyCount() + "跟帖"); // 新闻来源
            // 显示新闻图片
            Picasso.with(context).load(news.getImgsrc()).into(iv01);
        } else {                                // 显示3张图片的item
            // 1. 创建列表项item视图
            if (convertView == null) {  // 为空时才创建列表项，提高列表效率
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.item_news_2, parent,false);
            }

            // 2. 查找列表项中的子控件
            ImageView iv01 = (ImageView) convertView.findViewById(R.id.iv_01);
            ImageView iv02 = (ImageView) convertView.findViewById(R.id.iv_02);
            ImageView iv03 = (ImageView) convertView.findViewById(R.id.iv_03);
            TextView tvNewsTitle = (TextView) convertView.findViewById(R.id.tv_news_title);
            TextView tvCommentCount = (TextView) convertView.findViewById(R.id.tv_comment_count);

            // 3. 获取列表项对应的数据（javabean）
            NewsEntity.ResultBean news = (NewsEntity.ResultBean) getItem(position);

            // 4. 显示列表项中的子控件
            tvNewsTitle.setText(news.getTitle());           // 显示标题
            tvCommentCount.setText(news.getReplyCount() + "跟帖"); // 新闻来源
            // 显示3张新闻图片
            Picasso.with(context).load(news.getImgsrc()).into(iv01);
            Picasso.with(context).load(news.getImgextra().get(0).getImgsrc()).into(iv02);
            Picasso.with(context).load(news.getImgextra().get(1).getImgsrc()).into(iv03);
        }

        return convertView;
    }


    // 返回列表项属于哪一种类型的item
    @Override
    public int getItemViewType(int position) {
        // 列表项对应的实体数据
        NewsEntity.ResultBean news = (NewsEntity.ResultBean) getItem(position);
        if (news.getImgextra() != null && news.getImgextra().size() > 0) {
            return ITEM_TYPE_3_IMAGE;       // 显示3张图片的item
        } else {
            return ITEM_TYPE_1_IMAGE;       // 显示1张图片的item
        }
    }

    // 有多少种不同类型的列表项
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public void setDatas(List<NewsEntity.ResultBean> listDatas) {
        this.listDatas = listDatas;
        notifyDataSetChanged();     // 刷新列表
    }

    /** 追加数据，并刷新列表显示 */
    public void appendDatas(List<NewsEntity.ResultBean> listDatas) {
        this.listDatas.addAll(listDatas);
        notifyDataSetChanged();     // 刷新列表
    }
}
