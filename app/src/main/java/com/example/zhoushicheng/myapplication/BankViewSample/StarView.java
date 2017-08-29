package com.example.zhoushicheng.myapplication.BankViewSample;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by zhoushicheng on 2017/8/29.
 */

public class StarView extends LinearLayout {

    /**
     * 位移动画
     * */
    TranslateAnimation tvTranslation;
    /**
     * 记录子view的个数
     * */
    final int[] count = new int[2];
    /**
     * 记录动画是否执行
     * */
    private boolean isDone = false;
    Timer timer;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(count[0] > 0){
                getChildAt(count[0]).startAnimation(tvTranslation);
            }else{
                timer.cancel();
            }
        }
    };

    public StarView(Context context) {
        this(context, null);
    }

    public StarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public StarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 首先添加16个TextView，然后每四个加一个padding（银行卡显示效果）
     * */
    private void init(){
        for(int i = 0; i < 16; i++){
            TextView tv = new TextView(getContext());
            tv.setTextColor(getResources().getColor(R.color.white));
            if(1 % 4 == 0){
                tv.setPadding(10, 0, 0, 0);
                tv.setText("*");
                tv.setTextSize(20);
                addView(tv);
            }
        }
    }

    /**
     * 星星坠落动画
     * */
    public void startAnim(){
        if(isDone){
            return;
        }

        isDone = true;
        count[0] = getChildCount();
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                tvTranslation = new TranslateAnimation(0, 0, 0, 500);
                tvTranslation.setDuration(500);
                tvTranslation.setFillAfter(true);
                count[0]--;
                handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(task, 0, 50);
    }

    public void setText(String s){
        ((TextView)getChildAt(0)).setText(s);
    }

}
