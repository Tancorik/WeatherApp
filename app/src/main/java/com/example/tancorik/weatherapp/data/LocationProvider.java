package com.example.tancorik.weatherapp.data;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.tancorik.weatherapp.data.exeption.NoFineLocationPermissionException;
import com.example.tancorik.weatherapp.domain.ILocationProvider;
import com.example.tancorik.weatherapp.domain.ILocationProviderCallback;

public class LocationProvider implements ILocationProvider {

    public static final String LOG_TAG = "LocationLogs";

    private Context mContext;
    private ILocationProviderCallback mCallback;
    private LocationManager mLocationManager;

    public static LocationProvider getInstance() {
        return LocationProviderHolder.INSTANCE;
    }

    public void init(Context context) {
        mContext = context;
    }

    @Override
    public void getLocation(ILocationProviderCallback callback) {
        mCallback = callback;
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        if ((ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                 || !checkProvider()) {
            Exception permissionException = new NoFineLocationPermissionException();
            mCallback.onError(permissionException);
            Log.e(LOG_TAG, permissionException.getMessage());
        } else {
            LocationListener listener = new Listener();
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 50, listener);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 50, listener);
        }
    }

    private boolean checkProvider() {
        return (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }



    private static class LocationProviderHolder {
        private static final LocationProvider INSTANCE = new LocationProvider();
    }

    public class Listener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            mCallback.onSuccess((float)location.getLatitude(), (float)location.getLongitude());
            Log.i(LOG_TAG, "Широта: " + location.getLatitude() + ", долгота: " + location.getLongitude());
            mLocationManager.removeUpdates(this);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
