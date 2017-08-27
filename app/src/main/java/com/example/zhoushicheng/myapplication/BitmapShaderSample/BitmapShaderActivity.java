package com.example.zhoushicheng.myapplication.BitmapShaderSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zhoushicheng.myapplication.R;

public class BitmapShaderActivity extends AppCompatActivity {

    private RoundImageView mQiqiu;
    private RoundImageView mMeinv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_shader);

        mQiqiu = (RoundImageView) findViewById(R.id.id_qiqiu);
        mMeinv = (RoundImageView) findViewById(R.id.id_meinv);

        mQiqiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQiqiu.setType(RoundImageView.TYPE_ROUND);
            }
        });

        mMeinv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMeinv.setBorderRadius(90);
            }
        });
    }
}
