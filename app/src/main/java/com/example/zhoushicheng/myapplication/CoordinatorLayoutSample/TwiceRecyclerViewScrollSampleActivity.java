package com.example.zhoushicheng.myapplication.CoordinatorLayoutSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

import java.util.ArrayList;

public class TwiceRecyclerViewScrollSampleActivity extends AppCompatActivity {

    RecyclerView recyclerView1;
    RecyclerView recyclerView2;


    ArrayList<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twice_recycler_view_scroll_sample);

        for (int i = 0; i < 40; i++) {
            datas.add("content : " + i);
        }


        recyclerView1 = (RecyclerView) findViewById(R.id.recycler_1);
        recyclerView2 = (RecyclerView) findViewById(R.id.recycler_2);

        LinearLayoutManager m1 = new LinearLayoutManager(this);
        LinearLayoutManager m2 = new LinearLayoutManager(this);

        recyclerView1.setAdapter(new MyAdapter());
        recyclerView2.setAdapter(new MyAdapter());

        recyclerView1.setLayoutManager(m1);
        recyclerView2.setLayoutManager(m2);


    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(TwiceRecyclerViewScrollSampleActivity.this).inflate(R.layout.item_layout, null);
            MyHolder holder = new MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.textView.setText(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }


}
