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
import com.example.paulthomaskorsvold.festivalappclean.models.Location;
import com.example.paulthomaskorsvold.festivalappclean.models.SensorReading;
import com.example.paulthomaskorsvold.festivalappclean.utils.api.RemoteServiceImplementation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.paulthomaskorsvold.festivalappclean.utils.Utils.showModal;

/**
 * Fragment for "Adding funds" and displaying previous transaction history
 */

public class AddFragment extends Fragment {

    private ArrayList<String> mTransactionHistoryList;
    private ListView mTransactionHistoryListView;
    private ArrayAdapter<String> mTransactionHistoryAdapter;
    private final String M_TAG = "AddFragment";
    private Button mAddFundsButton;
    private static final String url = "http://visa.nl";

    private RemoteServiceImplementation.RemoteService mRemoteService;



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
            }
        });

        mAddFundsButton = view.findViewById(R.id.add_funds_button);
        mAddFundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openChrome();
                try {
                    sendRequest();
                } catch (Exception e) {
                    Log.d(M_TAG, e.getMessage());
                }
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        mRemoteService = RemoteServiceImplementation.getInstance();
    }

    /**
     * Opens a Google chrome window
     */
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

    private void sendRequest() {
        SensorReading s = new SensorReading(-1, -1, "someUser");
        Call<List <Location>> call = mRemoteService.postSensorReading(s);

        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(
                    final Call<List<Location>> call,
                    final Response<List<Location>> response) {
                final List<Location> tasks = response.body();
                if (tasks != null && !tasks.isEmpty()) {
//                    getView().showLoadedItems(tasks);

                    Log.d(M_TAG, "onResponse: tasks found as map with size: " + tasks.size());
                    for (Location location: tasks) {
                        Log.d(M_TAG, location.toString());
                    }
                } else {
                    Log.d(M_TAG, "onResponse: no tasks found");
                }
            }

            @Override
            public void onFailure(
                    final Call<List<Location>> call,
                    final Throwable t) {
//                getView().showErrorLoading();
                Log.e(M_TAG, "onResume: failed to que request", t);
            }
        });
    }
}
