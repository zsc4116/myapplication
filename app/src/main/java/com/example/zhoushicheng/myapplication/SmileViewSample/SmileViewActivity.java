package com.example.zhoushicheng.myapplication.SmileViewSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.example.zhoushicheng.myapplication.R;

public class SmileViewActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    private ImageView smileface;
    private SeekBar seekBar;
    private SmileView smileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smile_view);
        smileface = (ImageView) findViewById(R.id.smileface);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);

        smileView = (SmileView) findViewById(R.id.smileView);
        smileView.setNum(60, 40);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)smileface.getLayoutParams();
        lp.bottomMargin = i * 3;
        smileface.setLayoutParams(lp);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
