package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op;

import android.util.Log;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zhoushicheng on 2018/1/26.
 */

//doOnSubscribe()的使用
public class OpdoOnSubscribeUse implements IRxJavaClick {
    public final String NAME = "doOnSubscribe()的使用";

    @Override
    public void reaction(final TextView textView) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.e("zzz", "on create, Thread = " + Thread.currentThread().getName());
            }
        })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("zzz", "on doOnSubscribe, Thread = " + Thread.currentThread().getName());
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("zzz", "on subscribe, Thread = " + Thread.currentThread().getName());
                    }
                });
    }

    @Override
    public String getName() {
        return NAME;
    }
}
