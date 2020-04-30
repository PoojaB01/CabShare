package com.example.not_decided.ui;

public class TripDetails {
    String source, destination, date, time;
    Boolean notifications;
    public TripDetails(String source, String destination, String date, String time)
    {
        this.date = date;
        this.time = time;
        this.source = source;
        this.destination = destination;
        this.notifications = true;
    }
}
