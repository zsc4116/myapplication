package com.example.zhoushicheng.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.Utils.ClassUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mActivitiesList;
    private List<Class> activities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Class> excludeList = new ArrayList<Class>();
        excludeList.add(this.getClass());
        activities = ClassUtil.getActivitiesClass(this, getPackageName(), excludeList);

        mActivitiesList = (ListView) findViewById(R.id.lv_activities_list);
        mActivitiesList.setAdapter(new ActivitiesAdapter());
    }

    class ActivitiesAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (activities == null) {
                return 0;
            } else {
                return activities.size();
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_main_listview, parent, false);
                holder = new ViewHolder();
                holder.tvItemActivityName = (TextView) convertView.findViewById(R.id.tv_item_activity_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String name = activities.get(position).getName();
            name = name.substring(name.lastIndexOf(".") + 1);
            holder.tvItemActivityName.setText(name);
            holder.tvItemActivityName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, activities.get(position)));
                }
            });

            return convertView;
        }
    }

    class ViewHolder {
        TextView tvItemActivityName;
    }
}
