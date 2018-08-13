package com.example.zhoushicheng.myapplication.ImageUploadAnnotationSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.zhoushicheng.myapplication.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ImageUploadAnnotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload_annotation);

        List<UpLoadItem> items = new ArrayList<>();
        UpLoadItem item1 = new UpLoadItem();
        item1.setPicName("picFirst");
        item1.setPicPath("/data/picFirst.jpeg");
        items.add(item1);
        UpLoadItem item2 = new UpLoadItem();
        item2.setPicName("picSecond");
        item2.setPicPath("/data/picSecond.jpeg");
        items.add(item2);
        UpLoadItem item3 = new UpLoadItem();
        item3.setPicName("picThird");
        item3.setPicPath("/data/picThird.jpeg");
        items.add(item3);


        UpLoadRequest request = new UpLoadRequest();

        processUploadItem(items, request);

        Log.e("annotation", "request = " + request);
    }


    private void processUploadItem(List<UpLoadItem> items, UpLoadRequest request) {
        for (UpLoadItem item : items) {
            Field[] fields = UpLoadRequest.class.getDeclaredFields();
            if (fields != null && fields.length != 0) {
                for (Field field : fields) {
                    if (field.isAnnotationPresent(ArgumentStrump.class)) {
                        field.setAccessible(true);
                        try {
                            ArgumentStrump annotaion = field.getAnnotation(ArgumentStrump.class);
                            if (annotaion.argument().equals(item.getPicName())) {
                                field.set(request, item.getPicPath());
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }
}
