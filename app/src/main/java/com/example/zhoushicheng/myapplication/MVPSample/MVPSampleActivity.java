package com.example.zhoushicheng.myapplication.MVPSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MVPSampleActivity extends AppCompatActivity implements DetailContract.View {

    private DetailContract.Presenter mDetailPresenter;

    @BindView(R.id.text)
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvpsample);
        ButterKnife.bind(this);
        mDetailPresenter = new DetailPresenter(this);
    }

    @OnClick(R.id.button)
    public void onShowData() {
        mDetailPresenter.start();
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        mDetailPresenter = presenter;
    }


    @Override
    public <String> void showData(String content) {
        textView.setText(content.toString());
    }
}
