package com.example.zhoushicheng.myapplication.TextMixImageSample;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.R;
import com.example.zhoushicheng.myapplication.Utils.FileUtil;

import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

public class TextMixImageActivity extends AppCompatActivity {

    PictureAndTextEditorView ptView;
    Button button;
    TextView tvPrint;
    Button btnPrint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_mix_image);

        ptView = (PictureAndTextEditorView) findViewById(R.id.ptv_input);
        button = (Button) findViewById(R.id.btn_insert);
        tvPrint = (TextView) findViewById(R.id.tv_print);
        btnPrint = (Button) findViewById(R.id.btn_print);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = ptView.getmContentList();
                for (String content : list) {
                    Log.e("zzz", "content = " + content);
                }
            }
        });

        List<PermissionItem> permissionItems = new ArrayList<>();
        permissionItems.add(new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, "READ EXTERNAL STORAGE", R.mipmap.ic_launcher));
        HiPermission.create(TextMixImageActivity.this)
                .permissions(permissionItems)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                        Log.e("zzz", "onClose");
                    }

                    @Override
                    public void onFinish() {
                        Log.e("zzz", "onFinish");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 0) {
            //            ContentResolver resolver = getContentResolver();
            //            // 获得图片的uri
            //            Uri originalUri = data.getData();
            //            Bitmap bitmap = null;
            //            try {
            //                Bitmap originalBitmap = BitmapFactory.decodeStream(resolver.openInputStream(originalUri));
            //                bitmap = getResizedBitmap(originalBitmap, 600, 600);
            //                // 将原始图片的bitmap转换为文件
            //                // 上传该文件并获取url
            //                picList.add(bitmap);
            //                insertPic();
            //            } catch (FileNotFoundException e) {
            //                e.printStackTrace();
            //            }

            Uri selectedImage = data.getData();

            String imageurl = FileUtil.getRealPathFromUri(this, selectedImage);

            Log.e("ant", "path = " + imageurl);

            ptView.insertBitmap(imageurl);
        }
    }

}
