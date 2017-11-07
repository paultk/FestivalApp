package com.example.paulthomaskorsvold.festivalappclean.payment;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.paulthomaskorsvold.festivalappclean.R;

import java.util.ArrayList;

import static com.example.paulthomaskorsvold.festivalappclean.utils.Utils.showModal;


public class AddFragment extends Fragment {

    private ArrayList<String> mTransactionHistoryList;
    private ListView mTransactionHistoryListView;
    private ArrayAdapter<String> mTransactionHistoryAdapter;
    private final String M_TAG = "AddFragment";
    private Button mAddFundsButton;
    private static final String url = "http://visa.nl";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mTransactionHistoryList = new ArrayList<String>();
        mTransactionHistoryList.add("20.2.2017 19:00 Degos pancakes 50 $");
        mTransactionHistoryList.add("5.2.2017 12:00 Papa johns pizza 30 $");
        mTransactionHistoryListView = (ListView) getView().findViewById(R.id.transaction_history_listview);
        mTransactionHistoryAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, mTransactionHistoryList);
        mTransactionHistoryListView.setAdapter(mTransactionHistoryAdapter);
        mTransactionHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showModal(mTransactionHistoryList.get(i), "Previous transaction", getContext());
                Log.d(M_TAG, "item clicked\n" + mTransactionHistoryList.get(i));
            }
        });

        mAddFundsButton = view.findViewById(R.id.add_funds_button);
        mAddFundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(M_TAG, "mAddFundsButton clicked");
                openChrome();
            }
        });
    }

    /*
    *    mStrings = new ArrayList<Notification>();
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
*/

    private void init(ViewGroup container) {


    }


    private void openChrome() {
        try {
            Intent i = new Intent("android.intent.action.MAIN");
            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
            i.addCategory("android.intent.category.LAUNCHER");
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        catch(ActivityNotFoundException e) {
            // Chrome is not installed
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
        }
    }
}
