package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op;

import android.widget.TextView;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by zhoushicheng on 2018/1/26.
 */

//简洁使用
public class SimpleUse implements IRxJavaClick {
    public final String NAME = "简洁使用";

    @Override
    public void reaction(final TextView textView) {
        Observable.just("Hello, World!")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        textView.setText(s);
                    }
                });
    }

    @Override
    public String getName() {
        return NAME;
    }
}
