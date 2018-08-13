package com.example.zhoushicheng.myapplication.ZxingSample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;
import com.example.zhoushicheng.myapplication.ZxingSample.zxing.app.CaptureActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

public class ZxingSampleActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView codeImage;
    private TextView codeContentText;
    private final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing_sample);

        codeImage = (ImageView) findViewById(R.id.zing_show_code_bitmap_view);

        Button goScanButton = (Button) findViewById(R.id.zxing_go_scan_button);
        goScanButton.setOnClickListener(this);

        Button createCodeButton = (Button) findViewById(R.id.zxing_create_code_button);
        createCodeButton.setOnClickListener(this);

        codeContentText = (TextView) findViewById(R.id.zxing_show_code_content_text);

        handler.removeCallbacksAndMessages(null);
       
    }

    private static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zxing_go_scan_button:
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.zxing_create_code_button:
                Bitmap bitmap = createQRCode(600, 600, "史诗级大帅哥");
                if (bitmap != null) {
                    codeImage.setImageBitmap(bitmap);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    String content = data.getStringExtra("SCAN_RESULT");
                    if (content != null && !TextUtils.isEmpty(content)) {
                        codeContentText.setText(content);
                    }
                } else if (resultCode == 200) {
                    Bitmap bitmap = (Bitmap) data.getParcelableExtra("QR_CODE");
                    if (bitmap != null) {
                        codeImage.setImageBitmap(bitmap);
                    }
                }
                break;
        }
    }

    private Bitmap createQRCode(int width, int height, String source) {
        try {
            if (TextUtils.isEmpty(source)) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(source, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            // sheng chen de er wei ma
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

}
