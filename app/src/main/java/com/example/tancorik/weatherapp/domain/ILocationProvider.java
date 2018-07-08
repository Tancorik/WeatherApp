package com.example.tancorik.weatherapp.domain;

public interface ILocationProvider {
    void getLocation(ILocationProviderCallback callback);
}
