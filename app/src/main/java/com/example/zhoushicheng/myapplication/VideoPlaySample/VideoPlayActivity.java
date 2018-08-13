package com.example.zhoushicheng.myapplication.VideoPlaySample;

import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;

import com.example.zhoushicheng.myapplication.R;

import java.io.IOException;

public class VideoPlayActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {
    TextureView showView;
    Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        showView = (TextureView) findViewById(R.id.show_view);
        showView.setSurfaceTextureListener(this);

    }

    @SuppressLint("NewApi")
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//        int cameras = Camera.getNumberOfCameras();
        mCamera =Camera.open();
        try {
            mCamera.setPreviewTexture(surface);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
