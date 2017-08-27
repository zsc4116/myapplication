package com.example.zhoushicheng.myapplication.SurfaceViewSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.zhoushicheng.myapplication.R;

public class SurfaceViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view);

        final LuckyPlateView plateView = (LuckyPlateView) findViewById(R.id.surfaceview);


        ImageView tvStart = (ImageView) findViewById(R.id.iv_start);
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plateView.luckyStart((int)(Math.random() * 7));
                plateView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        plateView.luckyEnd();
                    }
                }, 3000);
            }
        });

    }
}
