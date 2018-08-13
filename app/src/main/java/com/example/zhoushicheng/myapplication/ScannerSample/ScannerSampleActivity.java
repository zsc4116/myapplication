package com.example.zhoushicheng.myapplication.ScannerSample;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhoushicheng.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

public class ScannerSampleActivity extends AppCompatActivity {

    ImageView imgQRCode;
    TextView tvQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_sample);

        imgQRCode = (ImageView) findViewById(R.id.img_result);
        tvQRCode = (TextView) findViewById(R.id.tv_result);

        findViewById(R.id.btn_scanner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<PermissionItem> permissionItems = new ArrayList<>();
                permissionItems.add(new PermissionItem(Manifest.permission.CAMERA, "camera", R.mipmap.ic_launcher));
                HiPermission.create(ScannerSampleActivity.this)
                        .permissions(permissionItems)
                        .checkMutiPermission(new PermissionCallback() {
                            @Override
                            public void onClose() {
                                Log.e("zzz", "onClose");
                            }

                            @Override
                            public void onFinish() {
                                Log.e("zzz", "onFinish");
                                Intent intent = new Intent(ScannerSampleActivity.this, ScannerActivity.class);
                                startActivityForResult(intent, 1000);

                            }

                            @Override
                            public void onDeny(String permisson, int position) {
                                Log.e("zzz", "onDeny");
                            }

                            @Override
                            public void onGuarantee(String permisson, int position) {
                                Log.e("zzz", "onGuarantee");
                            }
                        });
            }
        });

        findViewById(R.id.btn_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "会说话的腔棘鱼";
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(ScannerSampleActivity.this, "请先输入需要生成二维码的内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bitmap bitmap = ZXingUtils.createQRImage(content, imgQRCode.getWidth(), imgQRCode.getHeight());
                imgQRCode.setImageBitmap(bitmap);
                tvQRCode.setText(content);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1000:
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    final String qrCode = bundle.getString(("SCANNER_RESULT"));
                    Log.e("zzz", "qrCode = " + qrCode);
                }
                break;
        }
    }
}
