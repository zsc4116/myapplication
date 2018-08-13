package com.example.zhoushicheng.myapplication.DispatchTouchEventSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.example.zhoushicheng.myapplication.R;

public class DispatchTouchEventSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_touch_event_sample);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("event", "DispatchTouchEventSampleActivity onTouchEvent");

//        boolean touch = super.onTouchEvent(event);
                boolean touch = true;

        Log.i("event", "DispatchTouchEventSampleActivity onTouchEvent, getTouch = " + touch);
        return touch;
    }


}
