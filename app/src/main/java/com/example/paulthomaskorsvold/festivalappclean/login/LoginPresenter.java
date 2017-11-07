package com.example.paulthomaskorsvold.festivalappclean.login;

import android.content.Intent;
import android.widget.Toast;

import com.example.paulthomaskorsvold.festivalappclean.R;
import com.example.paulthomaskorsvold.festivalappclean.home_screen.HomeScreenActivity;

/**
 * Created by paulthomaskorsvold on 11/7/17.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mLoginView;

    public LoginPresenter(LoginContract.View loginView) {
        mLoginView = loginView;
//        mLoginView.setPresenter(this);
    }




    @Override
    public boolean checkUsernameAndPassword(String username, String password) {
        if (validate(username, password)) {
            Intent intent = new Intent(mLoginView.getContext(), HomeScreenActivity.class);
            mLoginView.getContext().startActivity(intent);
            return true;
        } else {
            Toast.makeText(mLoginView.getContext(), R.string.login_error_info, Toast.LENGTH_LONG).show();
            return false;
        }

    }
    @Override
    public boolean validate(String username, String password) {
        if (username.equals(mLoginView.getContext().getString(R.string.username_2)) && password.equals(mLoginView.getContext().getString(R.string.password_2))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void redirectToActivity() {

    }


}
