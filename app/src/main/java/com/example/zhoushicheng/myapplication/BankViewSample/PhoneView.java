package com.example.zhoushicheng.myapplication.BankViewSample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;
import com.example.zhoushicheng.myapplication.Utils.Util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhoushicheng on 2017/8/29.
 */

@SuppressLint("ViewConstructor")
public class PhoneView extends LinearLayout {

    private int index = 0;
    private String[] texts = {"您", "的", "手", "机", "号", "码"};
    TranslateAnimation tvTranslation;
    Timer timer;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (index < texts.length) {
                getChildAt(index++).startAnimation(tvTranslation);
            } else {
                timer.cancel();
            }
        }
    };

    public PhoneView(Context context) {
        this(context, null);
    }

    public PhoneView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhoneView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, Util.dp2px(getContext(), 100));
        for (int i = 0; i < texts.length; i++) {
            TextView tv = new TextView(getContext());
            tv.setTextColor(getResources().getColor(R.color.phoneTextBlue));
            tv.setText(texts[i]);
            tv.setTextSize(16);
            tv.setLayoutParams(params);
            tv.setGravity(Gravity.BOTTOM);
            tv.setPadding(0, 0, 0, Util.dp2px(getContext(), 30));
            addView(tv);
        }
    }

    public void starAnim() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                tvTranslation = new TranslateAnimation(0, 0, 0, -100);
                tvTranslation.setDuration(300);
                tvTranslation.setFillAfter(true);
                handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(task, 0, 50);
    }
}
