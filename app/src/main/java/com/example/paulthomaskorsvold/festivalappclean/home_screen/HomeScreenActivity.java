package com.example.paulthomaskorsvold.festivalappclean.home_screen;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.paulthomaskorsvold.festivalappclean.R;
import com.example.paulthomaskorsvold.festivalappclean.models.Notification;
import com.example.paulthomaskorsvold.festivalappclean.models.SensorReading;
import com.example.paulthomaskorsvold.festivalappclean.utils.api.RemoteServiceImplementation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.paulthomaskorsvold.festivalappclean.utils.Utils.showModal;

public class HomeScreenActivity extends AppCompatActivity implements HomeScreenContract.View {
    private static final String M_TAG = "HomeScreenActivity";

    private ImageView mCheckList, mMicrophone, mNavigation, mPayment, mSettings, mWeather;
    private ListView mNotificationListView;
    private List<Notification> mStrings;
    private ArrayAdapter<Notification> mNotificationListAdapter;
    private HomeScreenPresenter mPresenter;

    private FusedLocationProviderClient mFusedLocationClient;
    private RemoteServiceImplementation.RemoteService mRemoteService;


    private void initiateLocation() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M ) {
            checkPermission();
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }



    private void getLatestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            SensorReading sensorReading = new SensorReading(location.getLongitude(), location.getLatitude(), "first_user");

                            Call<List <com.example.paulthomaskorsvold.festivalappclean.models.Location>> call = mRemoteService.postSensorReading(sensorReading);

                            call.enqueue(new Callback<List<com.example.paulthomaskorsvold.festivalappclean.models.Location>>() {
                                @Override
                                public void onResponse(
                                        final Call<List<com.example.paulthomaskorsvold.festivalappclean.models.Location>> call,
                                        final Response<List<com.example.paulthomaskorsvold.festivalappclean.models.Location>> response) {
                                    final List<com.example.paulthomaskorsvold.festivalappclean.models.Location> tasks = response.body();
                                    if (tasks != null && !tasks.isEmpty()) {
//                    getView().showLoadedItems(tasks);

                                        Log.d(M_TAG, "onResponse: tasks found as map with size: " + tasks.size());
                                        for (com.example.paulthomaskorsvold.festivalappclean.models.Location location: tasks) {
                                            Log.d(M_TAG, location.toString());
                                            showModal("You are close to event in " + location.name, "Event notification", HomeScreenActivity.this);
                                        }
                                    } else {
                                        Log.d(M_TAG, "onResponse: no tasks found");
                                    }
                                }

                                @Override
                                public void onFailure(
                                        final Call<List<com.example.paulthomaskorsvold.festivalappclean.models.Location>> call,
                                        final Throwable t) {
//                getView().showErrorLoading();
                                    Log.e(M_TAG, "onResume: failed to que request", t);
                                }
                            });

                        }
                    }
                });

        return;

    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        ActionBar actionBar = getActionBar();
        setPresenter();
        initializeViews();
        mRemoteService = RemoteServiceImplementation.getInstance();
        initiateLocation();
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        getLatestLocation();
                        try {
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                            Log.d(M_TAG, e.getMessage());
                        }
                    }
                }
            }).start();
        } catch (Exception e) {
            Log.d(M_TAG, "Cant start sending locations");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_screen_menu, menu);
        menu.findItem(R.id.action_search).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.d(M_TAG, "search clicked");

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d(M_TAG, "title" + item.getTitle());

        switch (item.getItemId()) {
            case R.id.action_search:
                Log.i(M_TAG, "search clicked");
                return true;
            case R.id.festival_name:
                Log.d(M_TAG, "festival name clicked");
                //// TODO: 9/27/17 implement search logic here.
                return true;
            default:
                return true;
        }

    }

    @Override
    public void setPresenter() {
        mPresenter = new HomeScreenPresenter(this);
    }

    @Override
    public void initializeViews() {
        mStrings = new ArrayList<Notification>();
        mStrings.add(new Notification("Pink floyd starting in 10 minutes", "Stage 3, 15:30"));
        mStrings.add(new Notification("Red hot chili peppers is canceled", "No new information"));
        mStrings.add(new Notification("Oasis starts in 1 hour", "Stage 1, 22:20"));
        mStrings.add(new Notification("Thunderstorm incoming", "All festival area"));
        mNotificationListView = (ListView) findViewById(R.id.notification_listView);
        mNotificationListAdapter = new ArrayAdapter<Notification>(this, R.layout.support_simple_spinner_dropdown_item, mStrings);
        mNotificationListView.setAdapter(mNotificationListAdapter);
        mNotificationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showModal(mStrings.get(i).mBody, mStrings.get(i).mTitle, HomeScreenActivity.this);
                Log.d(M_TAG, "item clicked\n" + mStrings.get(i));
            }
        });


        mCheckList = findViewById(R.id.checklist);
        mNavigation = findViewById(R.id.navigation);
        mPayment = findViewById(R.id.payment);

        ArrayList<ImageView> arr = new ArrayList<>(Arrays.asList(mCheckList, mNavigation, mPayment));
        for (final ImageView imageView :
                arr) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.redirectToActivity(imageView.getId());
                }
            });
        }

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
