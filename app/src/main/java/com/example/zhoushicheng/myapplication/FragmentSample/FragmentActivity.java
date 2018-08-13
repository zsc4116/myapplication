package com.example.zhoushicheng.myapplication.FragmentSample;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.zhoushicheng.myapplication.R;

public class FragmentActivity extends AppCompatActivity {
    private TabLayout mTab;
    private Toolbar mToolbar;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTab = (TabLayout) findViewById(R.id.tab);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mToolbar.setTitle("discover");

        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());

        mViewPager.setAdapter(new PagerFragmentAdapter(getSupportFragmentManager()
                , getString(R.string.all), getString(R.string.more)));
        mTab.setupWithViewPager(mViewPager);

        mViewPager.setCurrentItem(0);

    }
}
