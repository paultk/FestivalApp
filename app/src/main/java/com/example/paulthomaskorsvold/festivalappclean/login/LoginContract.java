package com.example.paulthomaskorsvold.festivalappclean.login;

import com.example.paulthomaskorsvold.festivalappclean.utils.BasePresenter;
import com.example.paulthomaskorsvold.festivalappclean.utils.BaseView;

/**
 * Created by paulthomaskorsvold on 11/7/17.
 */

public interface LoginContract  {
    public interface Presenter extends BasePresenter {
        boolean checkUsernameAndPassword(String username, String password);

        void redirectToActivity();

        boolean validate(String username, String password);

    }

    public interface View extends BaseView<LoginContract.Presenter> {
    }
}
