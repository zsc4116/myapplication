package com.example.zhoushicheng.myapplication.FlabbyBirdSurfaceViewSample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.zhoushicheng.myapplication.R;
import com.example.zhoushicheng.myapplication.Utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoushicheng on 2017/8/27.
 */

public class FlabbyBirdSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Thread t;
    private boolean isRunning;

    private int mWidth;
    private int mHeight;

    private int mSpeed;

    /**
     * 背景
     * */
    private RectF mGamePanelRect = new RectF();
    private Bitmap mBg;

    /**
     * 小鸟
     * */
    private Bird mBird;
    private Bitmap mBirdBitmap;

    /**
     * 地板
     * */
    private Paint mPaint;
    private Floor mFloor;
    private Bitmap mFloorBg;

    /**
     * 管道
     * */
    private Bitmap mPipeTop;
    private Bitmap mPipeBottom;
    private RectF mPipeRect;
    private int mPipeWidth;
    private static final int PIPE_WIDTH = 60;
    private List<Pipe> mPipes = new ArrayList<>();

    /**
     * 分数
     * */
    private final int[] mNums = new int[]{R.drawable.n0, R.drawable.n1,
            R.drawable.n2, R.drawable.n3, R.drawable.n4, R.drawable.n5,
            R.drawable.n6, R.drawable.n7, R.drawable.n8, R.drawable.n9};
    private Bitmap[] mNumBitmap;
    private int mGrade = 100;
    /**
     * 单个数字的高度的1/15
     * */
    private static final float RADIO_SINGLE_NUM_HEIGHT = 1 / 15.0f;
    /**
     * 单个数字的高度
     * */
    private int mSingleGradeWidth;
    /**
     * 单个数字的宽度
     * */
    private int mSingleGradeHeight;
    /**
     * 单个数字的范围
     * */
    private RectF mSingleNumRectF;


    public FlabbyBirdSurfaceView(Context context) {
        this(context, null);
    }

    public FlabbyBirdSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mHolder = getHolder();
        mHolder.addCallback(this);

        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);

        setFocusable(true);
        setFocusableInTouchMode(true);

        this.setKeepScreenOn(true);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mPipeWidth = DisplayUtil.dp2px(getContext(), PIPE_WIDTH);

        initBitmaps();

        mSpeed = DisplayUtil.dp2px(getContext(), 2);
    }


    private void initBitmaps() {
        mBg = loadImageByResId(R.drawable.bg1);
        mBirdBitmap = loadImageByResId(R.drawable.b1);
        mFloorBg = loadImageByResId(R.drawable.floor_bg2);
        mPipeTop = loadImageByResId(R.drawable.g2);
        mPipeBottom = loadImageByResId(R.drawable.g1);

        mNumBitmap = new Bitmap[mNums.length];
        for(int i = 0; i < mNumBitmap.length; i++){
            mNumBitmap[i] = loadImageByResId(mNums[i]);
        }
    }


    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {


                drawBg();
                drawBird();
                drawPipes();
                drawFloor();
                drawGrades();

                mFloor.setX(mFloor.getX() - mSpeed);

            }
        } catch (Exception e) {

        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private void drawBg() {
        mCanvas.drawBitmap(mBg, null, mGamePanelRect, null);
    }

    private void drawBird(){
        mBird.draw(mCanvas);
    }

    private void drawFloor(){
        mFloor.draw(mCanvas, mPaint);
    }

    private void drawPipes(){
        for(Pipe pipe : mPipes){
            pipe.setX(pipe.getX() - mSpeed);
            pipe.draw(mCanvas, mPipeRect);
        }
    }

    @SuppressLint("WrongConstant")
    private void drawGrades(){
        String grade = mGrade + "";
        mCanvas.save(Canvas.MATRIX_SAVE_FLAG);
        mCanvas.translate(mWidth / 2 - grade.length() * mSingleGradeWidth / 2,
                1.0f / 8 * mHeight);
        for(int i = 0; i < grade.length(); i++){
            String numStr = grade.substring(i, i + 1);
            int num = Integer.valueOf(numStr);
            mCanvas.drawBitmap(mNumBitmap[num], null, mSingleNumRectF, null);
            mCanvas.translate(mSingleGradeWidth, 0);
        }
        mCanvas.restore();
    }

    private Bitmap loadImageByResId(int resId){
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
        mGamePanelRect.set(0, 0, w, h);

        mBird = new Bird(getContext(), mWidth, mHeight, mBirdBitmap);
        mFloor = new Floor(mWidth, mHeight, mFloorBg);

        mPipeRect = new RectF(0, 0, mPipeWidth, mHeight);
        Pipe pipe = new Pipe(getContext(), w, h, mPipeTop, mPipeBottom);
        mPipes.add(pipe);

        mSingleGradeHeight = (int)(h * RADIO_SINGLE_NUM_HEIGHT);
        mSingleGradeWidth = (int)(mSingleGradeHeight * 1.0f / mNumBitmap[0].getHeight() * mNumBitmap[0].getWidth());
        mSingleNumRectF = new RectF(0, 0, mSingleGradeWidth, mSingleGradeHeight);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();

            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
