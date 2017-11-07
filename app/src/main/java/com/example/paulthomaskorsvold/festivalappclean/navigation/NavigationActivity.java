package com.example.paulthomaskorsvold.festivalappclean.navigation;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paulthomaskorsvold.festivalappclean.R;
import com.example.paulthomaskorsvold.festivalappclean.models.Place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

/**
 * Displays a map, depending on the Action called by MapsActivity, the map is either for displaying a saved location or adding a new one.
 */


public class NavigationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String M_TAG = "NavigationActivity";

    public final int PERMISSIONS_REQUEST_LOCATION = 1003;

    private GoogleMap mMap;
    private EditText mNameEditText;
    private String mAction;
    private String mPlaceFilter;
    private Marker mMarker;
    private Place mPlace;

    //defines wheater the activity is viewing or inserting
    private boolean mIsViewing;

    /*
    * Place{name='null', city='Lelystad', latitude=52.456656020617245, longitude=5.393330901861191}*/

    public static final Place HOME = new Place("Home", "Amsterdam", 52.35920595735264, 4.909708499908447);
    public static final String PLACE = "PLACE";
//    public static final

//    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        mNameEditText = (EditText) findViewById(R.id.edit_text_place_name);
//        mUri = getIntent().getParcelableExtra(PlacesProvider.CONTENT_ITEM_TYPE);

        populateMap();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try {
            mIsViewing = getIntent().getAction().equals(Intent.ACTION_VIEW);
        } catch (Exception e) {
            Log.d(M_TAG, e.getMessage());
        }

//        Hide the inputfield if the ACTION_CODE is VIEWING
        if (mIsViewing) {
            mNameEditText.setVisibility(View.GONE);
        }
    }



    private String showCityName(double latitude, double longitude) {
        String cityName = getString(R.string.no_city);
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            cityName = addresses.get(0).getLocality();
        } catch (Exception e) {
//            Toast.makeText(NavigationActivity.this, "No city could be found", Toast.LENGTH_SHORT).show();
        }

        return cityName;
    }

    /**
     * Add new marker to map
     * @param latLng
     */
    private void addMarker(LatLng latLng) {
        mMap.clear();
        mMarker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(mNameEditText.getText().toString())
                .snippet("city: " + showCityName(latLng.latitude, latLng.longitude))
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        enableMyLocation();

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                marker.remove();
            }
            @Override
            public void onMarkerDrag(Marker marker) {
            }
            @Override
            public void onMarkerDragEnd(Marker marker) {
            }
        });

        // add new marker on long click
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                addMarker(latLng);
                // set the LatLng values each time a new marker is created
                mPlace.setLatitude(mMarker.getPosition().latitude);
                mPlace.setLongitude(mMarker.getPosition().longitude);
                mPlace.setName(mNameEditText.getText().toString());
                mPlace.setCity(showCityName(mPlace.getLatitude(), mPlace.getLongitude()));
                Log.d(M_TAG, getString(R.string.place) + mPlace);

                if (!mIsViewing) {
                    showDialog(mPlace);
                }
            }
        });

        if (mPlace.getLatitude() != 0.0 && mPlace.getLongitude() != 0.0) {
            // add marker and animate camera
            LatLng latLng = new LatLng(mPlace.getLatitude(), mPlace.getLongitude());
            addMarker(latLng);
            CameraUpdate markerLocation = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            mMap.animateCamera(markerLocation);
        } else {
            LatLng latLng = new LatLng(52.092876, 5.104480);
            CameraUpdate markerLocation = CameraUpdateFactory.newLatLngZoom(latLng, 6);
            mMap.animateCamera(markerLocation);
        }
    }

    private void enableMyLocation() {
        // Check if location permissions are granted.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission.
            // The dialog box asking for permission is generated by this call.
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION
            );
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    private void populateMap() {
        mPlace = new Place();
        if (mIsViewing) {
            mPlace = (Place) getIntent().getExtras().getSerializable(PLACE);
        }
        mPlace = HOME;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // location-related task you need to do.
                enableMyLocation();
            } else {
                // Otherwise, disable functionality that requires permission(s)
                // and let your user know about it.
            }
        }
    }

    private void showDialog(Place place) {
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(NavigationActivity.this);
        builder1.setTitle(R.string.confirm);
        builder1.setMessage(getString(R.string.confirmation) + place.getName());
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(PLACE, mPlace);
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                });

        builder1.setNegativeButton(
                R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        android.support.v7.app.AlertDialog alertDialog = builder1.create();
        alertDialog.show();
//        return alertDialog;
    }
}

