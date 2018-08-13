package com.example.zhoushicheng.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.Utils.ClassUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private ListView mActivitiesList;
    private List<Class> activities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPermission();

        List<Class> excludeList = new ArrayList<Class>();
        excludeList.add(this.getClass());
        activities = ClassUtil.getActivitiesClass(this, getPackageName(), excludeList);

        mActivitiesList = (ListView) findViewById(R.id.lv_activities_list);
        mActivitiesList.setAdapter(new ActivitiesAdapter());

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url("http://www.aaa.com")
                .get()
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("zzz", "e = " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("zzz", "response = " + response.toString() + ", " + response.body().string());
            }
        });

    }

    private static final int PERMISSIONS_REQUEST = 1;
    private static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    public void myPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS,
                    PERMISSIONS_REQUEST
            );
        }
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
                    Intent intent = new Intent(MainActivity.this, activities.get(position));
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

    class ViewHolder {
        TextView tvItemActivityName;
    }

}








