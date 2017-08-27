package com.example.zhoushicheng.myapplication.SurfaceViewSample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.zhoushicheng.myapplication.R;

/**
 * Created by zhoushicheng on 2017/8/26.
 */

public class LuckyPlateView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mHolder;

    //与SurfaceHolder绑定的Canvas；
    private Canvas mCanvas;

    //用于绘制的线程；
    private Thread t;

    //线程控制开关；
    private boolean isRunning;

    //抽奖文字
    private String[] mStrs = new String[]{"单反相机", "IPAD", "恭喜发财", "IPHONE", "软妹一枚", "恭喜发财"};

    //每个盘块的颜色
    private  int[] mColors = new int[]{0xFFFFC300, 0xFFF17E01, 0xFFFFC300, 0xFFF17E01, 0xFFFFC300, 0xFFF17E01};

    //与文字对应的图片
    private int[] mImgs = new int[]{R.drawable.bike, R.drawable.boat, R.drawable.platform, R.drawable.railway, R.drawable.ship, R.drawable.stop};

    //与文字对应图片的bitmap数组
    private Bitmap[] mImgsBitmap;

    //盘块的个数
    private int mItemCount = 6;

    //绘制盘块的范围
    private RectF mRange = new RectF();

    //圆的直径
    private int mRadius;

    //绘制盘块的画笔
    private Paint mArcPaint;

    //绘制文字的画笔
    private Paint mTextPaint;

    //滚动速度
    private double mSpeed;
    private volatile float mStartAngle = 0;

    //是否点击了停止
    private boolean isShouldEnd;

    //控件中心位置
    private int mCenter;

    private int mPadding;

    //背景图的bitmap
    private Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);

    //文字大小
    private float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());

    public LuckyPlateView(Context context) {
        super(context);
    }

    public LuckyPlateView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mHolder = getHolder();
        mHolder.addCallback(this);


//        setZOrderOnTop(true);//设置画布 背景透明
//        mHolder.setFormat(PixelFormat.TRANSLUCENT);

        //设置可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        //设置常亮
        this.setKeepScreenOn(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        //获取圆形的直径
        mRadius = width - getPaddingLeft() - getPaddingRight();
        //padding值
        mPadding = getPaddingLeft();
        //中心点
        mCenter = width / 2;
        setMeasuredDimension(width, width);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(0xFFffffff);
        mTextPaint.setTextSize(mTextSize);

        mRange = new RectF(getPaddingLeft(), getPaddingLeft(), mRadius + getPaddingLeft(), mRadius + getPaddingLeft());

        mImgsBitmap = new Bitmap[mItemCount];
        for(int i = 0; i < mItemCount; i++){
            mImgsBitmap[i] = BitmapFactory.decodeResource(getResources(), mImgs[i]);
        }


        //开启线程
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //通知线程关闭
        isRunning = false;
    }

    @Override
    public void run() {
        //不断进行draw
        while(isRunning){
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            try{
                if(end - start < 50){
                    Thread.sleep(50 - (end - start));
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void draw(){
        try{
            //获得canvas
            mCanvas = mHolder.lockCanvas();
            if(mCanvas != null){
                //绘制背景
                drawBg();

                //绘制每个盘块，每个盘块上的文本，每个盘块上的图片
                float tmpAngle = mStartAngle;
                float sweepAngle = (float)(360 / mItemCount);
                for(int i = 0; i < mItemCount; i++){
                    //绘制盘块
                    mArcPaint.setColor(mColors[i]);
                    mCanvas.drawArc(mRange, tmpAngle ,sweepAngle, true, mArcPaint);
                    //绘制文本
                    drawText(tmpAngle, sweepAngle, mStrs[i]);
                    //绘制Icon
                    drawIcon(tmpAngle, i);

                    tmpAngle += sweepAngle;
                }

                //如果mSpeed不等于0，则相当于在滚动
                mStartAngle += mSpeed;

                //点击停止时，设置mSpeed为递减，为0值转盘停止
                if(isShouldEnd){
                    mSpeed -= 1;
                }
                if(mSpeed <= 0){
                    mSpeed = 0;
                    isShouldEnd = false;
                }

                //根据当前旋转的mStartAngle计算当前滚动到的区域
                calInExactArea(mStartAngle);
            }
        }catch (Exception e){

        }finally {

            if(mCanvas != null){
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private void drawBg(){
        mCanvas.drawColor(0xFFffffff);
        mCanvas.drawBitmap(mBgBitmap, null, new Rect(mPadding / 2, mPadding / 2, getMeasuredWidth() - mPadding / 2, getMeasuredWidth() - mPadding / 2), null);
    }

    public void calInExactArea(float startAngle){
        float rotate = startAngle + 90;
        rotate %= 360.0;
        for(int i = 0; i < mItemCount; i++){
            float from = 360 - (i + 1) * (360 / mItemCount);
            float to = from + 360 - (i) * (360 / mItemCount);

            if((rotate > from) && (rotate < to)){
                return;
            }
        }
    }

    private void drawText(float startAngle, float sweepAngle, String string){
        Path path = new Path();
        path.addArc(mRange, startAngle, sweepAngle);
        float textWidth = mTextPaint.measureText(string);
        //利用水平偏移让蚊子居中
        float hOffset = (float)(mRadius * Math.PI / mItemCount / 2 - textWidth / 2);
        float vOffest = mRadius / 2 / 6;
        mCanvas.drawTextOnPath(string, path, hOffset, vOffest, mTextPaint);
    }

    private void drawIcon(float startAngle, int i){
        int imgWidth = mRadius / 8;

        float angle = (float)((30 + startAngle) * (Math.PI / 180));

        int x = (int)(mCenter + mRadius / 2 / 2 * Math.cos(angle));
        int y = (int)(mCenter + mRadius / 2 / 2 * Math.sin(angle));

        Rect rect = new Rect(x - imgWidth / 2 , y - imgWidth / 2, x + imgWidth / 2, y + imgWidth / 2);

        mCanvas.drawBitmap(mImgsBitmap[i], null ,rect, null);
    }

    public void luckyStart(int luckyIndex){
        //每项角度大小
        float angle = (float)(360 / mItemCount);
        //中奖范围
        float from = 270 - (luckyIndex + 1) * angle;
        float to = from + angle;
        float targetFrom = 4 * 360 + from;

        float v1 = (float)(Math.sqrt(1 * 1 + 8 * 1 * targetFrom) - 1) / 2;
        float targetTo = 4 * 360 + to;
        float v2 = (float)(Math.sqrt(1 * 1 + 8 * 1 * targetTo) - 1) / 2;

        mSpeed = (float)(v1 + Math.random() * (v2 - v1));
        isShouldEnd = false;
    }

    public void luckyEnd(){
        mStartAngle = 0;
        isShouldEnd = true;
    }

    public boolean isStart(){
        return mSpeed != 0;
    }

    public boolean isShouldEnd(){
        return isShouldEnd;
    }
}
