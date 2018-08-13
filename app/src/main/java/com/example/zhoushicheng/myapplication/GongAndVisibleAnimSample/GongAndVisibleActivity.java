package com.example.zhoushicheng.myapplication.GongAndVisibleAnimSample;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

public class GongAndVisibleActivity extends AppCompatActivity {

    //TODO:属性动画的示例并不成功

    Button button;
    TextView textView;
    private int mVisiable = View.GONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gong_and_visible);

        button = (Button) findViewById(R.id.btn_anim);
        textView = (TextView) findViewById(R.id.tv_content);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisiable();
                if (mVisiable == View.GONE) {
                    setOutAnim();
                    setContainerVisiable();
                } else {
                    setContainerVisiable();
                    setInAnim();
                }
            }
        });
    }

    private void setVisiable() {
        mVisiable = mVisiable == View.GONE ? View.VISIBLE : View.GONE;
    }

    private void setContainerVisiable() {
        textView.setVisibility(mVisiable);
    }

    private void setOutAnim() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.top_out);
        animator.setTarget(textView);
        animator.start();
    }

    private void setInAnim() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.top_in);
        animator.setTarget(textView);
        animator.start();
    }

}
