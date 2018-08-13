package com.example.zhoushicheng.myapplication.DispatchTouchEventSample;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by ZSC on 2018/7/28.
 */

public class MyViewGroupB extends LinearLayoutCompat {
    public MyViewGroupB(Context context) {
        super(context);
    }

    public MyViewGroupB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroupB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("event", "MyViewGroupB onInterceptTouchEvent");

        boolean intercept = super.onInterceptTouchEvent(ev);

        Log.i("event", "MyViewGroupB onInterceptTouchEvent getIntercept = " + intercept);
        return intercept;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("event", "MyViewGroupB dispatchTouchEvent");

        boolean dispatch = super.dispatchTouchEvent(ev);

        Log.i("event", "MyViewGroupB dispatchTouchEvent, getDispatch = " + dispatch);
        return dispatch;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("event", "MyViewGroupB onTouchEvent");

//        boolean touch = super.onTouchEvent(event);
                boolean touch = true;


        Log.i("event", "MyViewGroupB onTouchEvent, getTouch = " + touch);
        return touch;
    }
}
