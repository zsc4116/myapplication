package com.example.zhoushicheng.myapplication;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by zhoushicheng on 2018/5/14.
 */

public class DisplayUtils {

    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static int pxToDp(float px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, Resources.getSystem().getDisplayMetrics());
    }

}
