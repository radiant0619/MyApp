package com.radiant.acsl.myworkapp.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.radiant.acsl.myworkapp.R;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        TextView txt = (TextView) getActivity().findViewById(R.id.dateValue);
        txt.setText(new StringBuilder().append(java.lang.String.format("%02d", day)).append("-")
                .append(java.lang.String.format("%02d", month + 1))
                .append("-").append(year));

    }
}
