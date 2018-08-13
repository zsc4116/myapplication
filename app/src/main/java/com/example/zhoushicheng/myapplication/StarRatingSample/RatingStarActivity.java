package com.example.zhoushicheng.myapplication.StarRatingSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zhoushicheng.myapplication.R;

public class RatingStarActivity extends AppCompatActivity {

    RatingStarView starView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_star);

        starView = (RatingStarView) findViewById(R.id.star);
        starView.setMark(3.0f);
    }
}
