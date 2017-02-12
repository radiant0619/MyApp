package com.radiant.acsl.myworkapp.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.radiant.acsl.myworkapp.R;

import java.util.Calendar;


/**
 * Created by rsvra on 08/02/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mon = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, mon, day);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        TextView txt =
                (TextView) getActivity().findViewById(R.id.dateValue);
        txt.setText(new StringBuilder().append(java.lang.String.format("%02d", day)).append("-")
                .append(java.lang.String.format("%02d", month + 1))
                .append("-").append(year));
    }
}
