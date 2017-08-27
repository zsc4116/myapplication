package com.example.zhoushicheng.myapplication.ListViewRefreshSample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

import java.util.ArrayList;

/**
 * Created by zhoushicheng on 2017/8/10.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> strings = new ArrayList<>();

    public MyAdapter(Context context){
        this.context = context;
        for(int i = 0; i < 20; i++){
            strings.add("" + i);
        }
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            view = View.inflate(context, R.layout.item_layout, null);
            viewHolder.textView = (TextView) view.findViewById(R.id.tv_item);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(strings.get(i));
        return view;
    }

    class ViewHolder{
        TextView textView;
    }
}
