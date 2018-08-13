package com.example.zhoushicheng.myapplication.MVPSample;

/**
 * Created by zhoushicheng on 2018/5/16.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private final DetailContract.View mDetailView;

    public DetailPresenter(DetailContract.View mDetailView) {
        this.mDetailView = mDetailView;
        mDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        String content = "Deep dark fantsy";
        mDetailView.showData(content);
    }

    @Override
    public void getData() {

    }
}
