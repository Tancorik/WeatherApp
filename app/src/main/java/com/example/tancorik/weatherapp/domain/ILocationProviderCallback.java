package com.example.tancorik.weatherapp.domain;

public interface ILocationProviderCallback {
    void onSuccess(float latitude, float longitude);
    void onError(Throwable error);
}
