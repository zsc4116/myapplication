package com.example.zhoushicheng.myapplication.MVPSample;

/**
 * Created by zhoushicheng on 2018/5/16.
 */

public interface DetailContract {
    interface View extends BaseView<Presenter> {
        <T> void showData(T content);
    }


    interface Presenter extends BasePresenter {
        void getData();
    }
}
