package com.example.dailyeventsapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyeventsapp.R;
import com.example.dailyeventsapp.adapters.EventAdapter;
import com.example.dailyeventsapp.db.AppDatabase;
import com.example.dailyeventsapp.dto.EventModel;
import com.example.dailyeventsapp.db.EventEntity;

import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private ArrayList<EventModel> eventList = new ArrayList<>();
    private TextView placerholderTextView;
    private AppDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("SavedFragment", "onCreateView called");
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("SavedFragment", "onViewCreated called");
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        placerholderTextView = view.findViewById(R.id.textView5);

        database = AppDatabase.getInstance(getContext());

        loadSavedEvents();
    }

    private void loadSavedEvents() {
        Log.d("SavedFragment", "loadSavedEvents() called");

        new Thread(() -> {
            Log.d("SavedFragment", "Thread started for loading events");

            List<EventEntity> savedEvents = database.eventDao().getAllEvents();
            eventList.clear();

            if (savedEvents != null) {
                for (EventEntity eventEntity : savedEvents) {
                    Log.d("SavedFragment", "Loaded EventEntity: " + eventEntity.getTitle());

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
            } else {
                Log.e("SavedFragment", "savedEvents is NULL!");
            }

            requireActivity().runOnUiThread(() -> {
                Log.d("SavedFragment", "Updating UI on main thread with " + eventList.size() + " events");

                if (eventList.isEmpty()) {
                    recyclerView.setVisibility(View.INVISIBLE);
                    placerholderTextView.setText("Nothing saved for now.\n Start exploring! \uD83D\uDE0A");
                }

                if (!eventList.isEmpty()) {
                    placerholderTextView.setVisibility(View.GONE);
                    if (eventAdapter == null) {
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

                            SavedDetailFragment savedDetailFragment = new SavedDetailFragment();
                            savedDetailFragment.setArguments(bundle);

                            requireActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.savedfragment, savedDetailFragment)
                                    .addToBackStack(null)
                                    .commit();
                        });
                        recyclerView.setAdapter(eventAdapter);
                    } else {
                        eventAdapter.notifyDataSetChanged();
                    }
                }
            });
        }).start();
    }


    public void refreshEventList() {
        Log.d("SavedFragment", "refreshEventList() called");
        loadSavedEvents();
    }
}
