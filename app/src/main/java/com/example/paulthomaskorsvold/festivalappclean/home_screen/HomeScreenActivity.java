package com.example.paulthomaskorsvold.festivalappclean.home_screen;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
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
import com.example.paulthomaskorsvold.festivalappclean.checklist.CheckListActivity;
import com.example.paulthomaskorsvold.festivalappclean.models.Notification;
import com.example.paulthomaskorsvold.festivalappclean.navigation.MapsActivity;
import com.example.paulthomaskorsvold.festivalappclean.payment.PaymentActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.paulthomaskorsvold.festivalappclean.utils.Utils.showModal;

public class HomeScreenActivity extends AppCompatActivity implements HomeScreenContract.View {
    private static final String M_TAG = "HomeScreenActivity";

    private ImageView mCheckList, mMicrophone, mNavigation, mPayment, mSettings, mWeather;
    private ListView mNotificationListView;
    private List<Notification> mStrings;
    private ArrayAdapter<Notification> mNotificationListAdapter;
    private HomeScreenPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        ActionBar actionBar = getActionBar();
        setPresenter();
        initializeViews();

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
