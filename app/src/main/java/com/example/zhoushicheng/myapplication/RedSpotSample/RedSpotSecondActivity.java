package com.example.zhoushicheng.myapplication.RedSpotSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.zhoushicheng.myapplication.R;
import com.example.zhoushicheng.myapplication.RedSpotSample.redSpotUtil.RedSpotClient;

public class RedSpotSecondActivity extends AppCompatActivity {

    private Button btnControlSpot = null;

    private static final String RED_SPOT = "red_spot";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_spot_second);

        btnControlSpot = (Button) findViewById(R.id.btn_control_red_spot);

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
    }

}
