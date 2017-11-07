package com.example.paulthomaskorsvold.festivalappclean.home_screen;


/**
 * Created by paulthomaskorsvold on 9/27/17.
 */

public class HomeScreenPresenter implements HomeScreenContract.Presenter {

    private HomeScreenContract.View mHomeScreenView;

    public HomeScreenPresenter(HomeScreenContract.View homeScreenView) {
        mHomeScreenView = homeScreenView;
        mHomeScreenView.setPresenter();
    }



    public int returnOne() {
        return 1;
    }

    @Override
    public void start() {
    }




}
