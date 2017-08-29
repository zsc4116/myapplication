package com.example.zhoushicheng.myapplication.BankViewSample;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

public class BankViewActivity extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout rlContent;//动态添加绑卡动画的layout
    private View bankCardView1, bankCardView2, bankCardView3;//绑卡的3个阶段view
    private BankCardEditText etInput;
    private StarView starView;
    private TextView next;//下一步按钮
    private int step = 0;

    private LinearLayout phoneLayout;
    private PhoneView phoneView;
    private TextView tv1, tv2, tv3;//3个步骤指示
    private ImageView ivPhone;

    private Animation firstAnim;
    private Animation secondAnim;
    private Animator thirdAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_view);
        initView();
    }

    private void initView() {

        tv1 = (TextView) findViewById(R.id.addbank_text1);
        tv2 = (TextView) findViewById(R.id.addbank_text2);
        tv3 = (TextView) findViewById(R.id.addbank_text3);
        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(this);

        etInput = (BankCardEditText) findViewById(R.id.addbank_et_input);
        rlContent = (RelativeLayout) findViewById(R.id.addbank_rl_image);

        bankCardView1 = LayoutInflater.from(this).inflate(R.layout.bankcardview1, null);
        bankCardView2 = LayoutInflater.from(this).inflate(R.layout.bankcardview2, null);
        phoneView = new PhoneView(this);
        starView = (StarView) bankCardView1.findViewById(R.id.starview);
        phoneLayout = (LinearLayout) bankCardView2.findViewById(R.id.ll_tvphone);
        ivPhone = (ImageView) bankCardView2.findViewById(R.id.image_phone);
        final ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bankCardView1.setLayoutParams(param);
        bankCardView2.setLayoutParams(param);
        rlContent.addView(bankCardView1);


        etInput.setListener(new BankCardEditText.BankCardEditTextListner() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                starView.setText(s);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                starView.startAnim();
            }
        });

        firstAnim = AnimationUtils.loadAnimation(this, R.anim.firstanim);
        firstAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rlContent.removeView(bankCardView1);
                rlContent.addView(bankCardView2);
                rlContent.startAnimation(secondAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        secondAnim = AnimationUtils.loadAnimation(this, R.anim.secondanim);
        secondAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                phoneLayout.addView(phoneView);
                phoneView.starAnim();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        thirdAnim = AnimatorInflater.loadAnimator(this, R.animator.thirdanim);
        thirdAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                phoneLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                View viewCode = BankViewActivity.this.getLayoutInflater().inflate(R.layout.view_addbank_code, null);

                final ImageView ivMessage = (ImageView) viewCode.findViewById(R.id.addbank_code_image);
                final TextView tvMess1 = (TextView) viewCode.findViewById(R.id.addbank_code_tv1);
                final TextView tvMess2 = (TextView) viewCode.findViewById(R.id.addbank_code_tv2);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);

                params.width = (int) (ivPhone.getWidth() - ivPhone.getWidth() * 0.116);
                params.height = (int) (ivPhone.getHeight() * 0.3);
                params.leftMargin = (int) (ivPhone.getLeft() + ivPhone.getWidth() * 0.058);
                params.topMargin = (int) (ivPhone.getTop() + ivPhone.getHeight() * 0.251);
                rlContent.addView(viewCode, params);

                Animation ivAnim = AnimationUtils.loadAnimation(BankViewActivity.this, R.anim.scaleanim);
                final Animation tvAnim1 = AnimationUtils.loadAnimation(BankViewActivity.this, R.anim.scaleanim);
                final Animation tvAnim2 = AnimationUtils.loadAnimation(BankViewActivity.this, R.anim.scaleanim);

                ivMessage.startAnimation(ivAnim);
                ivAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ivMessage.setVisibility(View.VISIBLE);
                        tvMess1.startAnimation(tvAnim1);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                tvAnim1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        tvMess1.setVisibility(View.VISIBLE);
                        tvMess2.startAnimation(tvAnim2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                tvAnim2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        tvMess2.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next:
                if(step == 0){
                    rlContent.startAnimation(firstAnim);
                    tv1.setTextColor(getResources().getColor(R.color.colorGray));
                    tv2.setTextColor(getResources().getColor(R.color.colorBule2));
                    tv3.setTextColor(getResources().getColor(R.color.colorGray));
                    etInput.setBankCardType(1);
                    etInput.setText("");
                    etInput.setHint("请输入手机号");
                    step = 1;
                } else if (step == 1) {
                    tv1.setTextColor(getResources().getColor(R.color.colorGray));
                    tv2.setTextColor(getResources().getColor(R.color.colorGray));
                    tv3.setTextColor(getResources().getColor(R.color.colorBule2));
                    etInput.setBankCardType(-1);
                    etInput.setText("");
                    etInput.setHint("请输入验证码");
                    thirdAnim.setTarget(rlContent);
                    thirdAnim.start();
                }
                break;
        }
    }
}
