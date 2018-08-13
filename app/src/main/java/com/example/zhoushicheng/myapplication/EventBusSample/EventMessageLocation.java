package com.example.zhoushicheng.myapplication.EventBusSample;

/**
 * Created by zhoushicheng on 2018/6/4.
 */

public class EventMessageLocation extends EventMessageBase implements EventMessageInterface {

    public double latitude;
    public double longitude;

    public EventMessageLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String getInterfaceMessage() {
        return latitude + " : " + longitude + " : interface";
    }
}
