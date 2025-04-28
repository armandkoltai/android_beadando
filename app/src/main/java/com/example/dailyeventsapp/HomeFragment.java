package com.example.dailyeventsapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.dailyeventsapp.adapters.EventAdapter;
import com.example.dailyeventsapp.dto.EventModel;
import com.google.android.material.button.MaterialButton;


import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private MaterialButton submitButton;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private ArrayList<EventModel> eventModels = new ArrayList<>();

    private WikipediaApiService wikipediaApiService;

    private int selectedMonth;
    private int selectedDay;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initDatePicker();
        submitButton = view.findViewById(R.id.submitMaterialButton);
        dateButton = view.findViewById(R.id.datePickerButton);
        //recyclerView = view.findViewById(R.id.recyclerView);

        dateButton.setText(getTodaysDate());

        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventAdapter = new EventAdapter(getContext(), eventModels, position -> {});
        //recyclerView.setAdapter(eventAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/api/rest_v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        wikipediaApiService = retrofit.create(WikipediaApiService.class);

        dateButton.setOnClickListener(v -> openDatePicker());

        submitButton.setOnClickListener(v -> {
            // Pass the selected month and day to the ListFragment
            ListFragment listFragment = new ListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("MONTH", selectedMonth);
            bundle.putInt("DAY", selectedDay);
            listFragment.setArguments(bundle);

            // Switch fragment
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, listFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }




    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        selectedMonth = month;
        selectedDay = day;
        return makeDateString(day, month);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            month += 1;
            selectedMonth = month;
            selectedDay = dayOfMonth;
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

    private void fetchEvents(int month, int day) {
        Call<WikipediaResponseModel> call = wikipediaApiService.getEventsForDate(month, day);

        call.enqueue(new Callback<WikipediaResponseModel>() {
            @Override
            public void onResponse(Call<WikipediaResponseModel> call, Response<WikipediaResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    eventModels.clear();
                    for (WikipediaResponseModel.Event event : response.body().getEvents()) {
                        String title = event.getText();
                        String date = getMonthFormat(month) + " " + String.format("%02d", day);
                        String location = event.getPages() != null && !event.getPages().isEmpty() ? event.getPages().get(0).getTitle() : "Unknown";
                        String description = event.getText();
                        String sourceLink = event.getPages() != null && !event.getPages().isEmpty() ? "https://en.wikipedia.org/wiki/" + event.getPages().get(0).getTitle().replace(" ", "_") : "";
                        String thumbnail = event.getText();
                        String extract = null;
                        if (event.getPages() != null && !event.getPages().isEmpty()) {
                            WikipediaResponseModel.Page page = event.getPages().get(0);
                            if (page.getExtract() != null) {
                                extract = page.getExtract();  // ÚJ: Extract mező kimentése
                            }
                        }
                        EventModel eventModel = new EventModel(title, date, location, description, sourceLink, thumbnail, extract);
                        eventModels.add(eventModel);
                    }
                    eventAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Hiba az események betöltésekor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WikipediaResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), "Hálózati hiba: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
