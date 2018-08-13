package com.example.zhoushicheng.myapplication.RedSpotSample.redSpotUtil;

import android.app.Activity;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhoushicheng on 2018/4/17.
 */

public final class RedSpotClient {

    private final int OP_SHOW = 1;
    private final int OP_HIDDEN = 2;

    public static int count = 0;

    private HashMap<String, ImageView> mViews = new HashMap<>();
    private HashMap<Object, ArrayList<String>> mContainer = new HashMap<>();
    private HashMap<String, Object> mReflect = new HashMap<>();


    public RedSpotClient() {

    }

    private static final class RedSpotClientHolder {
        private static RedSpotClient INSTANCE = new RedSpotClient();
    }

    public static final RedSpotClient getInstance() {
        return RedSpotClientHolder.INSTANCE;
    }

    public final void regiser(Object container) {
        if (!mContainer.containsKey(container)) {
            mContainer.put(container, new ArrayList<String>());
        }
    }

    public final void add(Object container, @NonNull String key,@NonNull ImageView value) {
        if (!mContainer.containsKey(container)) {
            throw new RuntimeException("You have not register this container : " + container.getClass().getSimpleName());
        }

        if (!mViews.containsKey(value)) {
            if (!mViews.containsKey(key)) {
                mViews.put(key, value);
                mContainer.get(container).add(key);
                mReflect.put(key, container);
            }
        }
    }

    public final void unregister(Object container) {
        if (!mContainer.containsKey(container)) {
            return;
        }

        ArrayList<String> keys = mContainer.get(container);
        if (keys != null && keys.size() != 0) {
            for (String key : keys) {
                mReflect.remove(key);
                mViews.remove(key);
            }
        }
        mContainer.remove(container);
    }


    public final void show(String key) {
        operate(key, OP_SHOW);
    }

    public final void hidden(String key) {
        operate(key, OP_HIDDEN);
    }

    private final void operate(String key, int op) {
        checkClientContainKey(key);

        boolean b = false;

        Object container = mReflect.get(key);

        if (container instanceof Fragment) {
            Fragment f = (Fragment) container;
            if (!f.isDetached()) {
                b = checkObjectContainKey(key);
            }
        } else if (container instanceof Activity) {
            Activity a = (Activity) container;
            if (!a.isDestroyed()) {
                b = checkObjectContainKey(key);
            }
        }

        if (b) {
            if (op == OP_HIDDEN) {
                mViews.get(key).setVisibility(View.GONE);
            } else if (op == OP_SHOW) {
                mViews.get(key).setVisibility(View.VISIBLE);
            }
        }
    }

    private final void checkClientContainKey(String key) {
        if (!mViews.containsKey(key)) {
            throw new RuntimeException("Do not register key : " + key);
        }

        if (!mReflect.containsKey(key)) {
            throw new RuntimeException("Do not register key : " + key);
        }

        Object container = mReflect.get(key);
        if (container == null) {
            throw new RuntimeException("Do not have container : " + container.getClass().getSimpleName());
        }

    }

    private final boolean checkObjectContainKey(String key) {
        for (Map.Entry<Object, ArrayList<String>> entries : mContainer.entrySet()) {
            ArrayList<String> keys = entries.getValue();
            if (keys != null && keys.size() != 0) {
                int size = keys.size();
                for (int i = 0; i < size; i++) {
                    Log.e("key", keys.get(i));
                    if (keys.get(i).equals(key)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
