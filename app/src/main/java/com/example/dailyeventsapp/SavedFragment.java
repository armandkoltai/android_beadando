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
    private ArrayList<EventModel> eventList = new ArrayList<>();
    private AppDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        database = AppDatabase.getInstance(getContext());

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
                        eventEntity.getExtract()
                ));
            }
            requireActivity().runOnUiThread(() -> {
                eventAdapter = new EventAdapter(getContext(), eventList, position -> {
                    EventModel selectedEvent = eventList.get(position);

                    Bundle bundle = new Bundle();
                    bundle.putString("TITLE", selectedEvent.getTitle());
                    bundle.putString("YEAR", selectedEvent.getDate());
                    bundle.putString("LOCATION", selectedEvent.getLocation());
                    bundle.putString("DESCRIPTION", selectedEvent.getDescription());
                    bundle.putString("LINK", selectedEvent.getSourceLink());
                    bundle.putString("IMAGE_URL", selectedEvent.getImageUrl());
                    bundle.putString("EXTRACT", selectedEvent.getExtract());

                    // Most a SavedDetailFragment-et nyitjuk meg
                    SavedDetailFragment savedDetailFragment = new SavedDetailFragment();
                    savedDetailFragment.setArguments(bundle);

                    requireActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.savedfragment, savedDetailFragment)  // A fragment_container helyére lecseréljük az új fragmentet
                            .addToBackStack(null)  // Lehetővé teszi, hogy visszalépjünk a mentett események listájára
                            .commit();
                });
                recyclerView.setAdapter(eventAdapter);
            });
        }).start();
    }
}
