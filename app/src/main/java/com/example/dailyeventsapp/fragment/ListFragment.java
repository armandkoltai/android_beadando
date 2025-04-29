package com.example.dailyeventsapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyeventsapp.R;
import com.example.dailyeventsapp.RecyclerViewInterface;
import com.example.dailyeventsapp.adapters.EventAdapter;
import com.example.dailyeventsapp.api.WikipediaApiService;
import com.example.dailyeventsapp.api.WikipediaResponseModel;
import com.example.dailyeventsapp.dto.EventModel;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListFragment extends Fragment implements RecyclerViewInterface {

    private ArrayList<EventModel> eventList;
    private RecyclerView recyclerView;
    private WikipediaApiService wikipediaApiService;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize RecyclerView, TextView, and Event list
        recyclerView = view.findViewById(R.id.eventRecyclerView);
        TextView textView = view.findViewById(R.id.textView2);
        eventList = new ArrayList<>();

        // Initialize Retrofit and API service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/api/rest_v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        wikipediaApiService = retrofit.create(WikipediaApiService.class);

        // Fetch events for a specific date
        int selectedMonth = getArguments() != null ? getArguments().getInt("MONTH", 1) : 2;
        int selectedDay = getArguments() != null ? getArguments().getInt("DAY", 1) : 2;

        // Set TextView text to show in "MONTH. DAY." format
        String monthName = getMonthFormat(selectedMonth);
        textView.setText(monthName + ". " + selectedDay + ".");

        fetchEvents(selectedMonth, selectedDay);

        // Initialize and set the adapter
        EventAdapter adapter = new EventAdapter(getContext(), eventList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void fetchEvents(int month, int day) {
        Call<WikipediaResponseModel> call = wikipediaApiService.getEventsForDate(month, day);

        call.enqueue(new Callback<WikipediaResponseModel>() {
            @Override
            public void onResponse(Call<WikipediaResponseModel> call, Response<WikipediaResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    eventList.clear();
                    for (WikipediaResponseModel.Event event : response.body().getEvents()) {
                        String title = event.getText();
                        String date = String.valueOf(event.getYear());
                        String location = event.getPages() != null && !event.getPages().isEmpty() ? event.getPages().get(0).getTitle() : "Unknown";
                        String description = event.getText();
                        String extract = null;
                        if (event.getPages() != null && !event.getPages().isEmpty()) {
                            WikipediaResponseModel.Page page = event.getPages().get(0);
                            if (page.getExtract() != null) {
                                extract = page.getExtract();
                            }
                        }
                        String sourceLink = event.getPages() != null && !event.getPages().isEmpty()
                                ? "https://en.wikipedia.org/wiki/" + event.getPages().get(0).getTitle().replace(" ", "_")
                                : "";

                        String imageUrl = null;
                        if (event.getPages() != null && !event.getPages().isEmpty()) {
                            WikipediaResponseModel.Page page = event.getPages().get(0);
                            if (page.getThumbnail() != null) {
                                imageUrl = page.getThumbnail().getSource();
                            }
                        }

                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            EventModel eventModel = new EventModel(title, date, location, description, sourceLink, imageUrl, extract);
                            eventList.add(eventModel);
                        }
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<WikipediaResponseModel> call, Throwable t) {
                // Handle failure (e.g., show a toast message)
            }
        });
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

    @Override
    public void onItemClick(int position) {
        // When an item is clicked, pass data to DetailFragment
        DetailFragment detailFragment = new DetailFragment();

        Bundle bundle = new Bundle();
        String imageUrl = eventList.get(position).getImageUrl();
        bundle.putString("IMAGE_URL", imageUrl);
        bundle.putString("TITLE", eventList.get(position).getTitle());
        bundle.putString("YEAR", eventList.get(position).getDate());
        bundle.putString("LOCATION", eventList.get(position).getLocation());
        bundle.putString("DESCRIPTION", eventList.get(position).getDescription());
        bundle.putString("LINK", eventList.get(position).getSourceLink());
        bundle.putString("EXTRACT", eventList.get(position).getExtract());

        detailFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}
