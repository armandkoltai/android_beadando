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
 */
public class ListFragment extends Fragment implements RecyclerViewInterface {

    private ArrayList<EventModel> eventList;
    private RecyclerView recyclerView;

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

        // Initialize RecyclerView and Event list
        recyclerView = view.findViewById(R.id.eventRecyclerView);
        eventList = new ArrayList<>();
        setUpEventModels();

        // Initialize and set the adapter
        EventAdapter adapter = new EventAdapter(getContext(), eventList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setUpEventModels() {
        // Add sample data
        eventList.add(new EventModel("Event1", "1934", "Italy", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus lacinia odio vitae vestibulum vestibulum.", "wikipedia.com"));
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

    @Override
    public void onItemClick(int position) {
        // When an item is clicked, pass data to DetailFragment
        DetailFragment detailFragment = new DetailFragment();

        // Passing data through Bundle
        Bundle bundle = new Bundle();
        bundle.putInt("IMAGE", R.drawable.placeholder);
        bundle.putString("TITLE", eventList.get(position).getTitle());
        bundle.putString("YEAR", eventList.get(position).getDate());
        bundle.putString("LOCATION", eventList.get(position).getLocation());
        bundle.putString("DESCRIPTION", eventList.get(position).getDescription());
        bundle.putString("LINK", eventList.get(position).getSourceLink());

        detailFragment.setArguments(bundle);

        // Replace fragment with the new DetailFragment
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}
