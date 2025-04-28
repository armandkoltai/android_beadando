package com.example.dailyeventsapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import androidx.room.Room;

public class DetailFragment extends Fragment {

    private AppDatabase db;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Itt fogadjuk az átadott adatokat
        Bundle args = getArguments();
        if (args != null) {
            String imageUrl = args.getString("IMAGE_URL");
            String title = args.getString("TITLE");
            String year = args.getString("YEAR");
            String location = args.getString("LOCATION");
            String description = args.getString("DESCRIPTION");
            String link = args.getString("LINK");
            int imageResId = args.getInt("IMAGE");
            String extract = args.getString("EXTRACT");

            // Kép megjelenítése Glide használatával
            ImageView eventImageView = view.findViewById(R.id.eventImageView);
            if (imageUrl != null) {
                Glide.with(getContext())
                        .load(imageUrl)
                        .into(eventImageView);
            } else {
                eventImageView.setImageResource(R.drawable.placeholder);  // Alapértelmezett kép
            }

            // A Fragmenthez tartozó nézetek keresése
            TextView titleTextView = view.findViewById(R.id.titleTextView);
            TextView dateTextView = view.findViewById(R.id.dateTextView);
            TextView locationTextView = view.findViewById(R.id.locationTextView);
            TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);
            TextView linkTextView = view.findViewById(R.id.linkTextView);

            // Adatok beállítása
            titleTextView.setText(title);
            dateTextView.setText(year);
            locationTextView.setText(location);
            descriptionTextView.setText(extract);
            linkTextView.setText(link);

            // Inicializáljuk a Room adatbázist
            db = AppDatabase.getInstance(getContext());

            // Mentés gomb kezelés
            MaterialButton saveButton = view.findViewById(R.id.saveMaterialButton);
            saveButton.setOnClickListener(v -> {
                // EventEntity létrehozása
                EventEntity eventEntity = new EventEntity(
                        title, year, location, description, link, imageUrl, extract
                );
                saveEventToDatabase(eventEntity);
            });

            // Vissza gomb kezelése
            MaterialButton backButton = view.findViewById(R.id.backButton);
            backButton.setOnClickListener(v -> {
                // Visszatérés a ListFragment-re
                getParentFragmentManager().popBackStack();
            });
        }
    }

    // Esemény mentése a Room adatbázisba
    private void saveEventToDatabase(EventEntity eventEntity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.eventDao().insertEvent(eventEntity);
            }
        }).start();
    }
}
