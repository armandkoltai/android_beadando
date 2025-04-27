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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<EventModel> eventList;
    private RecyclerView recyclerView;

    private String mParam1;
    private String mParam2;

    public SavedFragment() {
        // Required empty public constructor
    }

    public static SavedFragment newInstance(String param1, String param2) {
        SavedFragment fragment = new SavedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.mRecyclerView);
        eventList = new ArrayList<>();
        setUpEventModels();

        EventAdapter adapter = new EventAdapter(getContext(), eventList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setUpEventModels() {
        eventList.add(new EventModel("Event1", "1934", "Italy", "Something happened", "wikipedia.com"));
        eventList.add(new EventModel("Event2", "1234", "Denmark", "Something happened", "wikipedia.com"));
        eventList.add(new EventModel("Event3", "1784", "Germany", "Something happened", "wikipedia.com"));
        eventList.add(new EventModel("Event4", "1824", "Ottoman Empire", "Something happened", "wikipedia.com"));
        eventList.add(new EventModel("Event5", "1533", "Hungary", "Something happened", "wikipedia.com"));
        eventList.add(new EventModel("Event6", "1263", "USA", "Something happened", "wikipedia.com"));
        eventList.add(new EventModel("Event7", "1933", "Australia", "Something happened", "wikipedia.com"));
        eventList.add(new EventModel("Event8", "1423", "Belgium", "Something happened", "wikipedia.com"));
        eventList.add(new EventModel("Event9", "1235", "France", "Something happened", "wikipedia.com"));
        eventList.add(new EventModel("Event10", "1113", "England", "Something happened", "wikipedia.com"));
    }
}
