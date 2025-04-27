package com.example.dailyeventsapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private MaterialButton submitButton;

    public HomeFragment() {

    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initDatePicker();
        submitButton = view.findViewById(R.id.submitAppCompatButton);
        dateButton = view.findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        dateButton.setOnClickListener(v -> openDatePicker());
        submitButton.setOnClickListener(v -> {
            String selectedDate = dateButton.getText().toString();
            Toast.makeText(requireContext(), "Selected date: " + selectedDate, Toast.LENGTH_SHORT).show();
        });


        return view;
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            month += 1;
            String date = makeDateString(dayOfMonth, month);
            dateButton.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);


        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(requireContext(), style, dateSetListener, year, month, day);


        DatePicker datePicker = datePickerDialog.getDatePicker();
        int yearId = getResources().getIdentifier("year", "id", "android");
        View yearView = datePicker.findViewById(yearId);
        if (yearView != null) {
            yearView.setVisibility(View.GONE);
        }
    }

    private String makeDateString(int day, int month) {
        return getMonthFormat(month) + " " + String.format("%02d", day);
    }

    private String getMonthFormat(int month) {
        switch (month) {
            case 1: return "JAN";
            case 2: return "FEB";
            case 3: return "MAR";
            case 4: return "APR";
            case 5: return "MAY";
            case 6: return "JUN";
            case 7: return "JUL";
            case 8: return "AUG";
            case 9: return "SEP";
            case 10: return "OCT";
            case 11: return "NOV";
            case 12: return "DEC";
            default: return "JAN";
        }
    }

    private void openDatePicker() {
        datePickerDialog.show();
    }
}
