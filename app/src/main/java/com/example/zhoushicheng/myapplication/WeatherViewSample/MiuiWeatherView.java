package com.example.zhoushicheng.myapplication.WeatherViewSample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.example.zhoushicheng.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoushicheng on 2017/8/26.
 */

public class MiuiWeatherView extends View {
    private int backgroundColor;
    private int minViewHeight;
    private int minPointHeight;
    private int lineInterval;
    private float pointRadius;
    private float textSize;
    private float pointGap;
    private int defaultPadding;
    private float iconWidth;
    private int viewHeight;
    private int viewWidth;
    private int screenWidth;
    private int screenHeight;

    private float lastX = 0;
    private float x = 0;

    private int maxTemperature;
    private int minTemperature;

    private List<WeatherBean> data = new ArrayList<>();
    private List<Pair<Integer, String>> weatherDatas = new ArrayList<>();
    private List<Float> dashDatas = new ArrayList<>();
    private List<PointF> points = new ArrayList<>();
    private Map<String, Bitmap> icons = new HashMap<>();

    private Paint linePaint;
    private Paint textPaint;
    private Paint circlePaint;

    private Scroller scroller;
    private ViewConfiguration viewConfiguration;
    private VelocityTracker velocityTracker;

    private static final int DEFAULT_GRAY = 0xff808080;
    private static final int DEFAULT_BLUE = 0xff4682b4;


    public MiuiWeatherView(Context context) {
        this(context, null);
    }

    public MiuiWeatherView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MiuiWeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        scroller = new Scroller(context);
        viewConfiguration = ViewConfiguration.get(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MiuiWeatherView);

        minPointHeight = (int) ta.getDimension(R.styleable.MiuiWeatherView_min_point_height, dp2pxF(context, 60));
        lineInterval = (int) ta.getDimension(R.styleable.MiuiWeatherView_line_interval, dp2pxF(context, 60));
        backgroundColor = ta.getColor(R.styleable.MiuiWeatherView_background_color, Color.WHITE);

        ta.recycle();

        setBackgroundColor(backgroundColor);

        initSize(context);

        initPaint(context);

