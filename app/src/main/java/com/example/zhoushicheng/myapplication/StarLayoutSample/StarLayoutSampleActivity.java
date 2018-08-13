package com.example.zhoushicheng.myapplication.StarLayoutSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.zhoushicheng.myapplication.R;

public class StarLayoutSampleActivity extends AppCompatActivity implements StarLayout.StarSelectListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_layout_sample);

        StarLayout sl = (StarLayout) findViewById(R.id.sl_content);
        sl.setStarSelectListener(this);


    }


    @Override
    public void onStarChanged(int count) {
        Log.e("count", String.valueOf(count));
    }
}
