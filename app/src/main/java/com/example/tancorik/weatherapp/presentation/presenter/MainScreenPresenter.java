package com.example.tancorik.weatherapp.presentation.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.tancorik.weatherapp.data.LocationProvider;
import com.example.tancorik.weatherapp.data.RemoteWeatherService;
import com.example.tancorik.weatherapp.data.exeption.NoFineLocationPermissionException;
import com.example.tancorik.weatherapp.domain.ILocationProviderCallback;
import com.example.tancorik.weatherapp.domain.IWeatherServiceCallback;
import com.example.tancorik.weatherapp.presentation.model.WeatherInfo;
import com.example.tancorik.weatherapp.presentation.view.IMainScreenView;

public class MainScreenPresenter {

    private static final String WEATHER_SERVICE_ERROR_MESSAGE = "Город не найден или проблемы с сетью";
    private static final String EMPTY_CITY_ERROR_MESSAGE = "Введите название города";

    private IMainScreenView mView;
    private String mWeatherCriterion;
    private WeatherInfo mCurrentWeatherInfo;
    private boolean mInProgress;
    private String mErrorMessage;

    /**
     * Dagger юзать нельзя, поэтому использует OnDemandHolder-синглтон для презентера
     *
     * @return  экземпляр {@link MainScreenPresenter}
     */
    public static MainScreenPresenter getInstance() {
        return PresenterHolder.INSTANCE;
    }

    public void attachView(@NonNull IMainScreenView view) {
        mView = view;
        if (mInProgress) {
            mView.showProgress();
            return;
        }
        showInfoOrStub();
    }

    public void detachView() {
        mView = null;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    @Nullable
    public WeatherInfo getCurrentWeatherInfo() {
        return mCurrentWeatherInfo;
    }

    public void getWeatherByCity(String cityName) {
        if (cityName.isEmpty()) {
            mErrorMessage = EMPTY_CITY_ERROR_MESSAGE;
            mView.showError();
            return;
        }
        mWeatherCriterion = WeatherInfo.WEATHER_BY_CITY_CRITERION;
        mView.showProgress();
        mInProgress = true;
        RemoteWeatherService.getInstance().getWeatherInfoByCity(cityName, new IWeatherServiceCallback() {
            @Override
            public void onSuccess(WeatherInfo weatherInfo) {
                mCurrentWeatherInfo = weatherInfo;
                mCurrentWeatherInfo.setCriterionText(mWeatherCriterion);
                if (mView != null) {
                    mView.showWeather(weatherInfo);
                    mInProgress = false;
                }
            }

            @Override
            public void onError(Throwable error) {
                if (mView != null) {
                    showInfoOrStub();
                    mErrorMessage = WEATHER_SERVICE_ERROR_MESSAGE;
                    mView.showError();
                    mInProgress = false;
                }
            }
        });
    }

    public void getWeatherByLocation() {
        mWeatherCriterion = WeatherInfo.WEATHER_BY_LOCATION_CRITERION;
        mView.showProgress();
        mInProgress = true;
        LocationProvider.getInstance().getLocation(new ILocationProviderCallback() {
            @Override
            public void onSuccess(float latitude, float longitude) {
                RemoteWeatherService.getInstance().getWeatherInfoByLocation(new WeatherInfo.Coordinates(latitude, longitude), new IWeatherServiceCallback() {
                    @Override
                    public void onSuccess(WeatherInfo weatherInfo) {
                        mCurrentWeatherInfo = weatherInfo;
                        mCurrentWeatherInfo.setCriterionText(mWeatherCriterion);
                        if (mView != null) {
                            mView.showWeather(weatherInfo);
                            mInProgress = false;
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        if (mView != null) {
                            showInfoOrStub();
                            mInProgress = false;
                        }
                    }
                });
            }

            @Override
            public void onError(Throwable error) {
                if (mView != null) {
                    mInProgress = false;
                    if (!(error instanceof NoFineLocationPermissionException)) {
                        mErrorMessage = "Системная ошибка";
                    }
                    else {
                        mErrorMessage = error.getMessage();
                    }
                    mView.showError();
                }
            }
        });
    }

    private void showInfoOrStub() {
        if (mCurrentWeatherInfo != null) {
            mView.showWeather(mCurrentWeatherInfo);
        }
        else {
            mView.showStub();
        }
    }

    private static class PresenterHolder {
        static final MainScreenPresenter INSTANCE = new MainScreenPresenter();
    }
}
