package com.example.dailyeventsapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

public class SavedDetailFragment extends Fragment {

    private AppDatabase db;

    public SavedDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_detail, container, false);
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
            db = Room.databaseBuilder(getContext(), AppDatabase.class, "event_database")
                    .fallbackToDestructiveMigration()
                    .build();

            // Remove (törlés) gomb kezelése
            MaterialButton removeButton = view.findViewById(R.id.removeMaterialButton);
            removeButton.setOnClickListener(v -> {
                // Az esemény törlése az adatbázisból a cím alapján
                removeEventFromDatabase(title); // Törlés cím alapján
            });

            // Vissza gomb kezelése
            MaterialButton backButton = view.findViewById(R.id.backButton);
            backButton.setOnClickListener(v -> {
                // Visszatérés az előző fragmenthez
                getParentFragmentManager().popBackStack();
            });
        }
    }

    // Esemény törlése az adatbázisból
    private void removeEventFromDatabase(String title) {
        Log.d("Database", "removeEventFromDatabase triggered");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Az esemény eltávolítása az adatbázisból
                    Log.d("Database", "Attempting to delete event with title: " + title);

                    // Ellenőrizzük, hogy van-e ilyen esemény
                    EventEntity event = db.eventDao().getEventByTitle(title);
                    if (event != null) {
                        Log.d("Database", "Event found: " + event.getTitle());

                        // Törlés végrehajtása
                        db.eventDao().deleteEventByTitle(title);
                        requireActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "Event successfully deleted!", Toast.LENGTH_SHORT).show()
                        );
                        Log.d("Database", "Event successfully deleted: " + title);
                    } else {
                        Log.d("Database", "No event found with title: " + title);
                    }

                    // Frissítjük az UI-t, miután a törlés megtörtént
                    requireActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.savedfragment, new SavedFragment())  // Helyes ID-t használunk
                            .addToBackStack(null)
                            .commit();

                } catch (Exception e) {
                    // Hibakezelés
                    Log.e("Database", "Error occurred while deleting event: " + e.getMessage(), e);
                }
            }
        }).start(); // A szál indítása
    }
}
