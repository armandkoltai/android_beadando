package com.example.dailyeventsapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventDao {

    @Insert
    void insertEvent(EventEntity eventEntity);

    @Query("SELECT * FROM events WHERE title = :title LIMIT 1")
    EventEntity getEventByTitle(String title);
    @Query("SELECT * FROM events")
    List<EventEntity> getAllEvents();
}
