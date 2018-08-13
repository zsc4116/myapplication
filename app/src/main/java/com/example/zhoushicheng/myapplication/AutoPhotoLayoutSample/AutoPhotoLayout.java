package com.example.zhoushicheng.myapplication.AutoPhotoLayoutSample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.example.zhoushicheng.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoushicheng on 2018/5/12.
 */

public class AutoPhotoLayout extends LinearLayoutCompat {

    private int mCurrentNum = 0;
    private int mMaxNum = 0;
    private int mMaxLineNum = 0;
    private ImageView mImgAdd = null;
    private LayoutParams mParams = null;
    private int mDeletedId = 0;
    private ImageView mTargetImageView = null;
    private int mImageMargin = 0;
    private int mIconSize = 0;
    private List<View> mLineViews = null;
    private AlertDialog mTargetDialog = null;
    private static final List<List<View>> ALL_VIEWS = new ArrayList<>();
    private static final List<Integer> Line_HEIGHTS = new ArrayList<>();

    private boolean mIsOnceInitOnMeasure = false;
    private boolean mHasInitOnLayout = false;


    public AutoPhotoLayout(Context context) {
        this(context, null);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.camera_flow_layout);
        mMaxNum = typedArray.getInt(R.styleable.camera_flow_layout_max_count, 1);
        mImageMargin = typedArray.getInt(R.styleable.camera_flow_layout_item_margin, 0);
        mMaxLineNum = typedArray.getInt(R.styleable.camera_flow_layout_line_count, 3);
        mIconSize = (int) typedArray.getDimension(R.styleable.camera_flow_layout_icon_size, 20);

        typedArray.recycle();
    }


    public final void onCrop(Uri uri) {
        createNewImageView();
    }

    private void createNewImageView() {
        mTargetImageView = new ImageView(getContext());
        mTargetImageView.setId(mCurrentNum);
        mTargetImageView.setLayoutParams(mParams);
        mTargetImageView.setImageResource(R.drawable.aa);
        mTargetImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeletedId = v.getId();
                mTargetDialog.show();
                final Window window = mTargetDialog.getWindow();
                if (window != null) {
                    window.setContentView(R.layout.dialog_image_click_panel);
                    window.setGravity(Gravity.BOTTOM);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    final WindowManager.LayoutParams params = window.getAttributes();
                    params.width = WindowManager.LayoutParams.MATCH_PARENT;
                    params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    params.dimAmount = 0.5f;
                    window.setAttributes(params);
                    window.findViewById(R.id.dialog_image_clicked_btn_delete).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final ImageView deletedImageView = (ImageView) findViewById(mDeletedId);
                            final AlphaAnimation animation = new AlphaAnimation(1, 0);
                            animation.setDuration(500);
                            animation.setRepeatCount(0);
                            animation.setFillAfter(true);
                            animation.setStartOffset(0);
                            deletedImageView.setAnimation(animation);
                            animation.start();
                            AutoPhotoLayout.this.removeView(deletedImageView);
                            mCurrentNum -= 1;
                            if (mCurrentNum < mMaxNum) {
                                mImgAdd.setVisibility(VISIBLE);
                            }
                            mTargetDialog.cancel();
                        }
                    });
                }

            }
        });

        this.addView(mTargetImageView, mCurrentNum);
        mCurrentNum++;

        if (mCurrentNum >= mMaxNum) {
            mImgAdd.setVisibility(GONE);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        final int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;

        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            if (i == childCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }

        }

        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );

        final int imageSideLen = sizeWidth / mMaxLineNum;
        if (!mIsOnceInitOnMeasure) {
            mParams = new LayoutParams(imageSideLen, imageSideLen);
            mIsOnceInitOnMeasure = true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        ALL_VIEWS.clear();
        Line_HEIGHTS.clear();

        final int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;

        if (!mHasInitOnLayout) {
            mHasInitOnLayout = true;
            mLineViews = new ArrayList<>();
        }

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            final int childWidth = child.getMeasuredWidth();
            final int childHeight = child.getMeasuredHeight();

            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin >
                    width - getPaddingLeft() - getPaddingRight()) {
                Line_HEIGHTS.add(lineHeight);
                ALL_VIEWS.add(mLineViews);
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                mLineViews.clear();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            mLineViews.add(child);
        }

        Line_HEIGHTS.add(lineHeight);
        ALL_VIEWS.add(mLineViews);


        int left = getPaddingLeft();
        int top = getPaddingTop();
        final int lineNum = ALL_VIEWS.size();
        for (int i = 0; i < lineNum; i++) {

            mLineViews = ALL_VIEWS.get(i);
            lineHeight = Line_HEIGHTS.get(i);
            final int size = mLineViews.size();

            for (int j = 0; j < size; j++) {
                Log.e("zzz", "i = " + i + " j = " + j);
                final View child = mLineViews.get(j);

                if (child.getVisibility() == GONE) {
                    continue;
                }
                final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                final int lc = left + lp.leftMargin;
                final int tc = top + lp.topMargin;
                final int rc = lc + child.getMeasuredWidth() + mImageMargin;
                final int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredHeight() + lp.leftMargin + lp.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
        mImgAdd.setLayoutParams(mParams);
        mHasInitOnLayout = false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initAddIcon();
        mTargetDialog = new AlertDialog.Builder(getContext()).create();
    }

    private void initAddIcon() {
        mImgAdd = new ImageView(getContext());
        mImgAdd.setImageResource(R.mipmap.dianping_neironglan_tainjiatupian);
        final LayoutParams lp = new LayoutParams(mIconSize, mIconSize);
        mImgAdd.setLayoutParams(lp);
        mImgAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onCrop(null);
            }
        });
        this.addView(mImgAdd);
    }
}
