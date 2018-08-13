package com.example.zhoushicheng.myapplication.ActivityLifecycleCallbacksSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zhoushicheng.myapplication.R;

public class ActivityLifecycleCallbacksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle_callbacks);

//        ForegroundCallbacks.get(this).addListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ForegroundCallbacks.get(this).removeListener(this);
    }

}
