package com.example.paulthomaskorsvold.festivalappclean.models;

/**
 * Created by paulthomaskorsvold on 1/6/18.
 */

public class Location {
    public String id, longitude, latitude, name ;


    public Location(){}

    @Override
    public String toString() {
        return "Location{" +
                "id='" + id + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
