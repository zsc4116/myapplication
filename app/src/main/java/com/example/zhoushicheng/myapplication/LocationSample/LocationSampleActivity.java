package com.example.zhoushicheng.myapplication.LocationSample;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.zhoushicheng.myapplication.EventBusSample.EventMessageLocation;
import com.example.zhoushicheng.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

public class LocationSampleActivity extends AppCompatActivity {

    private String[] mNeedPermissionsList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private TextView tvLocation;

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            GPSService.GPSBinder binder = (GPSService.GPSBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_sample);

        EventBus.getDefault().register(this);

        tvLocation = (TextView) findViewById(R.id.tv_location);

        List<PermissionItem> permissionItems = new ArrayList<>();
        permissionItems.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, "FINE LOCATION", R.mipmap.ic_launcher));
        permissionItems.add(new PermissionItem(Manifest.permission.ACCESS_COARSE_LOCATION, "COARSE LOCATION", R.mipmap.ic_launcher));
        HiPermission.create(LocationSampleActivity.this)
                .permissions(permissionItems)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                        Log.e("zzz", "onClose");
                    }

                    @Override
                    public void onFinish() {
                        Log.e("zzz", "onFinish");
                        //                        new LocationClient().getLocation(LocationSampleActivity.this);

                        Intent serviceIntent = new Intent(LocationSampleActivity.this, GPSService.class);
                        bindService(serviceIntent, conn, BIND_AUTO_CREATE);

                        //                        //2.1获取保存的经纬度坐标
                        //                        String longitude = sp.getString("longitude", "");
                        //                        String latitude = sp.getString("latitude", "");
                        //
                        //                        // 获取经纬度
                        //                        String locationStr = "纬度：" + latitude + "\n"
                        //                                + "经度：" + longitude;
                        //                        LogUtils.e("经纬度是：", locationStr);
                        //                        System.out.println("GPSService经纬度是：" + locationStr);


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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetEventBusMessageLocation(EventMessageLocation eventMsg) {
        Log.e("zzz", "latitude = " + eventMsg.getLatitude() + ", longtigutd = " + eventMsg.getLongitude());
        tvLocation.setText("latitude = " + eventMsg.getLatitude() + ", longtigutd = " + eventMsg.getLongitude());
        unbindService(conn);
    }
}
