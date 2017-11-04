package com.example.zhoushicheng.myapplication.ViewDragHelperSample;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

public class DrawerLayoutActivity extends AppCompatActivity {

    private LeftDrawerLayout mLeftDrawerLayout;
    private LeftMenuFragment mLeftMenuFragment;
    private TextView mContentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);

        mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.dl_drawer);
        mContentTv = (TextView) findViewById(R.id.tv_content);

        FragmentManager fm = getSupportFragmentManager();
        mLeftMenuFragment = (LeftMenuFragment) fm.findFragmentById(R.id.fl_container_menu);
        if (mLeftMenuFragment == null) {
            fm.beginTransaction().add(R.id.fl_container_menu, mLeftMenuFragment = new LeftMenuFragment()).commit();
        }

        mLeftMenuFragment.setMenuItemSelectedListener(new LeftMenuFragment.OnMenuItemSelectedListener() {
            @Override
            public void menuItemSelected(String title) {
                mLeftDrawerLayout.closeDrawer();
                mContentTv.setText(title);
            }
        });
    }
}
