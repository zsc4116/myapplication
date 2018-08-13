package com.example.zhoushicheng.myapplication.LocationSample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by zhoushicheng on 2018/6/4.
 */

public class LocationClient {
    private LocationManager locationManager;
    private String locationProvider;
    private Context context;

    public void getLocation(Context context) {
        this.context = context;
        Log.e("zzz", "getLocation");
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            Log.e("zzz", "如果是Network");
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            Log.e("zzz", "如果是GPS");
            locationProvider = LocationManager.GPS_PROVIDER;
        } else {
            Log.e("zzz", "没有可用的位置提供器");
            return;
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("zzz", "没有权限");
            return;
        }

        Location location = locationManager.getLastKnownLocation(locationProvider);

        if (location != null) {
            //不为空,显示地理位置经纬度
            Log.e("zzz", "location != null");
        }

        //监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
    }

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {
            Log.e("zzz", "onStatusChanged");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e("zzz", "onProviderEnabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e("zzz", "onProviderDisabled");
        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            location = getBestLocation(locationManager);
            if (location == null) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.e("zzz", "没有权限");
                    return;
                }
                locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
            }
            showLocation(location);
        }
    };

    private void showLocation(Location location) {
        Log.e("zzz", "定位成功------->  location------>" + "经度为：" + location.getLatitude() + "\n纬度为" + location.getAltitude());
    }

    private Location getBestLocation(LocationManager locationManager) {
        Location result = null;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("zzz", "没有权限");
            return null;
        }
        if (locationManager != null) {
            result = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (result != null) {
                return result;
            } else {
                result = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                return result;
            }
        }
        return result;
    }

}
