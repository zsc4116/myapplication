package com.example.zhoushicheng.myapplication.RedSpotSample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zhoushicheng.myapplication.R;
import com.example.zhoushicheng.myapplication.RedSpotSample.redSpotUtil.RedSpotClient;

public class RedSpotActivity extends AppCompatActivity {

    private ImageView imgRedSpot = null;
    private Button btnControlSpot = null;
    private Button btnGotoNext = null;

    private static final String RED_SPOT = "red_spot";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_spot);

        RedSpotClient.getInstance().regiser(this);

        imgRedSpot = (ImageView) findViewById(R.id.iv_red_spot);
        btnControlSpot = (Button) findViewById(R.id.btn_control_red_spot);
        btnGotoNext = (Button) findViewById(R.id.btn_go_to_next);

        RedSpotClient.getInstance().add(this, RED_SPOT, imgRedSpot);

        btnControlSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RedSpotClient.count % 2 == 0) {
                    RedSpotClient.getInstance().show(RED_SPOT);
                } else {
                    RedSpotClient.getInstance().hidden(RED_SPOT);
                }
                RedSpotClient.count++;
            }
        });

        btnGotoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RedSpotActivity.this, RedSpotSecondActivity.class));
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RedSpotClient.getInstance().unregister(this);
    }
}
