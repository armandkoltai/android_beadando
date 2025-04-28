package com.example.dailyeventsapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dailyeventsapp.adapters.EventAdapter;
import com.example.dailyeventsapp.dto.EventModel;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private ArrayList<EventModel> eventModels = new ArrayList<>();

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // Find the RecyclerView by its ID
        recyclerView = view.findViewById(R.id.eventRecyclerView);

        // Set the LayoutManager for RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the adapter and set it to the RecyclerView
        eventAdapter = new EventAdapter(getContext(), eventModels, position -> {
            // Handle click events (if any)
        });
        recyclerView.setAdapter(eventAdapter);

        // Optionally, you can populate eventModels with data here or from an API

        return view;
    }

    // You can populate the eventModels array with some sample data for testing
    private void loadSampleData() {
        eventModels.add(new EventModel("Event 1", "APR 01", "New York", "Sample Description", "https://example.com"));
        eventModels.add(new EventModel("Event 2", "APR 02", "Los Angeles", "Sample Description", "https://example.com"));
        eventAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Load some sample data when the fragment starts
        loadSampleData();
    }
}
