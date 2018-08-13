package com.example.zhoushicheng.myapplication.ScannerSample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {

    private ScannerView scannerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (scannerView == null) {
            scannerView = new ScannerView(this);
        }
        scannerView.setAutoFocus(true);
        scannerView.setResultHandler(this);
        setContentView(scannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (scannerView != null) {
            scannerView.startCamera();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (scannerView != null) {
            scannerView.stopCameraPreview();
            scannerView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent(this, ScannerSampleActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putString("SCANNER_RESULT", result.getContents());
        Log.e("zzz", "result.getContents() = " + result.getContents());
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

}
