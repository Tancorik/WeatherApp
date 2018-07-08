package com.example.tancorik.weatherapp.data;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import com.example.tancorik.weatherapp.domain.IWeatherService;
import com.example.tancorik.weatherapp.domain.IWeatherServiceCallback;
import com.example.tancorik.weatherapp.presentation.model.WeatherInfo;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RemoteWeatherService implements IWeatherService {

    private static final String LOG_TAG = "RemoteWeatherServiceLog";

    private final String API_KEY = "fa223b06ae1dc1f4ec4d767d668d7609";
    private static final String BASE_API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String WORKER_THREAD_NAME = "WorkerThread";

    private Handler mWorkerHandler;
    private Handler mMainThreadHandler;

    public static RemoteWeatherService getInstance() {
        return ServiceInstanceHolder.INSTANCE;
    }

    RemoteWeatherService() {
        HandlerThread handlerThread = new HandlerThread(WORKER_THREAD_NAME);
        handlerThread.start();
        mWorkerHandler = new Handler(handlerThread.getLooper());
        mMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void getWeatherInfoByLocation(final WeatherInfo.Coordinates coordinates, final IWeatherServiceCallback callback) {
        mWorkerHandler.post(() -> {
            String requestUrl = createDefaultUrl()
                    .append("&lat=").append(coordinates.mLatitude)
                    .append("&lon=").append(coordinates.mLongitude)
                    .toString();
            handleRequest(requestUrl, callback);
        });
    }

    @Override
    public void getWeatherInfoByCity(final String cityName, final IWeatherServiceCallback callback) {
        mWorkerHandler.post(() -> {
            String requestUrl = createDefaultUrl().append("&q=").append(cityName).toString();
            handleRequest(requestUrl, callback);
        });
    }

    private void handleRequest(String requestUrl, IWeatherServiceCallback callback) {
        try {
            Log.i(LOG_TAG, "Request = " + requestUrl);
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                String responseBody = readResponse(inputStream);
                throw new IOException("Код ответа сервера: " + responseCode + ", тело ответа: " + responseBody);
            }
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            WeatherInfo weatherInfo = new Gson().fromJson(reader, WeatherInfo.class);
            mMainThreadHandler.post(() -> callback.onSuccess(weatherInfo));
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            mMainThreadHandler.post(() -> callback.onError(e));
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            mMainThreadHandler.post(() -> callback.onError(e));
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            mMainThreadHandler.post(() -> callback.onError(e));
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            mMainThreadHandler.post(() -> callback.onError(e));
        }
    }

    private StringBuilder createDefaultUrl() {
        StringBuilder builder = new StringBuilder(BASE_API_URL);
        builder.append("?APPID=").append(API_KEY)
                .append("&units=metric")
                .append("&lang=ru");
        return builder;
    }

    private String readResponse(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[256];
        int len;
        while ((len = inputStream.read(buffer)) != - 1) {
            outputStream.write(buffer, 0, len);
        }
        return outputStream.toString("UTF-8");
    }

    private static class ServiceInstanceHolder {
        private static final RemoteWeatherService INSTANCE = new RemoteWeatherService();
    }
}
