package com.example.paulthomaskorsvold.festivalappclean.home_screen;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import com.example.paulthomaskorsvold.festivalappclean.R;
import com.example.paulthomaskorsvold.festivalappclean.checklist.CheckListActivity;
import com.example.paulthomaskorsvold.festivalappclean.navigation.MapsActivity;
import com.example.paulthomaskorsvold.festivalappclean.payment.PaymentActivity;

/**
 * Created by paulthomaskorsvold on 9/27/17.
 * First activity after login, he hub to the spokes
 */

public class HomeScreenPresenter implements HomeScreenContract.Presenter {

    private HomeScreenContract.View mHomeScreenView;

    public HomeScreenPresenter(HomeScreenContract.View homeScreenView) {
        mHomeScreenView = homeScreenView;
    }

    /** Redirects to the proper activity*/
    @Override
    public void redirectToActivity(int id) {
        Class intentClass = null;

        switch(id) {

            case R.id.checklist:
                intentClass = CheckListActivity.class;
                break;
            case R.id.navigation:
                intentClass = MapsActivity.class;
                break;
            case R.id.payment:
                intentClass = PaymentActivity.class;
            default:
                break;

        }
        if (intentClass != null) {
            Intent intent = new Intent(mHomeScreenView.getContext(), intentClass);
            mHomeScreenView.getContext().startActivity(intent);
        }
    }
}