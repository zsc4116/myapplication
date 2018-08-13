package com.example.zhoushicheng.myapplication.SharedElementSample.fragment;

import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

import java.util.ArrayList;


public class DetailFragment extends Fragment {
    private static final String ARG_NUMBER = "arg_number";
    private ArrayList<DetailData> mDetailDatas;

    ImageView mImage;
    TextView mHead;
    TextView mBody;

    /**
     * 根据选择的number, 选择展示的数据
     *
     * @param number 数字
     * @return 详情页面
     */
    public static DetailFragment newInstance(@IntRange(from = 0, to = 5) int number) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_NUMBER, number);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);

        return detailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mImage = (ImageView) view.findViewById(R.id.detail_image);
        mHead = (TextView) view.findViewById(R.id.detail_head);
        mBody = (TextView) view.findViewById(R.id.detail_body);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();

        int number = getArguments().getInt(ARG_NUMBER);
        mImage.setImageResource(mDetailDatas.get(number).getImage());
        mHead.setText(""+mDetailDatas.get(number).getHead());
        mBody.setText(""+mDetailDatas.get(number).getBody());

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }


    // 初始化数据
    private void initData() {
        mDetailDatas = new ArrayList<>();
        mDetailDatas.add(new DetailData(R.drawable.railway, 1, 1));
        mDetailDatas.add(new DetailData(R.drawable.rain, 2, 2));
        mDetailDatas.add(new DetailData(R.drawable.ship, 3, 3));
        mDetailDatas.add(new DetailData(R.drawable.sun, 4, 4));
        mDetailDatas.add(new DetailData(R.drawable.sun_cloudy, 5, 5));
        mDetailDatas.add(new DetailData(R.drawable.thunder, 6, 6));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    // 定义类
    private class DetailData {
        private int mImage;
        private int mHead;
        private int mBody;

        public DetailData(int image, int head, int body) {
            mImage = image;
            mHead = head;
            mBody = body;
        }

        public int getImage() {
            return mImage;
        }

        public int getHead() {
            return mHead;
        }

        public int getBody() {
            return mBody;
        }
    }

}
