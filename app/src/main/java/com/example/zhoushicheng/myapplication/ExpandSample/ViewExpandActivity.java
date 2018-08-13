package com.example.zhoushicheng.myapplication.ExpandSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

import java.util.ArrayList;

public class ViewExpandActivity extends AppCompatActivity {

    private ArrayList<Bean> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expand);

        final TextView textView = (TextView) findViewById(R.id.text_expand);
        final TextView textViewShow = (TextView) findViewById(R.id.text_expand_show);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_expand);

        String content = "原文地址:https://github.com/yueyueniao2012/ExpandTabView 参考地址:http://blog.csdn.net/minimicall/article/details/39484493 我们在常用的电商";

        textView.setText(content);
        textViewShow.setText(content);
        textViewShow.setVisibility(View.GONE);

        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setMaxWidth(90);
        textView.setSingleLine(true);
        textView.setOnClickListener(new View.OnClickListener() {
            Boolean flag = true;

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (flag) {

                    textView.setVisibility(View.GONE);
                    textViewShow.setVisibility(View.VISIBLE);
//                    flag = false;
//                    textView.setEllipsize(null);// 展开
//                    textView.setSingleLine(flag);
//                    ViewGroup.LayoutParams lp = textView.getLayoutParams();
//                    lp.height = 120;
//                    textView.setLayoutParams(lp);
//                    textView.postInvalidate();
                } else {
                    flag = true;
                    textView.setVisibility(View.VISIBLE);
                    textViewShow.setVisibility(View.GONE);
//                    textView.setEllipsize(TextUtils.TruncateAt.END); // 收缩
//                    textView.setSingleLine(flag);
//                    ViewGroup.LayoutParams lp = textView.getLayoutParams();
//                    lp.height =40;
//                    textView.setLayoutParams(lp);
//                    textView.postInvalidate();

                }
            }
        });

        textViewShow.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                textView.setVisibility(View.VISIBLE);
                textViewShow.setVisibility(View.GONE);
            }
        });



        for (int i = 0; i < 20; i++) {
            datas.add(new Bean("content == " + i, "title : " + i, false));
        }

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.setLayoutManager(manager);


    }

    class Bean {
        public Bean(String content, String title, boolean isShow) {
            this.content = content;
            this.title = title;
            this.isShow = isShow;
        }

        String content;
        String title;
        boolean isShow;

        public String getContent() {
            return content;
        }

        public String getTitle() {
            return title;
        }

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
        }
    }


    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ViewExpandActivity.this).inflate(R.layout.item_expand, null);
            MyHolder holder = new MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, final int position) {
            holder.tvShow.setText(datas.get(position).getTitle());
            holder.tvHidden.setText(datas.get(position).getContent());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (datas.get(position).isShow()) {
                        datas.get(position).setShow(false);
                        notifyItemChanged(position);
                    } else {
                        datas.get(position).setShow(true);
                        notifyItemChanged(position);
                    }
                }
            });

            if (datas.get(position).isShow()) {
                holder.tvHidden.setVisibility(View.VISIBLE);
            } else {
                holder.tvHidden.setVisibility(View.GONE);
            }

        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tvShow;
        TextView tvHidden;

        public MyHolder(View itemView) {
            super(itemView);
            tvShow = (TextView) itemView.findViewById(R.id.text_show);
            tvHidden = (TextView) itemView.findViewById(R.id.text_hidden);
        }
    }
}


