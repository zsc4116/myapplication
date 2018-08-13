package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op;

import android.util.Log;
import android.widget.TextView;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by zhoushicheng on 2018/1/26.
 */

//doOnNext()的使用
public class OpdoOnNextUse implements IRxJavaClick {
    public final String NAME = "oOnNext()的使用";

    @Override
    public void reaction(final TextView textView) {
        Observable.just("abc")
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("zzz", s + " def");
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("zzz", s);
                    }
                });
    }

    @Override
    public String getName() {
        return NAME;
    }
}
