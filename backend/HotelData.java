package com.example.hotelbookingapp;

public class HotelData {
    public String name,location,price,stars;
    //public static int count=0;

    public HotelData(){

    }

    public HotelData(String name,String location,String price,String stars){
        this.name=name;
        this.location=location;
        this.price=price;
        this.stars=stars;
        //this.count=count;
    }

//    public static int getCount()
//    {
//        return count;
//    }
}
