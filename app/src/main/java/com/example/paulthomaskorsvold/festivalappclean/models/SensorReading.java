package com.example.paulthomaskorsvold.festivalappclean.models;

/**
 * Created by paulthomaskorsvold on 1/6/18.
 */

public class SensorReading {
    public double longitude, latitude;
    public String user;
    public String timeStamp;

    public SensorReading(double longitude, double latitude, String user) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
        this.timeStamp = "" + System.currentTimeMillis();
    }
    public SensorReading() {

    }
}
