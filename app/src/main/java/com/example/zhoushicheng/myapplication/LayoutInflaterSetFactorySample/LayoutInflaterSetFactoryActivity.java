package com.example.zhoushicheng.myapplication.LayoutInflaterSetFactorySample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;

public class LayoutInflaterSetFactoryActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                Log.e("zzz", "name = " + name);

                AppCompatDelegate delegate = getDelegate();
                View view = delegate.createView(parent, name, context, attrs);

                if (view != null && view instanceof TextView) {
                    view = new Button(context, attrs);
                }
                return view;
            }
        });


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout_inflater_set_factory);

        button = (Button) findViewById(R.id.layoutinflater_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("" + button.getClass());
            }
        });

    }
}
