package com.example.zhoushicheng.myapplication.ActivityLifecycleCallbacksSample;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zhoushicheng on 2018/4/20.
 */

public class ForegroundCallbacks implements Application.ActivityLifecycleCallbacks {
    public static final long CHECK_DELAY = 500;
    public static final String TAG = ForegroundCallbacks.class.getName();
    private static ForegroundCallbacks INSTANCE;
    private boolean foreground = false, paused = true;
    private Handler handler = new Handler();
    private List<Listener> listeners = new CopyOnWriteArrayList<>();
    private Runnable check;

    public interface Listener {
        void onBecameForeground();

        void onBecameBackground();
    }

    public static ForegroundCallbacks init(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new ForegroundCallbacks();
            application.registerActivityLifecycleCallbacks(INSTANCE);
        }
        return INSTANCE;
    }

    public static ForegroundCallbacks get(Application application) {
        if (INSTANCE == null) {
            init(application);
        }
        return INSTANCE;
    }

    public static ForegroundCallbacks get(Context context) {
        if (INSTANCE == null) {
            Context appContext = context.getApplicationContext();
            if (appContext instanceof Application) {
                init((Application) appContext);
            } else {
                throw new IllegalStateException("Foreground is not initialised and "
                        + "cannot obtain the Application object");
            }
        }
        return INSTANCE;
    }

    public static ForegroundCallbacks get() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Foreground is not initialised - invoke "
                    + "at least once with parameterised init/get");
        }
        return INSTANCE;
    }

    public boolean isForeground() {
        return foreground;
    }

    public boolean isBackground() {
        return !foreground;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        paused = false;
        boolean wasBackground = !foreground;
        foreground = true;
        if (check != null) {
            handler.removeCallbacks(check);
        }
        if (wasBackground) {
            Log.e("aaa", "ForegroundCallbacks went foreground");
            for (Listener l : listeners) {
                l.onBecameForeground();
            }
        } else {
            Log.e("aaa", "ForegroundCallbacks still foreground");
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        paused = true;
        if (check != null) {
            handler.removeCallbacks(check);
        }
        handler.post(check = new Runnable() {
            @Override
            public void run() {
                if (foreground && paused) {
                    foreground = false;
                    Log.e("aaa", "ForegroundCallbacks went background");
                    for (Listener l : listeners) {
                        l.onBecameBackground();
                    }
                } else {
                    Log.e("aaa", "ForegroundCallbacks still background");
                }
            }
        });
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.e("aaa", "ForegroundCallbacks went Destroyed");
    }
}
