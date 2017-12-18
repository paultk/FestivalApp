package com.example.paulthomaskorsvold.festivalappclean.utils.api;

import java.io.File;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by paulthomaskorsvold on 11/19/17.
 */

public class RemoteServiceImplementation {
    private static final String M_API_KEY = "AIzaSyA8dElCwTXRza-lBp_TXpabS_hra5Bxgxc";
    private static String ip = "10.30.0.145";
    private static final String M_BASE_URL = "http://" + ip + ":3000";

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
        /*@GET("/sensor_reading.json")
        Call<Map<String, SensorData>> getAllSensorData(); // c

        @POST("/sensor_reading.json")
        Call<Map<String, String>> insertSensorData(@Body SensorData sensorData); // c

        @GET("/sensor_reading.json?orderBy=\"$key\"&limitToLast=1")
        Call<Map<String, SensorData>> getLastSensorData(); // c*/

        @GET("/")
        Call<Map<String, String>> testLocal();

        @POST("/")
        Call<Map<String, String>> postImage(@Body File file);

    }



}
