package com.example.tancorik.weatherapp.domain;

import com.example.tancorik.weatherapp.presentation.model.WeatherInfo;

public interface IWeatherService {
    void getWeatherInfoByLocation(WeatherInfo.Coordinates coordinates, IWeatherServiceCallback callback);
    void getWeatherInfoByCity(String cityName, IWeatherServiceCallback callback);
}
