package com.example.tancorik.weatherapp.data.exeption;

public class NoFineLocationPermissionException extends Exception {

    private static final String ERROR_MESSAGE = "Нет разрешения на получение местоположения или выключена геолокация";

    @Override
    public String getMessage() {
        return ERROR_MESSAGE;
    }
}
