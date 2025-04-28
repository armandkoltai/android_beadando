package com.example.dailyeventsapp.dto;

public class EventModel {

    private String title;
    private String date;
    private String location;
    private String description;
    private String sourceLink;
    private String extract;
    private String imageUrl;  // Kép URL

    public EventModel(String title, String date, String location, String description, String sourceLink, String imageUrl, String extract) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.description = description;
        this.sourceLink = sourceLink;
        this.imageUrl = imageUrl;  // Kép URL inicializálása
        this.extract = extract;

    }

    // Getters
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
        return imageUrl;  // Kép URL getter
    }

    public String getExtract() { return extract; }
}
