package com.example.tancorik.weatherapp.application;

import android.app.Application;

import com.example.tancorik.weatherapp.data.LocationProvider;

public class WeatherApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        LocationProvider.getInstance().init(this);
    }
}
