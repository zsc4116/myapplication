package com.example.zhoushicheng.myapplication.SnapHelperSample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

import java.util.List;

/**
 * Created by ZSC on 2018/8/7.
 */
public class SnapAdapter extends RecyclerView.Adapter<SnapAdapter.SnapHolder> {

    private Context context;
    private List<String> data;

    public SnapAdapter(Context context, @NonNull List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public SnapHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        SnapHolder holder = new SnapHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SnapHolder holder, int position) {
        holder.tvSnap.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class SnapHolder extends RecyclerView.ViewHolder {
        TextView tvSnap;

        public SnapHolder(View itemView) {
            super(itemView);
            tvSnap = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }
}