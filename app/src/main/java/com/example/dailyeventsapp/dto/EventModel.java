package com.example.dailyeventsapp.dto;

public class EventModel {

    String title;
    String date;

    String location;
    String description;
    String sourceLink;

    public EventModel(String title, String date,String location, String description, String sourceLink) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.description = description;
        this.sourceLink = sourceLink;
    }

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
}
