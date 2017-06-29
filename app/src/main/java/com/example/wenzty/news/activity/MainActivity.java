package com.example.wenzty.news.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import com.example.wenzty.news.R;
import com.example.wenzty.news.fragment.MainFragment01;
import com.example.wenzty.news.fragment.MainFragment02;
import com.example.wenzty.news.fragment.MainFragment03;
import com.example.wenzty.news.fragment.MainFragment04;
import com.example.wenzty.news.fragment.MainFragment05;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private RadioGroup mRadioGroup;
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolBar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;



    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }
    @Override
    protected void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initViewPager();
        initNavigationView();
        initToolbar();
        initDrawerLayout();
    }

    private void initDrawerLayout() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();     // 同步drawerLayout和toolbar的状态

    }

    private void initToolbar() {
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.drawable.biz_video_play);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_option,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_01){
            showToast("item_01");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initNavigationView() {
        NavigationView  navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_01){
                    showToast("item_01");
                    mDrawerLayout.closeDrawers();
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    protected void initListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rd_01:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.rd_02:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.rd_03:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.rd_04:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.rd_05:
                        mViewPager.setCurrentItem(4);
                        break;
                }
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    switch (position){
                        case 0:
                            mRadioGroup.check(R.id.rd_01);
                            break;
                        case 1:
                            mRadioGroup.check(R.id.rd_02);
                            break;
                        case 2:
                            mRadioGroup.check(R.id.rd_03);
                            break;
                        case 3:
                            mRadioGroup.check(R.id.rd_04);
                            break;
                        case 4:
                            mRadioGroup.check(R.id.rd_05);
                            break;
                    }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void initDate() {

    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MainFragment01());
        fragments.add(new MainFragment02());
        fragments.add(new MainFragment03());
        fragments.add(new MainFragment04());
        fragments.add(new MainFragment05());

        mViewPager.setAdapter(new FragmentPagerAdapter(
                getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

    }
}
