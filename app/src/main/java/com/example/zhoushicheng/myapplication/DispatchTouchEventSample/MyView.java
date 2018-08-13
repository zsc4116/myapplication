package com.example.zhoushicheng.myapplication.DispatchTouchEventSample;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ZSC on 2018/7/28.
 */

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("event", "view dispatchTouchEvent");

        boolean dispatch = super.dispatchTouchEvent(event);

        Log.i("event", "view dispatchTouchEvent, getDispatch = " + dispatch);

        return dispatch;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("event", "view onTouchEvent");

        boolean touch = super.onTouchEvent(event);
        //                boolean touch = true;

        Log.i("event", "view onTouchEvent, getTouch = " + touch);

        return touch;
    }
}
