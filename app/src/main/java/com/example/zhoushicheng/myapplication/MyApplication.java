package com.example.zhoushicheng.myapplication;

import android.app.Application;
import android.util.Log;

import com.example.zhoushicheng.myapplication.ActivityLifecycleCallbacksSample.ForegroundCallbacks;

import me.yokeyword.fragmentation.Fragmentation;

/**
 * Created by zhoushicheng on 2018/4/20.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ForegroundCallbacks.init(this);
        ForegroundCallbacks.get().addListener(new ForegroundCallbacks.Listener() {
            @Override
            public void onBecameForeground() {
                Log.e("aaa", "ActivityLifecycleCallbacksActivity Became Foreground");
            }

            @Override
            public void onBecameBackground() {
                Log.e("aaa", "ActivityLifecycleCallbacksActivity Became Background");
            }
        });

        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .install();

    }
}
