package com.example.tancorik.weatherapp.presentation.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.tancorik.weatherapp.R;
import com.example.tancorik.weatherapp.presentation.presenter.MainScreenPresenter;

public class ErrorDialog extends DialogFragment {

    public static final String TAG = "ErrorDialogTag";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String errorMessage = MainScreenPresenter.getInstance().getErrorMessage();
        if (errorMessage == null) {
            errorMessage = "Ошибка";
        }
        builder.setMessage(errorMessage)
                .setPositiveButton(R.string.ok_button_text, (dialog, which) -> {

                });
        return builder.create();
    }
}
