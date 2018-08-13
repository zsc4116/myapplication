package com.example.zhoushicheng.myapplication.SharedElementSample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

/**
 * Created by zhoushicheng on 2018/4/16.
 */

public class MyGridViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImageView;
    private TextView mTextView;

    public MyGridViewHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.grid_image);
        mTextView = (TextView) itemView.findViewById(R.id.grid_text);
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public TextView getTextView() {
        return mTextView;
    }

}
