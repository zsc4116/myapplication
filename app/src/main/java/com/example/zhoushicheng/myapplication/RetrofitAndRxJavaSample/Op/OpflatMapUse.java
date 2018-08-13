package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by zhoushicheng on 2018/1/26.
 * <p>
 * flatMap() 的原理是这样的：
 * 1. 使用传入的事件对象创建一个 Observable 对象；
 * 2. 并不发送这个 Observable, 而是将它激活，于是它开始发送事件；
 * 3. 每一个创建出来的 Observable 发送的事件，都被汇入同一个 Observable ，而这个 Observable 负责将这些事件统一交给 Subscriber 的回调方法。
 */

//flatMap()的使用
public class OpflatMapUse implements IRxJavaClick {
    public final String NAME = "flatMap()的使用";

    @Override
    public void reaction(final TextView textView) {
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("a1");
        list1.add("a2");
        list1.add("a3");
        list1.add("a4");
        Observable.just(list1)
                //可以这样说：map()是将数据一对一转换的关系，而flatMap()则是将原数据转换成Obserable的关系
                .flatMap(new Func1<ArrayList<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(ArrayList<String> strings) {
                        return Observable.from(strings);
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
