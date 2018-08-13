package com.example.zhoushicheng.myapplication.SnapHelperSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.example.zhoushicheng.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SnapHelperActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<String> data;

    SnapAdapter adapter;
    LinearSnapHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_helper);

        processData();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_snap);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new SnapAdapter(this, data);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);

    }

    private void processData() {
        data = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            data.add("position : " + i);
        }
    }

}
