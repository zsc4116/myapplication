package com.example.zhoushicheng.myapplication.AdapterViewTypeTestSample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

import java.util.ArrayList;

public class AdapterViewTypeTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_view_type_test);

        ArrayList<Integer> datas = new ArrayList<>();
        datas.add(1);
        datas.add(2);
        datas.add(3);
        datas.add(1);
        datas.add(2);
        datas.add(3);
        datas.add(1);
        datas.add(4);
        datas.add(1);
        datas.add(2);
        datas.add(2);
        datas.add(3);
        datas.add(3);
        datas.add(3);
        datas.add(3);
        datas.add(3);
        datas.add(4);
        datas.add(2);
        datas.add(1);
        datas.add(1);
        datas.add(1);
        datas.add(4);
        datas.add(1);
        datas.add(2);
        datas.add(2);
        datas.add(3);
        datas.add(3);
        datas.add(3);
        datas.add(3);
        datas.add(2);
        datas.add(2);


        ListView listView = (ListView) findViewById(R.id.adapter_test_listview);
        listView.setAdapter(new TestAdapter(this, datas));

    }

    public static class TestAdapter extends BaseAdapter {

        ArrayList<Integer> mData;
        LayoutInflater inflater;
        Context context;

        public TestAdapter(Context context, ArrayList<Integer> mData) {
            inflater = LayoutInflater.from(context);
            this.context = context;
            this.mData = mData;
        }

        @Override
        public int getCount() {
            if (mData != null || mData.size() > 0) {
                return mData.size();
            } else {
                return 0;
            }
        }

        @Override
        public Integer getItem(int position) {
            if (mData != null || mData.size() > 0) {
                return mData.get(position);
            } else {
                return 0;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 5;
        }

        @Override
        public int getItemViewType(int position) {
            if (mData != null || mData.size() > 0) {
                return mData.get(position);
            } else {
                return 0;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                switch (getItemViewType(position)) {
                    case 1:
                        convertView = inflater.inflate(R.layout.item_adapter_view_type_first_layout, parent, false);
                        viewHolder.firstImg = (ImageView) convertView.findViewById(R.id.adapter_test_item_first_img);
                        viewHolder.firstText = (TextView) convertView.findViewById(R.id.adapter_test_item_first_text);
                        viewHolder.flag = 1;
                        Log.e("sss", " set flag = 1");
                        break;
                    case 2:
                        convertView = inflater.inflate(R.layout.item_adapter_view_type_second_layout, parent, false);
                        viewHolder.secondButton = (Button) convertView.findViewById(R.id.adapter_test_item_second_button);
                        viewHolder.secondImg = (ImageView) convertView.findViewById(R.id.adapter_test_item_second_img);
                        viewHolder.flag = 2;
                        Log.e("sss", " set flag = 2");
                        break;
                    case 3:
                        convertView = inflater.inflate(R.layout.item_adapter_view_type_third_layout, parent, false);
                        viewHolder.thirdText = (TextView) convertView.findViewById(R.id.adapter_test_item_third_text);
                        viewHolder.thirdButton = (Button) convertView.findViewById(R.id.adapter_test_item_third_button);
                        viewHolder.flag = 3;
                        Log.e("sss", " set flag = 3");
                        break;
                    case 4:
                        convertView = inflater.inflate(R.layout.item_adapter_view_type_forth_layout, parent, false);
                        viewHolder.forthPager = (ViewPager) convertView.findViewById(R.id.adapter_test_item_forth_pager);
                        viewHolder.flag = 4;
                        Log.e("sss", " set flag = 4");

                        viewHolder.forthPager.setAdapter(new TestPagerAdapter(context));
                        break;
                }
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            switch (getItemViewType(position)) {
                case 1:
                    viewHolder.firstImg.setImageResource(R.mipmap.fishs);
                    viewHolder.firstText.setText("FIRST");
                    Log.e("sss", " type = 1, flag = " + viewHolder.flag);
                    break;
                case 2:
                    viewHolder.secondButton.setText("BUTTON");
                    viewHolder.secondImg.setImageResource(R.drawable.thunder);
                    Log.e("sss", " type = 2, flag = " + viewHolder.flag);
                    break;
                case 3:
                    viewHolder.thirdText.setText("THIRD");
                    viewHolder.thirdButton.setText("BUTTON");
                    Log.e("sss", " type = 3, flag = " + viewHolder.flag);
                    break;
                case 4:
                    Log.e("sss", " type = 4, flag = " + viewHolder.flag);
                    break;
            }

            return convertView;
        }

        public class ViewHolder {
            int flag;

            ImageView firstImg;
            TextView firstText;

            Button secondButton;
            ImageView secondImg;

            TextView thirdText;
            Button thirdButton;

            ViewPager forthPager;
        }
    }

    public static class TestPagerAdapter extends PagerAdapter {

        Context context;

        public TestPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView photoView = new ImageView(context);
            photoView.setScaleType(ImageView.ScaleType.FIT_XY);
            photoView.setImageResource(R.mipmap.fishs);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }


}
