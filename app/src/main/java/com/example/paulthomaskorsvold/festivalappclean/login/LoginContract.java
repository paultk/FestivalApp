package com.example.paulthomaskorsvold.festivalappclean.login;

import android.content.Context;

import com.example.paulthomaskorsvold.festivalappclean.utils.BasePresenter;
import com.example.paulthomaskorsvold.festivalappclean.utils.BaseView;

/**
 * Created by paulthomaskorsvold on 11/7/17.
 */

public interface LoginContract  {
    public interface Presenter extends BasePresenter {
        public void checkUsernameAndPassword(String username, String password);

    }

    public interface View extends BaseView<LoginContract.Presenter> {
    }
}
