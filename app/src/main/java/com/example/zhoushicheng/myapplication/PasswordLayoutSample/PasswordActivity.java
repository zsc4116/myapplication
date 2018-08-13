package com.example.zhoushicheng.myapplication.PasswordLayoutSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.zhoushicheng.myapplication.R;
import com.jungly.gridpasswordview.GridPasswordView;

public class PasswordActivity extends AppCompatActivity {

    GridPasswordView gridPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        gridPasswordView = (GridPasswordView) findViewById(R.id.pswView);

        gridPasswordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                Toast.makeText(PasswordActivity.this, psw, Toast.LENGTH_LONG).show();
            }
        });
    }
}
