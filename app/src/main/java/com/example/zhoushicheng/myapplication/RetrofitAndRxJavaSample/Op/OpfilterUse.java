package com.example.zhoushicheng.myapplication.RetrofitAndRxJavaSample.Op;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by zhoushicheng on 2018/1/26.
 */

//filter()的使用
public class OpfilterUse implements IRxJavaClick {
    public final String NAME = "filter()的使用";

    @Override
    public void reaction(final TextView textView) {
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("a1");
        list2.add(null);
        list2.add("a3");
        list2.add("a4");
        Observable.just(list2)
                .flatMap(new Func1<ArrayList<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(ArrayList<String> strings) {
                        return Observable.from(strings);
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s != null;
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
