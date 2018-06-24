package com.dailytodo.Utility;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import java.util.Calendar;

public class DateTimePicker {


    public static void showDataPickerDialog(Context mContex, DatePickerDialog.OnDateSetListener callBack) {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(mContex, callBack, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }

    public static void showTimePickerDialog(Context mContex, TimePickerDialog.OnTimeSetListener callBack) {
        final Calendar c = Calendar.getInstance();
        // Current Hour

        TimePickerDialog timePickerDialog = new TimePickerDialog(mContex, callBack,
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true);

        timePickerDialog.show();
    }
}