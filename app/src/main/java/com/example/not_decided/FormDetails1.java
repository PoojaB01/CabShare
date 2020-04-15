package com.example.not_decided;

public class FormDetails1 {
    String id;
    String name;
    String email;
    String date;
    String time;
    String flight;
    public FormDetails1(){

    }

    public FormDetails1(String id,String name, String email, String time, String date, String flightnumber) {
        this.name = name;
        this.email = email;
        this.time = time;
        this.id=id;
        this.date = date;
        this.flight = flight;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
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
