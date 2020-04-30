package com.example.not_decided;

public class User_information {
    String name, phone, hostel, department, email;
    public User_information(String name, String phone, String hostel, String department, String email)
    {
        this.name = name;
        this.phone = phone;
        this.hostel = hostel;
        this.department = department;
        this.email = email;
    }
    public String getName(){
        return this.name;
    }
    public String getPhone(){
        return this.phone;
    }
    public String getHostel(){
        return this.hostel;
    }
    public String getDepartment(){
        return this.department;
    }
    public String getEmail() {
        return this.email;
    }
}
