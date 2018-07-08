package com.example.tancorik.weatherapp.presentation.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherInfo {

    public static final String WEATHER_BY_CITY_CRITERION = "Город";
    public static final String WEATHER_BY_LOCATION_CRITERION = "По текущему местоположению";
    public static final String WEATHER_UNKNOWN_CRITERION = "Неизвестный критерий";

    private transient String mCriterionText;
    @SerializedName("coord")
    private Coordinates mCoordinates;
    @SerializedName("name")
    private String mCityName;
    @SerializedName("main")
    private MainWeatherParameters mMainWeatherParameters;
    @SerializedName("weather")
    private List<WeatherDescription> mWeatherDescriptionList;
    @SerializedName("dt")
    private long mDate;
    @SerializedName("wind")
    private Wind mWind;

    public Wind getWind() {
        return mWind;
    }

    public void setWind(Wind wind) {
        mWind = wind;
    }

    public String getCriterionText() {
        return mCriterionText;
    }

    public void setCriterionText(String criterionText) {
        mCriterionText = criterionText;
    }

    public Coordinates getCoordinates() {
        return mCoordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        mCoordinates = coordinates;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public MainWeatherParameters getMainWeatherParameters() {
        return mMainWeatherParameters;
    }

    public void setMainWeatherParameters(MainWeatherParameters mainWeatherParameters) {
        mMainWeatherParameters = mainWeatherParameters;
    }

    public List<WeatherDescription> getWeatherDescriptionList() {
        return mWeatherDescriptionList;
    }

    public void setWeatherDescriptionList(List<WeatherDescription> weatherDescriptionList) {
        mWeatherDescriptionList = weatherDescriptionList;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public static final class Coordinates {

        @SerializedName("lat")
        public float mLatitude;
        @SerializedName("lon")
        public float mLongitude;

        public Coordinates(float latitude, float longitude) {
            mLatitude = latitude;
            mLongitude = longitude;
        }
    }

    public static final class MainWeatherParameters {

        @SerializedName("temp")
        public float mTemperature;
        @SerializedName("humidity")
        public int mHumidity;
        @SerializedName("temp_min")
        public float mMinTemperature;
        @SerializedName("temp_max")
        public float mMaxTemperature;
        @SerializedName("pressure")
        public int mPressure;

        public float getMinTemperature() {
            return mMinTemperature;
        }

        public void setMinTemperature(float minTemperature) {
            mMinTemperature = minTemperature;
        }

        public float getMaxTemperature() {
            return mMaxTemperature;
        }

        public void setMaxTemperature(float maxTemperature) {
            mMaxTemperature = maxTemperature;
        }

        public int getPressure() {
            return mPressure;
        }

        public void setPressure(int pressure) {
            mPressure = pressure;
        }

        public float getTemperature() {
            return mTemperature;
        }

        public void setTemperature(float temperature) {
            mTemperature = temperature;
        }

        public int getHumidity() {
            return mHumidity;
        }

        public void setHumidity(int humidity) {
            mHumidity = humidity;
        }
    }

    public static final class WeatherDescription {

        @SerializedName("main")
        public String mMainDesription;
        @SerializedName("description")
        public String mExtendedDescription;

        public String getMainDesription() {
            return mMainDesription;
        }

        public void setMainDesription(String mainDesription) {
            mMainDesription = mainDesription;
        }

        public String getExtendedDescription() {
            return mExtendedDescription;
        }

        public void setExtendedDescription(String extendedDescription) {
            mExtendedDescription = extendedDescription;
        }
    }

    public static final class Wind {

        @SerializedName("speed")
        public float mSpeed;
        @SerializedName("deg")
        public int mOrientation;

        public float getSpeed() {
            return mSpeed;
        }

        public void setSpeed(float speed) {
            mSpeed = speed;
        }

        public int getOrientation() {
            return mOrientation;
        }

        public void setOrientation(int orientation) {
            mOrientation = orientation;
        }
    }
}
