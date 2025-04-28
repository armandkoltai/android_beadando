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
import com.example.dailyeventsapp.RecyclerViewInterface;

import java.util.ArrayList;

public class EventListFragment extends Fragment implements RecyclerViewInterface {

    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private ArrayList<EventModel> eventModels = new ArrayList<>();

    public EventListFragment() {
        // kötelező üres konstruktor
    }

    public static EventListFragment newInstance() {
        return new EventListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = view.findViewById(R.id.eventRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new EventAdapter(requireContext(), eventModels, this);
        recyclerView.setAdapter(adapter);

        // Példának dummy adatok
        loadDummyEvents();

        return view;
    }

    private void loadDummyEvents() {
        eventModels.add(new EventModel("Event 1", "APR 20", "Location 1", "Description 1", "https://example.com"));
        eventModels.add(new EventModel("Event 2", "APR 21", "Location 2", "Description 2", "https://example.com"));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        // Itt kezeled majd az itemre kattintást
    }
}
