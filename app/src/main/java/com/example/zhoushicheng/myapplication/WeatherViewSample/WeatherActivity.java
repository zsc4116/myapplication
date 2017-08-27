package com.example.zhoushicheng.myapplication.WeatherViewSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zhoushicheng.myapplication.R;

import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ArrayList<WeatherBean> beans = new ArrayList<>();
        beans.add(new WeatherBean(WeatherBean.SUN, 20, "6:00"));
        beans.add(new WeatherBean(WeatherBean.SUN, 22, "7:00"));
        beans.add(new WeatherBean(WeatherBean.SUN_CLOUD, 24, "8:00"));
        beans.add(new WeatherBean(WeatherBean.CLOUDY, 25, "9:00"));
        beans.add(new WeatherBean(WeatherBean.CLOUDY, 26, "10:00"));
        beans.add(new WeatherBean(WeatherBean.RAIN, 27, "11:00"));
        beans.add(new WeatherBean(WeatherBean.THUNDER, 26, "12:00"));
        beans.add(new WeatherBean(WeatherBean.RAIN, 25, "13:00"));
        beans.add(new WeatherBean(WeatherBean.RAIN, 26, "14:00"));
        beans.add(new WeatherBean(WeatherBean.RAIN, 27, "15:00"));
        beans.add(new WeatherBean(WeatherBean.SUN, 29, "16:00"));
        beans.add(new WeatherBean(WeatherBean.SUN, 27, "17:00"));
        beans.add(new WeatherBean(WeatherBean.SUN, 26, "18:00"));

        MiuiWeatherView weatherView = (MiuiWeatherView) findViewById(R.id.weather_view);
        weatherView.setData(beans);
    }
}
