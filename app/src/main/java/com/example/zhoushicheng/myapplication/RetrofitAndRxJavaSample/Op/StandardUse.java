package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op;

import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by zhoushicheng on 2018/1/26.
 */

//标准使用
public class StandardUse implements IRxJavaClick {
    public final String NAME = "标准使用";

    @Override
    public void reaction(final TextView textView) {
        Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello, World!");
                subscriber.onCompleted();
            }
        });
        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                String content = (String) textView.getText();
                textView.setText(content + " ,onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                String content = (String) textView.getText();
                textView.setText(content + " ," + s + " ,onNext");
            }
        };
        myObservable.subscribe(mySubscriber);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
