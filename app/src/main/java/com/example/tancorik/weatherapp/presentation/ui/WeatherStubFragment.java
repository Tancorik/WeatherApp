package com.example.tancorik.weatherapp.presentation.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tancorik.weatherapp.R;

public class WeatherStubFragment extends Fragment {

    public static final String TAG = "WeatherStubFragment";

    public static WeatherStubFragment createInstance() {
        return new WeatherStubFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_stub_layout, container, false);
    }

}
