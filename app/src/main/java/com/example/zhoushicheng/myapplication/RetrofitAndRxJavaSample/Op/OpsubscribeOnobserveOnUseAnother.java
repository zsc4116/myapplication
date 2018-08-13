package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op;

import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhoushicheng on 2018/1/26.
 */

//subscribeOn()与observeOn()调度线程的使用之二

//注意：subscribeOn () 与 observeOn()都会返回了一个新的Observable，因此若不是采用上面这种直接流方式，而是分步调用方式，需要将新返回的Observable赋给原来的Observable，否则线程调度将不会起作用。
//注意：observeOn() 指定的是它之后的操作所在的线程。因此如果有多次切换线程的需求，只要在每个想要切换线程的位置调用一次。
//注意：不同于 observeOn() ， subscribeOn() 的位置放在哪里都可以，但它是只能调用一次的。
public class OpsubscribeOnobserveOnUseAnother implements IRxJavaClick {
    public final String NAME = "subscribeOn()与observeOn()调度线程的使用之二";

    @Override
    public void reaction(final TextView textView) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("info1");

                SystemClock.sleep(2000);
                subscriber.onNext("info2-sleep 2s");

                SystemClock.sleep(3000);
                subscriber.onNext("info2-sleep 3s");

                SystemClock.sleep(5000);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io()) //指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) //指定 Subscriber 的回调发生在主线程
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.v("zzz", "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("zzz", "onError() e=" + e);
                    }

                    @Override
                    public void onNext(String s) {
                        textView.setText(s); //UI view显示数据
                    }
                });
    }

    @Override
    public String getName() {
        return NAME;
    }
}
