package com.example.zhoushicheng.myapplication.ActivityInteractFragmentHelperSample;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhoushicheng on 2018/6/22.
 */

public class InteractionHelper {

    private HashMap<Activity, List<FoodModule>> mFoodsMap = new HashMap<>();
    private HashMap<Activity, List<OnHelperDataChangeListener>> mHashMap = new HashMap<>();

    public static InteractionHelper INSTANCE;

    public void register(OnHelperDataChangeListener listener) {
        Activity activity = preprocessListenerClass(listener);

        if (mHashMap.containsKey(activity)) {
            List<OnHelperDataChangeListener> list = mHashMap.get(activity);
            list.add(listener);
        } else {
            List<OnHelperDataChangeListener> list = new ArrayList<>();
            list.add(listener);
            mHashMap.put(activity, list);
        }
    }

    public void unRegister(OnHelperDataChangeListener listener) {
        Activity activity = preprocessListenerClass(listener);

        if (mHashMap.containsKey(activity)) {
            List<OnHelperDataChangeListener> list = mHashMap.get(activity);
            list.remove(listener);
            if (list.size() == 0) {
                mHashMap.remove(activity);
            }
        }
    }

    private Activity preprocessListenerClass(OnHelperDataChangeListener listener) {
        Activity activity;
        if (listener instanceof Fragment) {
            activity = ((Fragment) listener).getActivity();
        } else {
            activity = (Activity) listener;
        }
        return activity;
    }

    public static final InteractionHelper get() {
        if (INSTANCE == null) {
            INSTANCE = new InteractionHelper();
        }
        return INSTANCE;
    }

    public void updateFoodInCart(FoodModule food, OnHelperDataChangeListener listener) {
        List<FoodModule> foods = checkHasFoodList(listener);
        BroadcastUpdate(foods, food, listener);
    }

    private List<FoodModule> checkHasFoodList(OnHelperDataChangeListener listener) {
        Activity activity = preprocessListenerClass(listener);
        List<FoodModule> list = mFoodsMap.get(activity);
        if (list == null) {
            list = new ArrayList<>();
            mFoodsMap.put(activity, list);
        }
        return list;
    }

    private void BroadcastUpdate(List<FoodModule> foods, FoodModule food, OnHelperDataChangeListener listener) {
        if (foods.contains(food)) {
            int index = foods.indexOf(food);
            foods.set(index, food);
        } else {
            foods.add(food);
        }

        Activity activity = preprocessListenerClass(listener);
        List<OnHelperDataChangeListener> list = mHashMap.get(activity);
        if (list != null && list.size() > 0) {
            for (OnHelperDataChangeListener dataChangeListener : list) {
                if (dataChangeListener != listener) {
                    dataChangeListener.onDataChange(foods);
                }
            }
        }
    }

    public interface OnHelperDataChangeListener {
        void onDataChange(List<FoodModule> foods);
    }
}
