package com.example.zhoushicheng.myapplication.TagGroupSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.zhoushicheng.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;
import me.next.tagview.TagCloudView;

public class TagGroupSampleActivity extends AppCompatActivity implements
        TagCloudView.OnTagClickListener {

    TagCloudView tagCloudView1;
    CheckTextGroupView checkTextGroupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_group_sample);


        final TagGroup tagGroup = (TagGroup) findViewById(R.id.tag_group);
        tagGroup.setTags(new String[]{"abc", "今天是个大晴天", "Google keyboard", "How to delete a tag"});


        TagGroup tagGroup2 = (TagGroup) findViewById(R.id.tag_group_2);
        tagGroup2.setTags(new String[]{"abc", "今天是个大晴天", "Google keyboard", "How to delete a tag"});

        tagGroup2.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                Log.e("zzz", tag);
            }
        });


        List<String> tags = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tags.add("标签" + i);
        }

        tagCloudView1 = (TagCloudView) findViewById(R.id.tag_cloud_view_1);
        tagCloudView1.setTags(tags);
        tagCloudView1.setOnTagClickListener(this);
        tagCloudView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "TagView onClick",
                        Toast.LENGTH_SHORT).show();
            }
        });

        checkTextGroupView= (CheckTextGroupView) findViewById(R.id.checkTextGroupView);
        checkTextGroupView.updateCheckTexts(initList());
    }

    public List<CheckTextGroupView.CheckText> initList(){

        List<CheckTextGroupView.CheckText> list=new ArrayList<CheckTextGroupView.CheckText>();
        for(int index=0;index<10;index++){
            CheckTextGroupView.CheckText checkText=new CheckTextGroupView.CheckText();
            checkText.setText("中文"+index);
            list.add(checkText);
        }

        return list;
    }

    @Override
    public void onTagClick(int position) {
        if (position == -1) {
            Toast.makeText(getApplicationContext(), "点击末尾文字",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "点击 position : " + position,
                    Toast.LENGTH_SHORT).show();
            tagCloudView1.getTag(position);
        }
    }
}
