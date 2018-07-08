package com.example.tancorik.weatherapp.presentation.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tancorik.weatherapp.R;
import com.example.tancorik.weatherapp.presentation.model.WeatherInfo;
import com.example.tancorik.weatherapp.presentation.presenter.MainScreenPresenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherInfoFragment extends Fragment {

    public static final String TAG = "WeatherInfoFragment";

    private MainScreenPresenter mScreenPresenter;
    private TextView mDateView;
    private TextView mCriterionView;
    private TextView mCityNameView;
    private TextView mDescriptionView;
    private TextView mTemperatureView;
    private TextView mMinMaxTemperatureView;
    private TextView mPressureView;
    private TextView mHumidityView;
    private TextView mWindSpeedView;
    private TextView mWindOrientationView;

    public static WeatherInfoFragment createInstance() {
        return new WeatherInfoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScreenPresenter = MainScreenPresenter.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_info_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        WeatherInfo weatherInfo = mScreenPresenter.getCurrentWeatherInfo();
        if (weatherInfo != null) {
            Date date = new Date(weatherInfo.getDate() * 1000);
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
            String dateString = dateFormat.format(date);

            mDateView.setText(dateString);
            mCriterionView.setText(weatherInfo.getCriterionText());
            mCityNameView.setText(weatherInfo.getCityName());
            mDescriptionView.setText(weatherInfo.getWeatherDescriptionList().get(0).getExtendedDescription());
            mTemperatureView.append(weatherInfo.getMainWeatherParameters().getTemperature() + "\u2103");
            mMinMaxTemperatureView.append((int)weatherInfo.getMainWeatherParameters().getMinTemperature() + " - " +
                    (int)weatherInfo.getMainWeatherParameters().getMaxTemperature() + "\u2103");
            mPressureView.append(weatherInfo.getMainWeatherParameters().getPressure() + "мм.р.с");
            mHumidityView.append(weatherInfo.getMainWeatherParameters().getHumidity() + "\u0025");
            mWindSpeedView.append(weatherInfo.getWind().getSpeed() + "мм/с");
            mWindOrientationView.append(weatherInfo.getWind().getOrientation() + "\u00B0");
        }
    }

    private void initViews(View view) {
        mDateView = view.findViewById(R.id.date_view);
        mCriterionView = view.findViewById(R.id.criterion_text_view);
        mCityNameView = view.findViewById(R.id.city_name_view);
        mDescriptionView = view.findViewById(R.id.description_text_view);
        mTemperatureView = view.findViewById(R.id.temperature_text_view);
        mMinMaxTemperatureView = view.findViewById(R.id.min_max_temperature_text_view);
        mPressureView = view.findViewById(R.id.pressure_text_view);
        mHumidityView = view.findViewById(R.id.humidity_text_view);
        mWindSpeedView = view.findViewById(R.id.wind_speed_text_view);
        mWindOrientationView = view.findViewById(R.id.wind_orientation_text_view);
    }
}
