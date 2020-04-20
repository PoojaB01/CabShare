package com.example.not_decided;

public class FormDetails1 {
    String id;
    String date;
    String time;
    String flight;
    public FormDetails1(){

    }

    public FormDetails1(String id, String time, String date, String flight) {

        this.time = time;
        this.id = id;
        this.date = date;
        this.flight = flight;
    }
    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getFlight() {
        return flight;
    }
    public String getId() {
        return id;
    }
}
