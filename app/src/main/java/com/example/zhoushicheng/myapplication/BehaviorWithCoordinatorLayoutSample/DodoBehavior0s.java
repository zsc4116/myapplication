package com.example.zhoushicheng.myapplication.BehaviorWithCoordinatorLayoutSample;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

/**
 * Created by ZSC on 2018/8/13.
 */

public class DodoBehavior0s extends CoordinatorLayout.Behavior<Button> {

    private Context mContext;

    private int width;
    private int height;

    public DodoBehavior0s(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        width = display.widthPixels;
        height = display.heightPixels;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Button child, View dependency) {
        return dependency instanceof DodoMoveView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Button child, View dependency) {
        int left = dependency.getLeft();
        int top = dependency.getTop();

        int x = width - left - child.getWidth();
        int y = height - top - child.getHeight();

        setPosition(child, x, y);
        return true;
    }

    private void setPosition(Button child, int x, int y) {
        CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) child.getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        layoutParams.width = y / 2;
        child.setLayoutParams(layoutParams);
    }

}
