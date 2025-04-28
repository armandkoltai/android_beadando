package com.example.dailyeventsapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class EventEntity {


    @PrimaryKey
    @NonNull
    private String title;
    private String date;
    private String location;
    private String description;
    private String sourceLink;
    private String imageUrl;
    private String extract;

    public EventEntity(String title, String date, String location, String description, String sourceLink, String imageUrl, String extract) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.description = description;
        this.sourceLink = sourceLink;
        this.imageUrl = imageUrl;
        this.extract = extract;
    }

    // Getterek és setterek


    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getExtract() {
        return extract;
    }
}
