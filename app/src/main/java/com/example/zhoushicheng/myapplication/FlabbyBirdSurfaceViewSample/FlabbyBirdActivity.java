package com.example.zhoushicheng.myapplication.FlabbyBirdSurfaceViewSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class FlabbyBirdActivity extends AppCompatActivity {

    private FlabbyBirdSurfaceView mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mGame = new FlabbyBirdSurfaceView(this);
        setContentView(mGame);
    }
}
