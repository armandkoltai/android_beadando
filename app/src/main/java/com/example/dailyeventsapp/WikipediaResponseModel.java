package com.example.dailyeventsapp;

import java.util.List;

public class WikipediaResponseModel {

    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public static class Event {
        private int year;
        private String text;
        private List<Page> pages;

        public int getYear() {
            return year;
        }

        public String getText() {
            return text;
        }

        public List<Page> getPages() {
            return pages;
        }
    }

    public static class Page {
        private String title;
        private Thumbnail thumbnail;

        public String getTitle() {
            return title;
        }

        public Thumbnail getThumbnail() {
            return thumbnail;
        }
    }

    public static class Thumbnail {
        private String source;

        public String getSource() {
            return source;
        }
    }
}
