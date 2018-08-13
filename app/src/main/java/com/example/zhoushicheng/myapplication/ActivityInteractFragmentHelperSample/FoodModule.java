package com.example.zhoushicheng.myapplication.ActivityInteractFragmentHelperSample;

/**
 * Created by zhoushicheng on 2018/6/22.
 */

public class FoodModule {
    private String name;
    private int count;
    private boolean isChosen;

    public FoodModule(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    @Override
    public boolean equals(Object obj) {
        if (name.equals(((FoodModule) obj).getName())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "FoodModule{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
