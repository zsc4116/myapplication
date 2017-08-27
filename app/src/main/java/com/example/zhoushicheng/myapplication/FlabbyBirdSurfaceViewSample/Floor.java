package com.example.zhoushicheng.myapplication.FlabbyBirdSurfaceViewSample;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

/**
 * Created by zhoushicheng on 2017/8/27.
 */

public class Floor {

    /**
     * 地板位置 游戏面板高度的4/5到底部
     * */
    private static final float FLOOR_Y_POS_RADIO = 4 / 5.0f;
    /**
     * x坐标
     * */
    private int x;
    /**
     * y坐标
     * */
    private int y;
    /**
     * 填充物
     * */
    private BitmapShader mFloorShader;
    private int mGameWidth;
    private int mGameHeight;

    public Floor(int gameWidht, int gameHeight, Bitmap floorBg){
        mGameWidth = gameWidht;
        mGameHeight = gameHeight;
        y = (int)(gameHeight * FLOOR_Y_POS_RADIO);
        mFloorShader = new BitmapShader(floorBg, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
    }

    @SuppressLint("WrongConstant")
    public void draw(Canvas mCanvas, Paint mPaint){
        if(-x > mGameWidth){
            x = x % mGameWidth;
        }
        mCanvas.save(Canvas.MATRIX_SAVE_FLAG);
        mCanvas.translate(x, y);
        mPaint.setShader(mFloorShader);
        mCanvas.drawRect(x, 0, -x + mGameWidth, mGameHeight - y, mPaint);
        mCanvas.restore();
        mPaint.setShader(null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
