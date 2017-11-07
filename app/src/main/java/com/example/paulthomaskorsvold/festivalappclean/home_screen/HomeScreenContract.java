package com.example.paulthomaskorsvold.festivalappclean.home_screen;


import android.content.Context;

import com.example.paulthomaskorsvold.festivalappclean.utils.BasePresenter;
import com.example.paulthomaskorsvold.festivalappclean.utils.BaseView;

/**
 * Created by paulthomaskorsvold on 9/27/17.
 */

public interface HomeScreenContract {
    interface Presenter extends BasePresenter {

        void redirectToActivity(int id);

    }

    interface View extends BaseView<Presenter> {
    }

}
