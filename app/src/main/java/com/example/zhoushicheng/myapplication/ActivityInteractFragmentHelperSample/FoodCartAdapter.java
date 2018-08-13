package com.example.zhoushicheng.myapplication.ActivityInteractFragmentHelperSample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

import java.util.List;

/**
 * Created by zhoushicheng on 2018/6/22.
 */

public class FoodCartAdapter extends RecyclerView.Adapter<FoodCartAdapter.FoodHolder> {

    private Context context;
    private List<FoodModule> data;

    public FoodCartAdapter(Context context, @NonNull List<FoodModule> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public FoodHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        FoodHolder holder = new FoodHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FoodHolder holder, final int position) {
        holder.tvName.setText(data.get(position).getName());
        holder.tvCount.setText(String.valueOf(data.get(position).getCount()));

        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = data.get(position).getCount() + 1;
                data.get(position).setCount(count);
                notifyItemChanged(position);
                if (onItemListener != null) {
                    onItemListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class FoodHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvCount;
        private ImageView imgPlus;

        public FoodHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_item_food_name);
            tvCount = (TextView) itemView.findViewById(R.id.tv_item_food_count);
            imgPlus = (ImageView) itemView.findViewById(R.id.img_item_food_plus);
        }
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }

    private OnItemListener onItemListener;

    public void setOnItemListener(OnItemListener listener) {
        onItemListener = listener;
    }
}
