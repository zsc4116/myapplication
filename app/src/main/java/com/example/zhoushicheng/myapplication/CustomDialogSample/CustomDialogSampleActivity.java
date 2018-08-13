package com.example.zhoushicheng.myapplication.CustomDialogSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zhoushicheng.myapplication.R;

public class CustomDialogSampleActivity extends AppCompatActivity {
    private Button btnShowDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog_sample);

        btnShowDialog = (Button) findViewById(R.id.btn_show_dialog);
        btnShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
    }


    private void showCustomDialog() {
        Toast.makeText(this, "显示自定义圆角Dialog", Toast.LENGTH_SHORT).show();
        View view = View.inflate(this, R.layout.dialog_custom, null);
        CustomDialog customDialog = new CustomDialog(this, 0, 0, view, R.style.KCornerDialog);
        customDialog.show();
    }
}
