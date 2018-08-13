package com.example.zhoushicheng.myapplication.SharedElementSample.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhoushicheng.myapplication.R;
import com.example.zhoushicheng.myapplication.SharedElementSample.adapter.MyGridAdapter;
import com.example.zhoushicheng.myapplication.SharedElementSample.adapter.MyGridViewHolder;
import com.example.zhoushicheng.myapplication.SharedElementSample.adapter.MyViewOnClickListener;

import java.util.ArrayList;


public class GridFragment extends Fragment {
    private ArrayList<Pair<Integer, Integer>> mData;
    private RecyclerView recyclerView = null;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcl_list);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();

        recyclerView.setAdapter(new MyGridAdapter(mData, mListener));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 一行两个
    }


    // 初始化数据
    private void initData() {
        mData = new ArrayList<>();

        mData.add(Pair.create(1, R.drawable.railway));
        mData.add(Pair.create(2, R.drawable.rain));
        mData.add(Pair.create(3, R.drawable.ship));
        mData.add(Pair.create(4, R.drawable.sun));
        mData.add(Pair.create(5, R.drawable.sun_cloudy));
        mData.add(Pair.create(6, R.drawable.thunder));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private MyViewOnClickListener mListener = new MyViewOnClickListener() {
        @Override
        public void onClickedView(MyGridViewHolder holder, int position) {
            DetailFragment detailFragment = DetailFragment.newInstance(position);

            //这里才是转进变换的核心代码！！！
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                detailFragment.setSharedElementEnterTransition(new DetailTransition());
                setExitTransition(new Fade());
                detailFragment.setEnterTransition(new Fade());
                detailFragment.setSharedElementReturnTransition(new DetailTransition());
            }

            getActivity().getSupportFragmentManager().beginTransaction()
                    .addSharedElement(holder.getImageView(), "abc")
                    .replace(R.id.main_cl_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        }
    };

    //需要创建转进类 DetailTransition 继承自 TransitionSet！！！
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public class DetailTransition extends TransitionSet {
        public DetailTransition() {
            init();
        }

        // 允许资源文件使用
        public DetailTransition(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            //设置转进变换的方式是：各类变换一起展示！！！
            setOrdering(ORDERING_TOGETHER);
            //添加具体的变换类，用于转进变换！！！
            addTransition(new ChangeBounds()).
                    addTransition(new ChangeTransform()).
                    addTransition(new ChangeImageTransform());
        }
    }


}
