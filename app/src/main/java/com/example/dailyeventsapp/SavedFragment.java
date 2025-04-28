package com.example.dailyeventsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private TextView emptyView; // Ha nincs esemény, megjelenítjük

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Itt majd lehetne egy TextView az "üres lista" kijelzésére, de most egyszerűbb
        database = AppDatabase.getInstance(getContext());

        fetchSavedEvents();
    }

    private void fetchSavedEvents() {
        new Thread(() -> {
            List<EventEntity> savedEvents = database.eventDao().getAllEvents();
            ArrayList<EventModel> tempList = new ArrayList<>();

            for (EventEntity eventEntity : savedEvents) {
                tempList.add(new EventModel(
                        eventEntity.getTitle(),
                        eventEntity.getDate(),
                        eventEntity.getLocation(),
                        eventEntity.getDescription(),
                        eventEntity.getSourceLink(),
                        eventEntity.getImageUrl(),
                        eventEntity.getExtract()
                ));
            }

            System.out.println("Lekérdezett események száma: " + tempList.size());

            requireActivity().runOnUiThread(() -> {
                if (tempList.isEmpty()) {
                    // Ha nincs adat, jelezzük a felhasználónak
                    // Később ide rakhatunk egy üzenetet vagy egy "Üres lista" nézetet
                    recyclerView.setVisibility(View.GONE);
                    // Például egy üres TextView-ot jeleníthetnénk meg
                    // (most kihagyva, mert XML-ben sincs előkészítve)
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    eventList.clear();
                    eventList.addAll(tempList);

                    eventAdapter = new EventAdapter(getContext(), eventList, position -> {
                        // Eseményre kattintás kezelése (ha kell)
                    });
                    recyclerView.setAdapter(eventAdapter);
                }
            });
        }).start();
    }
}
