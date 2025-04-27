package com.example.dailyeventsapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailFragment extends Fragment {

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
            String title = args.getString("TITLE");
            String year = args.getString("YEAR");
            String location = args.getString("LOCATION");
            String description = args.getString("DESCRIPTION");
            String link = args.getString("LINK");
            int imageResId = args.getInt("IMAGE");


            // Ezek a TextView-ok a fragment_detail.xml-ben lesznek
            ImageView eventImageView = view.findViewById(R.id.eventImageView);
            TextView titleTextView = view.findViewById(R.id.titleTextView);
            TextView dateTextView = view.findViewById(R.id.dateTextView);
            TextView locationTextView = view.findViewById(R.id.locationTextView);
            TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);
        //    TextView linkTextView = view.findViewById(R.id.linkTextView);


            eventImageView.setImageResource(imageResId);
            titleTextView.setText(title);
            dateTextView.setText(year);
            locationTextView.setText(location);
           descriptionTextView.setText(description);
        //    linkTextView.setText(link);
        }
    }
}
