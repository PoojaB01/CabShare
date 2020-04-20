package com.example.not_decided;

public class FormDetails4 {
    String id;
    String date;
    String time;
    String other;
    public FormDetails4(){

    }

    public FormDetails4(String id, String time, String date, String other) {

        this.time = time;
        this.id = id;
        this.date = date;
        this.other = other;
    }
    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getOther() {
        return other;
    }
    public String getId() {
        return id;
    }
}