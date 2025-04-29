package com.example.dailyeventsapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.dailyeventsapp.R;
import com.example.dailyeventsapp.db.AppDatabase;
import com.example.dailyeventsapp.db.EventEntity;
import com.google.android.material.button.MaterialButton;

public class DetailFragment extends Fragment {

    private AppDatabase db;

    public DetailFragment() {
        // Kötelező üres konstruktor
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

        // Adatok átvétele
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

            // Kép betöltése Glide-dal
            ImageView eventImageView = view.findViewById(R.id.eventImageView);
            if (imageUrl != null) {
                Glide.with(getContext())
                        .load(imageUrl)
                        .into(eventImageView);
            } else {
                eventImageView.setImageResource(R.drawable.placeholder);
            }

            // Nézetek
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

            // Singleton adatbázis inicializálás (nem új build!)
            db = AppDatabase.getInstance(getContext());

            // Mentés gomb kezelése
            MaterialButton saveButton = view.findViewById(R.id.removeMaterialButton);
            saveButton.setOnClickListener(v -> {
                EventEntity eventEntity = new EventEntity(
                        title, year, location, description, link, imageUrl, extract
                );
                saveEventToDatabase(eventEntity);
            });

            // Vissza gomb kezelése
            MaterialButton backButton = view.findViewById(R.id.backButton);
            backButton.setOnClickListener(v -> {
                getParentFragmentManager().popBackStack();
            });
        }
    }

    // Esemény mentése az adatbázisba, duplikáció ellenőrzéssel
    private void saveEventToDatabase(EventEntity eventEntity) {
        new Thread(() -> {
            EventEntity existingEvent = db.eventDao().getEventByTitle(eventEntity.getTitle());
            if (existingEvent == null) {
                // Manuális INSERT használata
                db.eventDao().insertEventManual(
                        eventEntity.getTitle(),
                        eventEntity.getDate(),
                        eventEntity.getLocation(),
                        eventEntity.getDescription(),
                        eventEntity.getSourceLink(),
                        eventEntity.getImageUrl(),
                        eventEntity.getExtract()
                );
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Esemény mentve!", Toast.LENGTH_SHORT).show()
                );
            } else {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Ez az esemény már mentve van!", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }
}
