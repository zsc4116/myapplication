package com.example.zhoushicheng.myapplication.XRecyclerViewSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class XRecyclerViewSampleActivity extends AppCompatActivity {

    XRecyclerView recyclerView;
    Button btnRefresh;

    MyAdapter adapter;
    int count;

    private List<MyData> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xrecycler_view_sample);

        btnRefresh = (Button) findViewById(R.id.btn_refresh);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new MyAdapter();
        recyclerView = (XRecyclerView) findViewById(R.id.xrecyclerview);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        produceData(true);

        View header = LayoutInflater.from(this).inflate(R.layout.header_xrecyclerview, (ViewGroup) findViewById(android.R.id.content), false);
        recyclerView.addHeaderView(header);


        recyclerView
                .getDefaultRefreshHeaderView() // get default refresh header view
                .setRefreshTimeVisible(true);
        recyclerView.getDefaultFootView().setLoadingHint("正在加载");


        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //refresh data here
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        produceData(true);
//                        recyclerView.refreshComplete();
//                    }
//                }, 3000);
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

            }

            @Override
            public void onLoadMore() {
                // load more data here
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        produceData(false);
//                        recyclerView.loadMoreComplete();
//                    }
//                }, 3000);
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        btnRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recyclerView.refresh();
            }
        });
    }

    private void produceData(boolean isReFresh) {
        if (isReFresh) {
            for (int i = 0; i < 20; i++) {
                MyData data = new MyData("title : " + System.currentTimeMillis(), "content : " + Math.random() * 100);
                list.add(data);
            }
        } else {
            for (int i = count; i < count + 20; i++) {
                MyData data = new MyData("title : " + System.currentTimeMillis(), "content : " + Math.random() * 100);
                list.add(data);
            }
        }

        count += 20;
        adapter.notifyDataSetChanged();
    }


    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(XRecyclerViewSampleActivity.this)
                    .inflate(R.layout.item_xrecyclerview, parent, false);
            MyHolder holder = new MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.tvTitle.setText(list.get(position).getTitle());
            holder.tvContent.setText(list.get(position).getContent());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvContent;

        public MyHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }

    class MyData {
        private String title;
        private String content;

        public MyData(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }
    }
}
