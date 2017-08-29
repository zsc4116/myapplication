package com.example.zhoushicheng.myapplication.BankViewSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.zhoushicheng.myapplication.R;

public class BankViewActivity extends AppCompatActivity {

    private RelativeLayout rlContent;//动态添加绑卡动画的layout
    private View bankCardView1, bankCardView2, bankCardView3;//绑卡的3个阶段view

    private BankCardEditText etInput;
    private StarView starView;

    private Animation firstAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_view);

        rlContent = (RelativeLayout) findViewById(R.id.addbank_rl_image);

        etInput = (BankCardEditText) findViewById(R.id.addbank_et_input);

        etInput.setListener(new BankCardEditText.BankCardEditTextListner() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                starView.startAnim();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                starView.setText(etInput.getText().toString());
            }
        });

        firstAnim = AnimationUtils.loadAnimation(this, R.anim.firstanim);
        firstAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rlContent.removeView();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
