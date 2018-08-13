package com.example.zhoushicheng.myapplication.ViewPagerFragmentColSample;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.example.zhoushicheng.myapplication.R;

public class ViewPagerFragmentCoordinatorLayoutActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_fragment_coordinator_layout);

        viewPager = (ViewPager) findViewById(R.id.vp_content);
        tabLayout = (TabLayout) findViewById(R.id.tb_content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        button = (Button) findViewById(R.id.button);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);
        // TabLayout指示器添加文本
        tabLayout.getTabAt(0).setText("头条");
        tabLayout.getTabAt(1).setText("热点");
        tabLayout.getTabAt(2).setText("时事");


        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.e("zzz", "onGlobalLayout: ViewPager width = " + viewPager.getWidth());
            }
        });

        viewPager.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e("zzz", "scrollX = " + scrollX);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.fakeDragBy(300);
            }
        });
    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return TestFragment.newInstance();
            } else if (position == 1) {
                return TestFragment.newInstance();
            } else {
                return TestFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    class Adapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
}
