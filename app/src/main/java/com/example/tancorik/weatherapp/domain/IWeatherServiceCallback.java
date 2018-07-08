package com.example.tancorik.weatherapp.domain;

import com.example.tancorik.weatherapp.presentation.model.WeatherInfo;

public interface IWeatherServiceCallback {
    void onSuccess(WeatherInfo weatherInfo);
    void onError(Throwable error);
}
