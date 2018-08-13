package com.example.zhoushicheng.myapplication.ScannerSample;

import android.content.Context;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by zhoushicheng on 2018/6/10.
 */

public class ScannerView extends ZBarScannerView {

    public ScannerView(Context context) {
        this(context, null);
    }

    public ScannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected IViewFinder createViewFinderView(Context context) {
        return new ViewFinderView(context);
    }
}
