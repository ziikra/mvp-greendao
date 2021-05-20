package com.example.arsitektur_mvp_and_greendao.ui.crud;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.arsitektur_mvp_and_greendao.R;
import com.example.arsitektur_mvp_and_greendao.ui.base.BaseActivity;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

public class CRUDActivity extends BaseActivity {

    @Inject
    CRUDPagerAdapter mPagerAdapter;

    Toolbar mToolBar;

    ViewPager mViewPager;

    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        this.mToolBar = findViewById(R.id.toolbar);
        this.mViewPager = findViewById(R.id.crudViewPager);
        this.mTabLayout = findViewById(R.id.tabLayout);

        getActivityComponent().inject(this);
        setUp();
    }

    @Override
    protected void setUp() {
        mPagerAdapter.setCount(4);

        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout.addTab(mTabLayout.newTab().setText("INSERT"));
        mTabLayout.addTab(mTabLayout.newTab().setText("SELECT"));
        mTabLayout.addTab(mTabLayout.newTab().setText("UPDATE"));
        mTabLayout.addTab(mTabLayout.newTab().setText("DELETE"));

        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}