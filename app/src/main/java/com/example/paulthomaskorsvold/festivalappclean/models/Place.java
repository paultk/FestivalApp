package com.example.paulthomaskorsvold.festivalappclean.models;

import java.io.Serializable;

/**
 * Created by paulthomaskorsvold on 11/5/17.
 */

public class Place implements Serializable {
    private String name;
    private String city;
    private double latitude;
    private double longitude;

    public Place() {
    }

    public Place(String name, String city, double latitude, double longitude) {
        this.name = name;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\tCity: " + city;
    }
}

