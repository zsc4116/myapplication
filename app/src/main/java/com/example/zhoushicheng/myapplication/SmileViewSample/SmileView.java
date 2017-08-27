package com.example.zhoushicheng.myapplication.SmileViewSample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

/**
 * Created by zhoushicheng on 2017/8/26.
 */

public class SmileView extends LinearLayout implements Animator.AnimatorListener{

    private ImageView imageLike;
    private ImageView imageDisLike;
    private TextView likeNum, disNum, likeText, disText;
    private LinearLayout likeBack, disBack, likeAll, disAll;

    private float count;
    private float fLike, fDis;

    private int type = 0;
    private boolean isClose = false;
    private int like = 10;
    private int disLike = 20;
    private int dividerMargin = 20;
    private int defaultBottom = 70;
    private String defaultLike = "喜欢";
    private String defaultDis = "无感";
    private int defaultTextColor = 0xffffffff;
    private String defaultShadow = "#7F484848";
    private int defaultGravity = Gravity.CENTER_HORIZONTAL;
    private int defaultSize = dip2px(getContext(), 25);

    private AnimationDrawable animLike;
    private AnimationDrawable animDisLike;
    private ValueAnimator animatorBack;

    public SmileView setDefaultBottom(int defaultBottom){
        this.defaultBottom = defaultBottom;
        return this;
    }

    public void notifyChange(){
        init();
        bindListener();
    }

    public void setDefaultGravity(int defaultGravity){
        this.defaultGravity = defaultGravity;
    }

    public void setDefaultDis(String defaultDis){
        this.defaultDis = defaultDis;
    }

    public void setDefaultLike(String defaultLike){
        this.defaultLike = defaultLike;
    }

    public SmileView setDividerMargin(int dividerMargin){
        this.dividerMargin = dividerMargin;
        return this;
    }

    public void setDefaultSize(int defaultSize){
        this.defaultSize = defaultSize;
    }

    public void setNum(int like, int disLike){
        count = like + disLike;
        fLike = like / count;
        fDis = disLike / count;
        this.like = (int)(fLike * 100);
        this.disLike = 100 - this.like;
        setLike(this.like);
        setDisLike(this.disLike);
    }

    public void setLike(int like){
        likeNum.setText(like + "%");
    }

    public void setDisLike(int disLike){
        disNum.setText(disLike + "%");
    }


    public SmileView(Context context) {
        this(context, null);
    }

