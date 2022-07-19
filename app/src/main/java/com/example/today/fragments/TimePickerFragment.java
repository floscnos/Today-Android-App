package com.example.today.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {
    // Null impossible and overwrite onCreate
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            // new TimePickerDiaglog
            return new TimePickerDialog(
                    getActivity(),
                    (TimePickerDialog.OnTimeSetListener) getActivity(),
                    hour,
                    minute, true
            );
        }
    }
