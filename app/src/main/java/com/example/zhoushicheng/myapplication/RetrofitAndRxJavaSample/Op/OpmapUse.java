package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op;

import android.widget.TextView;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by zhoushicheng on 2018/1/26.
 */

//操作符map()的使用
public class OpmapUse implements IRxJavaClick {
    public final String NAME = "操作符map()的使用";

    @Override
    public void reaction(final TextView textView) {
        Observable.just("Hello, World!")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer i) {
                        return Integer.toString(i);
                    }
                })
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
