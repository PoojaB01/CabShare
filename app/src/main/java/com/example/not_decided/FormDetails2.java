package com.example.not_decided;

public class FormDetails2 {
    String id;
    String date;
    String time;
    String train;
    public FormDetails2(){

    }

    public FormDetails2(String id, String time, String date, String train) {

        this.time = time;
        this.id = id;
        this.date = date;
        this.train = train;
    }
    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getTrain() {
        return train;
    }
    public String getId() {
        return id;
    }
}
