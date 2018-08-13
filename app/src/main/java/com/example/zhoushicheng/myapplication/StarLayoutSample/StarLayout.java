package com.example.zhoushicheng.myapplication.StarLayoutSample;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zhoushicheng.myapplication.R;

import java.util.ArrayList;

/**
 * Created by zhoushicheng on 2018/5/12.
 */

public class StarLayout extends LinearLayoutCompat implements View.OnClickListener {

    private static final int STAR_TOTAL_COUNT = 5;
    private static final ArrayList<ImageView> STARS = new ArrayList<>();


    public StarLayout(Context context) {
        this(context, null);
    }

    public StarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStarIcon();
    }

    private void initStarIcon() {
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            final ImageView star = new ImageView(getContext());
            final LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            star.setLayoutParams(lp);
            star.setImageResource(R.mipmap.neironglan_weixuanzhong_xingxing);
            star.setTag(R.id.star_cout, i);
            star.setTag(R.id.star_is_selected, false);
            star.setOnClickListener(this);
            STARS.add(star);
            this.addView(star);

        }
    }

    public int getStarCount() {
        int count = 0;
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            final ImageView star = STARS.get(i);
            final boolean isSelected = (boolean) star.getTag(R.id.star_is_selected);
            if (isSelected) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void onClick(View v) {
        final ImageView star = (ImageView) v;
        final int count = (int) star.getTag(R.id.star_cout);
        final boolean isSelected = (boolean) star.getTag(R.id.star_is_selected);
        if (!isSelected) {
            selectStar(count);
        } else {
            unSelectStar(count);
        }
        if (mStarSelectListener != null) {
            mStarSelectListener.onStarChanged(getStarCount());
        }
    }


    private void selectStar(int count) {
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            if (i <= count) {
                final ImageView star = STARS.get(i);
                star.setImageResource(R.mipmap.neironglan_xuanzhong_xingxing);
                star.setTag(R.id.star_is_selected, true);
            }
        }
    }

    private void unSelectStar(int count) {
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            if (i >= count) {
                final ImageView star = STARS.get(i);
                star.setImageResource(R.mipmap.neironglan_weixuanzhong_xingxing);
                star.setTag(R.id.star_is_selected, false);

            }
        }
    }

    interface StarSelectListener {
        void onStarChanged(int count);
    }

    private StarSelectListener mStarSelectListener;

    public void setStarSelectListener(StarSelectListener listener) {
        this.mStarSelectListener = listener;
    }


}
