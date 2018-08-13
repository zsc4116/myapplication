package com.example.zhoushicheng.myapplication.AutoPhotoLayoutSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zhoushicheng.myapplication.R;

public class AutoPhotoLayoutSampleActivity extends AppCompatActivity {

    AutoPhotoLayout AplContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_photo_layout_sample);
        AplContent = (AutoPhotoLayout) findViewById(R.id.apl_content);



    }
}
