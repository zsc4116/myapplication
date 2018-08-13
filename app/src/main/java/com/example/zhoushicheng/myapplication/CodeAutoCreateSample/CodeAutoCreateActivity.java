package com.example.zhoushicheng.myapplication.CodeAutoCreateSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.great_annotations.ZSCAnnotation;
import com.example.zhoushicheng.myapplication.R;

import java.util.HashMap;

//@GreatGenerator(
//        packageName = "com.example.zhoushicheng.myapplication.CodeAutoCreateSample",
//        template = Template.class
//)
@ZSCAnnotation(
        name = "VeryGoodRequest",
        text = "indeed very good!"
)
public class CodeAutoCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_auto_create);

        VeryGoodRequest$tuogusa request = new VeryGoodRequest$tuogusa();
        HashMap<String, String> map = request.getParams();
        Log.e("zzz", map.get("abc"));

    }
}
