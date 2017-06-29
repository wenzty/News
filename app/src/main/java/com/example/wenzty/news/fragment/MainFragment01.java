package com.example.wenzty.news.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.wenzty.news.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenzty on 2017/6/27.
 */

public class MainFragment01 extends BaseFragment{
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_01;
    }

    @Override
    public void initView() {
        mTabLayout = (TabLayout) mRootView.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.view_pager);
        initViewPager();
    }

    private void initViewPager() {
        final String[] titles = new String[] {
                "头条", "社会", "科技", "财经", "体育", "汽车"
        };
        final String[] channelIds = new String[] {
                "T1348647909107",
                "T1348648037603",
                "T1348649580692",
                "T1348648756099",
                "T1348649079062",
                "T1348654060988",
        };
        final List<Fragment> fragments = new ArrayList<>();
        for (int i=0; i < titles.length; i++){
            NewItemFragment fragment = new NewItemFragment();
            fragment.setNewsCategoryId(channelIds[i]);
            fragments.add(fragment);
        }
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
            @Override
            public int getCount() {
                return titles.length;
            }
            @Override
            public CharSequence getPageTitle(int position){
                return titles[position];
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initDate() {
    }

    @Override
    public void initListener() {
    }
}
