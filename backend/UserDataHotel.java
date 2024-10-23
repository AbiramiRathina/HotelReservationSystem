package com.example.hotelbookingapp;


public class UserDataHotel {
    public String fullname,age,email,role;
    public int cBookings;


    public UserDataHotel()
    {}
    public UserDataHotel(String fullname,String age,String email, String role ,int cBookings){
        this.fullname=fullname;
        this.age=age;
        this.email=email;
        this.role=role;
        this.cBookings=cBookings;
    }

}