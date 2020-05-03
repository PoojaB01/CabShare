package com.example.not_decided;

public class Trip_information {
    String destination;
    String source;
    String date;
    String time;
    String email;
    String phone;
    String id;


    public Trip_information(String destination,String source,String time, String date,String email,String phone,String id) {
        this.destination=destination;
        this.source=source;
        this.time = time;
        this.id = id;
        this.date = date;

        this.email=email;
        this.phone=phone;
    }
    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getSource() {
        return source;
    }
    public String getId() {
        return id;
    }
    public String getDestination() {
        return destination;
    }



    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }

}