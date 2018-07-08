package com.example.tancorik.weatherapp.presentation.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.tancorik.weatherapp.R;
import com.example.tancorik.weatherapp.presentation.model.WeatherInfo;
import com.example.tancorik.weatherapp.presentation.presenter.MainScreenPresenter;
import com.example.tancorik.weatherapp.presentation.view.IMainScreenView;

public class MainActivity extends AppCompatActivity implements IMainScreenView, EnterCityDialog.IEnterCityNameCallback {

    private Button mSelectCityButton;
    private Button mByLocationButton;

    private MainScreenPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        mPresenter = MainScreenPresenter.getInstance();

        mSelectCityButton.setOnClickListener(v -> {
            new EnterCityDialog().show(getSupportFragmentManager(), "TAG");
        });
        mByLocationButton.setOnClickListener(v -> mPresenter.getWeatherByLocation());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.detachView();
    }

    @Override
    public void showWeather(WeatherInfo weatherInfo) {
        enableButtons();
        showFragment(WeatherInfoFragment.createInstance(), WeatherInfoFragment.TAG);
    }

    @Override
    public void showProgress() {
        disableButtons();
        showFragment(ProgressFragment.createInstance(), ProgressFragment.TAG);
    }

    @Override
    public void showStub() {
        enableButtons();
        showFragment(WeatherStubFragment.createInstance(), WeatherStubFragment.TAG);
    }

    @Override
    public void showError() {
        new ErrorDialog().show(getSupportFragmentManager(), ErrorDialog.TAG);
    }

    @Override
    public void onCityNameEnter(String cityName) {
        mPresenter.getWeatherByCity(cityName);
    }

    private void initViews() {
        mSelectCityButton = findViewById(R.id.select_city_button);
        mByLocationButton = findViewById(R.id.by_location_button);
    }

    private void showFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.weather_info_fragment, fragment, tag)
                .commit();
    }

    private void disableButtons() {
        setButtonsEnabled(false);
    }

    private void enableButtons() {
        setButtonsEnabled(true);
    }

    private void setButtonsEnabled(boolean state) {
        mSelectCityButton.setEnabled(state);
        mByLocationButton.setEnabled(state);
    }
}
