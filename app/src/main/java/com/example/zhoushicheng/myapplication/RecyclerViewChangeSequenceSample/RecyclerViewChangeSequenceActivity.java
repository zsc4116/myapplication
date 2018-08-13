package com.example.zhoushicheng.myapplication.RecyclerViewChangeSequenceSample;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerViewChangeSequenceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    List<String> data;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_change_sequence);

        data = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            data.add(String.valueOf("NO. " + i));
        }

        Button button = (Button) findViewById(R.id.btn_down);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_change);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        adapter = new RecyclerAdapter();
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        //RecyclerView与ItemTouchHelper连接起来
        helper.attachToRecyclerView(recyclerView);


        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.onItemMove(count, count + 1);
            }
        });

    }

    ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
        //判断手指滑动方向
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int drag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipe = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

            return makeMovementFlags(drag, swipe);

        }

        //上下移动Item
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            //判断移动的item和目的位置是不是同种item
            if (viewHolder.getItemViewType() != target.getItemViewType()) {
                return false;
            }
            //            ((MyViewHolder) viewHolder).ivDown.setVisibility(View.GONE);
            adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        //左右滑动item
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            //            ((MyViewHolder) viewHolder).ivDown.setVisibility(View.VISIBLE);
            //            adapter.onItemSwipe(viewHolder.getAdapterPosition());

        }

        //选择一个item
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {

            //            if (viewHolder != null) {
            //                getDefaultUIUtil().onSelected(((MyViewHolder) viewHolder).llitem);
            //
            //            }

        }

        //重绘item，在item下面
        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            //            getDefaultUIUtil().onDraw(c, recyclerView, ((MyViewHolder) viewHolder).llitem, dX, dY, actionState, isCurrentlyActive);

        }

        //重绘item，在item上面
        @Override
        public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            //            getDefaultUIUtil().onDraw(c, recyclerView, ((MyViewHolder) viewHolder).llitem, dX, dY, actionState, isCurrentlyActive);

        }

        //清理视图，回到默认状态
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            //            getDefaultUIUtil().clearView(((MyViewHolder) viewHolder).llitem);
        }
    };

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(RecyclerViewChangeSequenceActivity.this).inflate(R.layout.item_recycler, parent, false);

            return new MyViewHolder(view);
        }

        //判断滑动的方向
        void onItemMove(int from, int to) {
            if (from < to) {
                for (int i = 0; i < to - from; i++) {
                    Collections.swap(data, from + i, from + 1 + i);
                }
                adapter.notifyItemMoved(from, to);
                notifyItemRangeChanged(from, 2);
            }

            if (from > to) {
                for (int i = 0; i < from - to; i++) {
                    Collections.swap(data, from - 1 - i, from - i);
                }
                adapter.notifyItemMoved(from, to);
                notifyItemRangeChanged(to, 2);
            }
        }

        //删除一个item
        void onItemSwipe(int position) {

            data.remove(position);
            adapter.notifyDataSetChanged();

        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            ((MyViewHolder) holder).textView.setText(data.get(position));
            ((MyViewHolder) holder).tvUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position != 0) {
                        onItemMove(position, position - 1);
//                        notifyDataSetChanged();
                    }
                }
            });

            ((MyViewHolder) holder).tvDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position != data.size() - 1) {
                        onItemMove(position, position + 1);
//                        notifyDataSetChanged();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView ivDown;
        public LinearLayout llitem;
        public TextView tvUp;
        public TextView tvDown;


        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            ivDown = (ImageView) itemView.findViewById(R.id.iv_done);
            llitem = (LinearLayout) itemView.findViewById(R.id.ll_item);
            tvUp = (TextView) itemView.findViewById(R.id.tv_up);
            tvDown = (TextView) itemView.findViewById(R.id.tv_down);
        }

    }

}
