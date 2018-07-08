package com.example.tancorik.weatherapp.presentation.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

import com.example.tancorik.weatherapp.R;

public class EnterCityDialog extends DialogFragment {

    private IEnterCityNameCallback mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (IEnterCityNameCallback) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.enter_city_layout, null);
        builder.setView(view)
                .setPositiveButton(R.string.ok_button_text, (dialog, which) -> {
                    EditText editText = view.findViewById(R.id.city_name_edit_text);
                    mCallback.onCityNameEnter(editText.getText().toString());
                })
                .setNegativeButton(R.string.cancel_button_text, (dialog, which) -> {
                });
        return builder.create();
    }

    public interface IEnterCityNameCallback {
        void onCityNameEnter(String cityName);
    }

}
