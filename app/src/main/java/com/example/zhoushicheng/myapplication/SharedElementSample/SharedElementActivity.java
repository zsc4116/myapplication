package com.example.zhoushicheng.myapplication.SharedElementSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zhoushicheng.myapplication.R;
import com.example.zhoushicheng.myapplication.SharedElementSample.fragment.GridFragment;

public class SharedElementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_element);

        // 替换Fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_cl_container, new GridFragment())
                    .commit();
        }

    }
}
