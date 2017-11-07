package com.example.paulthomaskorsvold.festivalappclean.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.paulthomaskorsvold.festivalappclean.R;
import com.example.paulthomaskorsvold.festivalappclean.home_screen.HomeScreenActivity;
import com.example.paulthomaskorsvold.festivalappclean.models.Notification;
import com.example.paulthomaskorsvold.festivalappclean.models.Place;

import java.util.ArrayList;
import java.util.List;

import static com.example.paulthomaskorsvold.festivalappclean.utils.Utils.showModal;

/**
 * Created by paulthomaskorsvold on 11/5/17.
 */

public class MapsActivity extends AppCompatActivity {
    private static final String M_TAG = "MapsActivity";
    private ListView mSavedPlacesListView;
    private List<Place> mSavedPlacesList;
    private ArrayAdapter<Place> mSavedPlacesAdapter;

    private String testString;

//    private

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        testString = "something";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mSavedPlacesList = new ArrayList<Place>();
        mSavedPlacesList.add(new Place("Home", "Amsterdam", 52.35920595735264, 4.909708499908447));
        mSavedPlacesListView = findViewById(R.id.saved_places_listview);
        mSavedPlacesAdapter = new ArrayAdapter<Place>(this, R.layout.support_simple_spinner_dropdown_item, mSavedPlacesList);
        mSavedPlacesListView.setAdapter(mSavedPlacesAdapter);

        mSavedPlacesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MapsActivity.this, NavigationActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                intent.putExtra(NavigationActivity.PLACE, mSavedPlacesList.get(i));
                startActivity(intent);
            }
        });

        mSavedPlacesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSavedPlacesList.remove(i);
                mSavedPlacesAdapter.notifyDataSetChanged();
                return true;
            }
        });

        findViewById(R.id.fab_add_place).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, NavigationActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        for (Place place :
                mSavedPlacesList) {
            Log.d(M_TAG, "item:\t" + place.toString());
        }
        Log.d(M_TAG, "here???");
        Log.d(M_TAG, testString);

        if (resultCode == RESULT_OK) {
//            try {
                for (Place place :
                        mSavedPlacesList) {
                    Log.d(M_TAG, "item:\t" + place.toString());
                }
            Log.d(M_TAG, "here2");

            Place newPlace = (Place) data.getExtras()
                        .getSerializable(NavigationActivity.PLACE);
//                mSavedPlacesAdapter.clear();
                mSavedPlacesList.add(newPlace);

            mSavedPlacesAdapter = new ArrayAdapter<Place>(this, R.layout.support_simple_spinner_dropdown_item, mSavedPlacesList);
            mSavedPlacesListView.setAdapter(mSavedPlacesAdapter);

                mSavedPlacesAdapter.notifyDataSetChanged();

//            } catch (Exception e) {
//                Log.d(M_TAG, e.getMessage());
//            }
        }
    }
}
