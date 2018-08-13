package com.example.zhoushicheng.myapplication.IndicatorSample;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zhoushicheng.myapplication.R;

/**
 * Created by zhoushicheng on 2018/5/14.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private static final int RES[] = new int[]{R.mipmap.head_img, R.mipmap.head_img0, R.mipmap.head_img1, R.mipmap.head_img2};

    @Override
    public int getCount() {
        return RES.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_pager_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
        imageView.setImageResource(RES[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
