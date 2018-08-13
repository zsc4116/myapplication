package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op;

import android.util.Log;
import android.widget.TextView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zhoushicheng on 2018/1/26.
 */

//subscribeOn()与observeOn()调度线程的使用之一
public class OpsubscribeOnobserveOnUse implements IRxJavaClick {
    public final String NAME = "subscribeOn()与observeOn()调度线程的使用之一";

    @Override
    public void reaction(final TextView textView) {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())//指定subscribe()发生在io线程
                .observeOn(AndroidSchedulers.mainThread())//指定Subscriber的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.e("zzz", "integer = " + integer);
                    }
                });
    }

    @Override
    public String getName() {
        return NAME;
    }
}