    public SmileView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmileView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        bindListener();
    }

    private void init(){
        this.removeAllViews();
        setOrientation(HORIZONTAL);
        setGravity(defaultGravity | Gravity.BOTTOM);
        setBackgroundColor(Color.TRANSPARENT);

        float count = like + disLike;
        fLike = like / count;
        fDis = disLike / count;
        like = (int) (fLike * 100);
        disLike = (int) (fDis * 100);

        imageLike = new ImageView(getContext());
        imageLike.setBackgroundResource(R.drawable.animation_like);
        animLike = (AnimationDrawable) imageLike.getBackground();
        likeNum = new TextView(getContext());
        likeNum.setText(like + "%");
        likeNum.setTextColor(defaultTextColor);
        TextPaint likeNumPaint = likeNum.getPaint();
        likeNumPaint.setFakeBoldText(true);
        likeNum.setTextSize(20f);
        likeText = new TextView(getContext());
        likeText.setText(defaultLike);
        likeText.setTextColor(defaultTextColor);

        imageDisLike = new ImageView(getContext());
        imageDisLike.setBackgroundResource(R.drawable.animation_dislike);
        animDisLike = (AnimationDrawable) imageDisLike.getBackground();
        disNum = new TextView(getContext());
        disNum.setText(disLike + "%");
        disNum.setTextColor(defaultTextColor);
        TextPaint disNumPaint = disNum.getPaint();
        disNumPaint.setFakeBoldText(true);
        disNum.setTextSize(20f);
        disText = new TextView(getContext());
        disText.setText(defaultDis);
        disText.setTextColor(defaultTextColor);

        likeBack = new LinearLayout(getContext());
        disBack = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(defaultSize, defaultSize);
        likeBack.addView(imageLike, params);
        disBack.addView(imageDisLike, params);
        likeBack.setBackgroundResource(R.drawable.white_background);
        disBack.setBackgroundResource(R.drawable.white_background);

        likeAll = new LinearLayout(getContext());
        disAll = new LinearLayout(getContext());
        likeAll.setOrientation(VERTICAL);
        disAll.setOrientation(VERTICAL);
        likeAll.setGravity(Gravity.CENTER_HORIZONTAL);
        disAll.setGravity(Gravity.CENTER_HORIZONTAL);
        likeAll.setBackgroundColor(Color.TRANSPARENT);
        disAll.setBackgroundColor(Color.TRANSPARENT);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 10, 0, 0);
        lp.gravity = Gravity.CENTER;
        disAll.setGravity(Gravity.CENTER_HORIZONTAL);
        likeAll.setGravity(Gravity.RIGHT);
        disAll.addView(disNum, lp);
        disAll.addView(disText, lp);
        disAll.addView(disBack, lp);
        likeAll.addView(likeNum, lp);
        likeAll.addView(likeText, lp);
        likeAll.addView(likeBack, lp);

        ImageView imageView = new ImageView(getContext());
        imageView.setBackground(new ColorDrawable(Color.GRAY));
        LayoutParams layoutParams = new LayoutParams(3, 80);
        layoutParams.setMargins(dividerMargin, 10, dividerMargin, defaultBottom + 20);
        layoutParams.gravity = Gravity.BOTTOM;

        LayoutParams p = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.setMargins(30, 20, 30, defaultBottom);
        p.gravity = Gravity.BOTTOM;
        addView(disAll, p);
        addView(imageView, layoutParams);
        addView(likeAll, p);

        setVisibilities(GONE);
    }

    public void setVisibilities(int v){
        likeNum.setVisibility(v);
        disNum.setVisibility(v);
        likeText.setVisibility(v);
        disText.setVisibility(v);
    }

    private void bindListener(){
        imageDisLike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
                animBack();
                setVisibilities(VISIBLE);

                setBackgroundColor(Color.parseColor(defaultShadow));
                likeBack.setBackgroundResource(R.drawable.white_background);
                disBack.setBackgroundResource(R.drawable.yellow_background);

                imageLike.setBackground(null);
                imageLike.setBackgroundResource(R.drawable.animation_like);
                animLike = (AnimationDrawable) imageLike.getBackground();
            }
        });
        imageLike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 0;
                animBack();
                setVisibilities(VISIBLE);
                setBackgroundColor(Color.parseColor(defaultShadow));
                disBack.setBackgroundResource(R.drawable.white_background);
                likeBack.setBackgroundResource(R.drawable.yellow_background);
                imageDisLike.setBackground(null);
                imageDisLike.setBackgroundResource(R.drawable.animation_dislike);
                animDisLike = (AnimationDrawable) imageDisLike.getBackground();
            }
        });
    }

    private void animBack(){
        imageDisLike.setClickable(false);
        imageLike.setClickable(false);

        final int max = Math.max(like * 4, disLike * 4);
        animatorBack = ValueAnimator.ofInt(5, max);
        animatorBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int margin = (int)valueAnimator.getAnimatedValue();
                LayoutParams params = (LayoutParams) imageLike.getLayoutParams();
                params.bottomMargin = margin;
                if(margin <= like * 4){
                    imageLike.setLayoutParams(params);
                }
                if(margin <= disLike * 4){
                    imageDisLike.setLayoutParams(params);
                }
            }
        });

        isClose = false;
        animatorBack.addListener(this);
        animatorBack.setDuration(500);
        animatorBack.start();
    }

    public void setBackUp(){
        final int max = Math.max(like * 4, like * 4);
        animatorBack = ValueAnimator.ofInt(max ,5);
        animatorBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int margin = (int)valueAnimator.getAnimatedValue();
                LayoutParams params = (LayoutParams) imageLike.getLayoutParams();
                params.bottomMargin = margin;
                if(margin <= like * 4){
                    imageLike.setLayoutParams(params);
                }
                if(margin <= disLike * 4){
                    imageDisLike.setLayoutParams(params);
                }
            }
        });
        isClose = true;
        animatorBack.addListener(this);
        animatorBack.setDuration(500);
        animatorBack.start();
    }


    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        animDisLike.stop();
        animLike.stop();

        if(isClose){
            imageDisLike.setClickable(true);
            imageLike.setClickable(true);

            setVisibilities(GONE);
            setBackgroundColor(Color.TRANSPARENT);
            return;
        }
        isClose = true;

        if(type == 0){
            animLike.start();
            objectY(imageLike);
        }else{
            animDisLike.start();
            objectX(imageDisLike);
        }
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    private void objectY(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY",  -10.0f, 0.0f, 10.0f, 0.0f, -10.0f, 0.0f, 10.0f, 0);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(1500);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setBackUp();
            }
        });
    }

    private void objectX(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX",  -10.0f, 0.0f, 10.0f, 0.0f, -10.0f, 0.0f, 10.0f, 0);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(1500);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setBackUp();
            }
        });
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //px转dp
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