        initIcons();
    }

    private void initSize(Context context) {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        minViewHeight = 3 * minPointHeight;
        pointRadius = dp2pxF(context, 2.5f);
        textSize = sp2pxF(context, 10);
        defaultPadding = (int) (0.5 * minPointHeight);
        iconWidth = (1.0f / 3.0f) * lineInterval;
    }

    private void initPaint(Context context) {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(dp2pxF(context, 1));

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStrokeWidth(dp2pxF(context, 1));
    }

    private void initIcons() {
        icons.clear();
        String[] weathers = WeatherBean.getAllWeathers();
        for (int i = 0; i < weathers.length; i++) {
            Bitmap bmp = getWeatherIcon(weathers[i], iconWidth, iconWidth);
            icons.put(weathers[i], bmp);
        }
    }

    private Bitmap getWeatherIcon(String weather, float requestW, float requestH) {
        int resId = getIconResId(weather);
        Bitmap bmp;
        int outWidth, outHeight;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), resId, options);
        outWidth = options.outWidth;
        outHeight = options.outHeight;
        options.inSampleSize = 1;
        if (outWidth > requestW || outHeight > requestH) {
            int ratioW = Math.round(outWidth / requestW);
            int ratioH = Math.round(outHeight / requestH);
            options.inSampleSize = Math.max(ratioW, ratioH);
        }
        options.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeResource(getResources(), resId, options);
        return bmp;
    }

    private int getIconResId(String weather) {
        int resId;
        switch (weather) {
            case WeatherBean.SUN:
                resId = R.drawable.sun;
                break;
            case WeatherBean.CLOUDY:
                resId = R.drawable.cloudy;
                break;
            case WeatherBean.RAIN:
                resId = R.drawable.rain;
                break;
            case WeatherBean.SNOW:
                resId = R.drawable.snow;
                break;
            case WeatherBean.SUN_CLOUD:
                resId = R.drawable.sun_cloudy;
                break;
            case WeatherBean.THUNDER:
            default:
                resId = R.drawable.thunder;
                break;
        }
        return resId;
    }

    public void setData(List<WeatherBean> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        this.data = data;
        weatherDatas.clear();
        points.clear();
        dashDatas.clear();

        initWeatherMap();
        requestLayout();
        invalidate();
    }

    private void initWeatherMap() {
        weatherDatas.clear();
        String lastWeather = "";
        int count = 0;
        for (int i = 0; i < data.size(); i++) {
            WeatherBean bean = data.get(i);
            if (i == 0) {
                lastWeather = bean.weather;
            }
            if (bean.weather != lastWeather) {
                Pair<Integer, String> pair = new Pair<>(count, lastWeather);
                weatherDatas.add(pair);
                count = 1;
            } else {
                count++;
            }
            lastWeather = bean.weather;

            if (i == data.size() - 1) {
                Pair<Integer, String> pair = new Pair<>(count, lastWeather);
                weatherDatas.add(pair);
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            viewHeight = Math.max(heightSize, minViewHeight);
        } else {
            viewHeight = minViewHeight;
        }

        int totalWidth = 0;
        if (data.size() > 1) {
            totalWidth = 2 * defaultPadding + lineInterval * (data.size() - 1);
        }
        viewWidth = Math.max(screenWidth, totalWidth);

        setMeasuredDimension(viewHeight, viewWidth);
        calculatePointGap();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initSize(getContext());
        calculatePointGap();
    }

    private void calculatePointGap() {
        int lastMaxTem = -100000;
        int lastMinTem = 100000;
        for (WeatherBean bean : data) {
            if (bean.temperature > lastMaxTem) {
                maxTemperature = bean.temperature;
                lastMaxTem = bean.temperature;
            }
            if (bean.temperature < lastMinTem) {
                minTemperature = bean.temperature;
                lastMinTem = bean.temperature;
            }
        }
        float gap = (maxTemperature - minTemperature) * 1.0f;
        gap = (gap == 0.0f ? 0.1f : gap);
        pointGap = (viewHeight - minPointHeight - 2 * defaultPadding) / gap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data.isEmpty()) {
            return;
        }
        drawAxis(canvas);

        drawLinesAndPoints(canvas);

        drawTemperature(canvas);

        drawWeatherDash(canvas);

        drawWeatherIcon(canvas);
    }

    private void drawAxis(Canvas canvas) {
        canvas.save();
        linePaint.setColor(DEFAULT_GRAY);
        linePaint.setStrokeWidth(dp2pxF(getContext(), 1));

        canvas.drawLine(defaultPadding,
                viewHeight - defaultPadding,
                viewWidth - defaultPadding,
                viewHeight - defaultPadding,
                linePaint);

        float centerY = viewHeight - defaultPadding + dp2pxF(getContext(), 15);
        float centerX;
        for (int i = 0; i < data.size(); i++) {
            String text = data.get(i).time;
            centerX = defaultPadding + i * lineInterval;
            Paint.FontMetrics m = textPaint.getFontMetrics();
            canvas.drawText(text, 0, text.length(), centerX, centerY - (m.ascent + m.descent) / 2, textPaint);
        }

        canvas.restore();
    }

    private void drawLinesAndPoints(Canvas canvas) {
        canvas.save();
        linePaint.setColor(DEFAULT_BLUE);
        linePaint.setStrokeWidth(dp2pxF(getContext(), 1));
        linePaint.setStyle(Paint.Style.STROKE);

        Path linePath = new Path();
        points.clear();
        int baseHeight = defaultPadding + minPointHeight;
        float centerX;
        float centerY;
        for (int i = 0; i < data.size(); i++) {
            int tem = data.get(i).temperature;
            tem = tem - minTemperature;
            centerY = (int) (viewHeight - (baseHeight + tem * pointGap));
            centerX = defaultPadding + i * lineInterval;
            points.add(new PointF(centerX, centerY));
            if (i == 0) {
                linePath.moveTo(centerX, centerY);
            } else {
                linePath.lineTo(centerX, centerY);
            }
        }
        canvas.drawPath(linePath, linePaint);

        float x, y;
        for (int i = 0; i < points.size(); i++) {
            x = points.get(i).x;
            y = points.get(i).y;

            circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            circlePaint.setColor(backgroundColor);
            canvas.drawCircle(x, y, pointRadius + dp2pxF(getContext(), 1), circlePaint);

            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setColor(DEFAULT_BLUE);
            canvas.drawCircle(x, y, pointRadius, circlePaint);
        }
        canvas.restore();
    }

    private void drawTemperature(Canvas canvas) {
        canvas.save();

        textPaint.setTextSize(1.2f * textSize);
        float centerY;
        float centerX;
        String text;
        for (int i = 0; i < points.size(); i++) {
            text = data.get(i).temperatureStr;
            centerX = points.get(i).x;
            centerY = points.get(i).y - dp2pxF(getContext(), 15);
            Paint.FontMetrics metrics = textPaint.getFontMetrics();
            canvas.drawText(text, centerX, centerY - (metrics.ascent + metrics.descent) / 2, textPaint);
        }
        textPaint.setTextSize(textSize);
        canvas.restore();
    }

    private void drawWeatherDash(Canvas canvas) {
        canvas.save();
        linePaint.setColor(DEFAULT_GRAY);
        linePaint.setStrokeWidth(dp2pxF(getContext(), 0.5f));
        linePaint.setAlpha(0x99);

        float[] f = {dp2pxF(getContext(), 5), dp2pxF(getContext(), 2)};
        PathEffect pathEffect = new DashPathEffect(f, 0);
        linePaint.setPathEffect(pathEffect);

        dashDatas.clear();
        int interval = 0;
        float startX, startY, endX, endY;
        endY = viewHeight - defaultPadding;

        canvas.drawLine(defaultPadding, points.get(0).y + pointRadius + dp2pxF(getContext(), 2),
                defaultPadding, endY, linePaint);
        dashDatas.add((float) defaultPadding);

        for (int i = 0; i < weatherDatas.size(); i++) {
            interval += weatherDatas.get(i).first;
            if (interval > points.size() - 1) {
                interval = points.size() - 1;
            }
            startX = endX = defaultPadding + interval * lineInterval;
            startY = points.get(interval).y + pointRadius + dp2pxF(getContext(), 2);
            dashDatas.add(startX);

            Path path = new Path();
            path.moveTo(startX, startY);
            path.lineTo(endX, endY);
//            canvas.drawLine(startX, startY, endX, endY, linePaint);
            canvas.drawPath(path, linePaint);
        }

        if (weatherDatas.get(weatherDatas.size() - 1).first == 1 && dashDatas.size() > 1) {
            dashDatas.remove(dashDatas.get(dashDatas.size() - 1));
        }
        linePaint.setPathEffect(null);
        linePaint.setAlpha(0xff);
        canvas.restore();
    }

    private void drawWeatherIcon(Canvas canvas) {
        canvas.save();
        textPaint.setTextSize(0.9f * textSize);

        boolean leftUesdScreenLeft = false;
        boolean rightUesdScreenRight = false;

        int scrollX = getScrollX();
        float left, right;
        float iconX, iconY;
        float textY;
        iconY = viewHeight - (defaultPadding + minPointHeight / 2.0f);
        textY = iconY + iconWidth / 2.0f + dp2pxF(getContext(), 10);
        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        for (int i = 0; i < dashDatas.size() - 1; i++) {
            left = dashDatas.get(i);
            right = dashDatas.get(i + 1);

            if (left < scrollX && right < scrollX + screenWidth) {
                left = scrollX;
                leftUesdScreenLeft = true;
            }
            if (right > scrollX + screenWidth && left > scrollX) {
                right = scrollX + screenWidth;
                rightUesdScreenRight = true;
            }

            if (right - left > iconWidth) {
                iconX = left + (right - left) / 2.0f;
            } else {
                if (leftUesdScreenLeft) {
                    iconX = right - iconWidth / 2.0f;
                } else {
                    iconX = left + iconWidth / 2.0f;
                }
            }

            if (right < scrollX) {
                iconX = right - iconWidth / 2.0f;
            } else if (left > scrollX + screenWidth) {
                iconX = left + iconWidth / 2.0f;
            } else if (left < scrollX && right > scrollX + screenWidth) {
                iconX = scrollX + (screenWidth / 2.0f);
            }

            Bitmap icon = icons.get(weatherDatas.get(i).second);

            RectF iconRect = new RectF(iconX - iconWidth / 2.0f,
                    iconY - iconWidth / 2.0f,
                    iconX + iconWidth / 2.0f,
                    iconY + iconWidth / 2.0f);

            canvas.drawBitmap(icon, null, iconRect, null);
            canvas.drawText(weatherDatas.get(i).second,
                    iconX, textY - (metrics.ascent + metrics.descent) / 2,
                    textPaint);

            leftUesdScreenLeft = rightUesdScreenRight = false;
        }

        textPaint.setTextSize(textSize);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                lastX = x = event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                int deltaX = (int) (lastX - x);
                if (getScrollX() + deltaX < 0) {
                    scrollTo(0, 0);
                    return true;
                } else if (getScrollX() + deltaX > viewWidth - screenWidth) {
                    scrollTo(viewWidth - screenWidth, 0);
                    return true;
                }
                scrollBy(deltaX, 0);
                lastX = x;
                break;
            case MotionEvent.ACTION_UP:
                x = event.getX();
                velocityTracker.computeCurrentVelocity(1000);
                int xVelocity = (int) velocityTracker.getXVelocity();
                if(Math.abs(xVelocity) > viewConfiguration.getScaledMinimumFlingVelocity()){
                    scroller.fling(getScrollX(), 0, -xVelocity, 0,
                            0, viewWidth - screenWidth, 0, 0);
                    invalidate();
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }

    public int dp2pxF(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public static int sp2pxF(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }
}
