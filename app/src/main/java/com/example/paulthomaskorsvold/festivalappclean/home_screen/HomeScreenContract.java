package com.example.paulthomaskorsvold.festivalappclean.home_screen;


import com.example.paulthomaskorsvold.festivalappclean.utils.BasePresenter;
import com.example.paulthomaskorsvold.festivalappclean.utils.BaseView;

/**
 * Created by paulthomaskorsvold on 9/27/17.
 */

public interface HomeScreenContract {
    public interface Presenter extends BasePresenter {

    }

    public interface View extends BaseView<Presenter> {

        void redirectToActivity(int id);
    }

}
