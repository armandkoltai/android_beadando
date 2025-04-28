package com.example.dailyeventsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyeventsapp.adapters.EventAdapter;
import com.example.dailyeventsapp.dto.EventModel;
import com.example.dailyeventsapp.AppDatabase;
import com.example.dailyeventsapp.EventEntity;

import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private ArrayList<EventModel> eventList = new ArrayList<>();  // Explicitly use ArrayList
    private AppDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize database
        database = AppDatabase.getInstance(getContext());

        // Fetch saved events from the database
        new Thread(() -> {
            List<EventEntity> savedEvents = database.eventDao().getAllEvents();
            for (EventEntity eventEntity : savedEvents) {
                eventList.add(new EventModel(
                        eventEntity.getTitle(),
                        eventEntity.getDate(),
                        eventEntity.getLocation(),
                        eventEntity.getDescription(),
                        eventEntity.getSourceLink(),
                        eventEntity.getImageUrl(),
                        eventEntity.getExtract()));
            }
            getActivity().runOnUiThread(() -> {
                eventAdapter = new EventAdapter(getContext(), eventList, position -> {
                    // Handle item click here
                });
                recyclerView.setAdapter(eventAdapter);
            });
        }).start();
    }
}
