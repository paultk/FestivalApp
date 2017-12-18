package com.example.paulthomaskorsvold.sensorapplication;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by paulthomaskorsvold on 11/19/17.
 */

public class RemoteServiceImplementation {
    private static final String M_API_KEY = "AIzaSyA8dElCwTXRza-lBp_TXpabS_hra5Bxgxc";
    private static final String M_BASE_URL = "https://androidproject-e69d9.firebaseio.com";

    private static RemoteService service = null;

    public static RemoteService getInstance() {
        if (service == null) {
            service = retrofit.create(RemoteService.class);
        }
        return service;
    }

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(M_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // use gson converter
            .build();

    public interface RemoteService {
        @GET("/sensor_reading.json")
        Call<Map<String, SensorData>> getAllSensorData(); // c

        @POST("/sensor_reading.json")
        Call<Map<String, String>> insertSensorData(@Body SensorData sensorData); // c

        @GET("/sensor_reading.json?orderBy=\"$key\"&limitToLast=1")
        Call<Map<String, SensorData>> getLastSensorData(); // c

    }



}
