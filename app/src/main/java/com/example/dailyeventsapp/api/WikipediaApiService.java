package com.example.dailyeventsapp.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WikipediaApiService {

    @GET("feed/onthisday/events/{month}/{day}")
    Call<WikipediaResponseModel> getEventsForDate(
            @Path("month") int month,
            @Path("day") int day
    );
}
