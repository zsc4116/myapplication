package com.example.zhoushicheng.myapplication.CustomDialogSample;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by zhoushicheng on 2018/5/23.
 */

public class CustomDialog extends Dialog {

    private static int default_width = 160; // 默认宽度
    private static int default_height = 120;// 默认高度

    private boolean noBackPressed = false;

    public CustomDialog(Context context, View layout, int style) {
        this(context, default_width, default_height, layout, style);
    }

    public CustomDialog(Context context, int width, int height, View layout, int style) {
        super(context, style);
        // 加载布局
        setContentView(layout);
        // 设置Dialog参数
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    /**
     * 设置返回键
     *
     * @param b
     */
    public void setBackPressed(boolean b) {
        noBackPressed = b;
    }

    @Override
    public void onBackPressed() {
        if (!noBackPressed) {
            super.onBackPressed();
        }
    }
}
