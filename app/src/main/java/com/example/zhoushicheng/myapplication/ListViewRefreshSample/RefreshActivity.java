package com.example.zhoushicheng.myapplication.ListViewRefreshSample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhoushicheng.myapplication.R;

public class RefreshActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Button button;

    private static final int MY_PERMISSION_REQUEST_CALL_PHONE = 1;

    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new MyAdapter(this));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light, android.R.color.holo_blue_bright);
        swipeRefreshLayout.setDistanceToTriggerSync(400);
        swipeRefreshLayout.setProgressBackgroundColor(android.R.color.black);
        swipeRefreshLayout.setSize(100);


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(RefreshActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RefreshActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE}, MY_PERMISSION_REQUEST_CALL_PHONE);
                } else {
                    callPhone();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 停止刷新
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 5000);

    }


    @SuppressLint("MissingPermission")
    public void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:10086");
        intent.setData(data);
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSION_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(RefreshActivity.this, "" + grantResults[1], Toast.LENGTH_SHORT).show();
                }
                callPhone();
            }

            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
