package com.example.zhoushicheng.myapplication.CoordinatorLayoutSample;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.zhoushicheng.myapplication.BannerSample.MZBannerViewSampleActivity;
import com.example.zhoushicheng.myapplication.R;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AntHomeFragmentCoordinatorSampleActivity extends AppCompatActivity {

    private List<Integer> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_col_toolbar);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        list.add(R.drawable.bike);
        list.add(R.drawable.boat);
        list.add(R.drawable.railway);


        MZBannerView mMZBanner = (MZBannerView) findViewById(R.id.banner);
        // 设置数据
        mMZBanner.setPages(list, new MZHolderCreator<MZBannerViewSampleActivity.BannerViewHolder>() {
            @Override
            public MZBannerViewSampleActivity.BannerViewHolder createViewHolder() {
                return new MZBannerViewSampleActivity.BannerViewHolder();
            }
        });
    }

    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_image, null);
            mImageView = (ImageView) view.findViewById(R.id.img_item);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }
}
