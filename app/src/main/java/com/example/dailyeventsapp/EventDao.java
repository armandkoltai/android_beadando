package com.example.dailyeventsapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface EventDao {

    // Manuális INSERT lekérdezés
    @Query("INSERT INTO events (title, date, location, description, sourceLink, imageUrl, extract) VALUES (:title, :date, :location, :description, :sourceLink, :imageUrl, :extract)")
    void insertEventManual(String title, String date, String location, String description, String sourceLink, String imageUrl, String extract);

    @Query("DELETE FROM events WHERE title LIKE :title")
    void deleteEventByTitle(String title);

    @Query("SELECT * FROM events WHERE title = :title LIMIT 1")
    EventEntity getEventByTitle(String title);
    @Query("SELECT * FROM events")
    List<EventEntity> getAllEvents();

}
