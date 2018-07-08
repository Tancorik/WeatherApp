package com.example.tancorik.weatherapp.presentation.view;

import com.example.tancorik.weatherapp.presentation.model.WeatherInfo;

public interface IMainScreenView {
    void showWeather(WeatherInfo weatherInfo);
    void showProgress();
    void showStub();
    void showError();
}
