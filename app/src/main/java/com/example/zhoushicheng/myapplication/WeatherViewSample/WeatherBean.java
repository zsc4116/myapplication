package com.example.zhoushicheng.myapplication.WeatherViewSample;

/**
 * Created by zhoushicheng on 2017/8/26.
 */

public class WeatherBean {
    public static final String SUN = "晴";
    public static final String CLOUDY = "阴";
    public static final String SNOW = "雪";
    public static final String RAIN = "雨";
    public static final String SUN_CLOUD = "多云";
    public static final String THUNDER = "雷";

    public String weather;
    public int temperature;
    public String temperatureStr;
    public String time;

    public WeatherBean(String weather, int temperature, String time){
        this.weather = weather;
        this.temperature = temperature;
        this.time = time;
        this.temperatureStr = temperature + "";
    }

    public WeatherBean(String weather, int temperature, String temperatureStr, String time){
        this.weather = weather;
        this.temperature = temperature;
        this.time = time;
        this.temperatureStr = temperatureStr;
    }

    public static String[] getAllWeathers(){
        String[] str = {SUN, RAIN, CLOUDY, SUN_CLOUD, SNOW, THUNDER};
        return str;
    }
}
