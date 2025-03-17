package com.example.dailyeventsapp.network;

import com.example.dailyeventsapp.model.WikipediaResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WikipediaApiService {
    @GET("feed/onthisday/events/{month}/{day}")
    Call<WikipediaResponse> getEvents(@Path("month") int month, @Path("day") int day);
}