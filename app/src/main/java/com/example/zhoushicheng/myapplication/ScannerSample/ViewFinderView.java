package com.example.zhoushicheng.myapplication.ScannerSample;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

/**
 * Created by zhoushicheng on 2018/6/10.
 */

public class ViewFinderView extends me.dm7.barcodescanner.core.ViewFinderView{
    public ViewFinderView(Context context) {
        this(context, null);
    }

    public ViewFinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mSquareViewFinder = true;
        mBorderPaint.setColor(Color.YELLOW);
        mLaserPaint.setColor(Color.YELLOW);
    }
}
