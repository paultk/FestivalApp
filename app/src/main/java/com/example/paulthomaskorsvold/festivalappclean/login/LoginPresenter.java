package com.example.paulthomaskorsvold.festivalappclean.login;

import android.content.Intent;
import android.widget.Toast;

import com.example.paulthomaskorsvold.festivalappclean.home_screen.HomeScreenActivity;

import static android.support.v4.content.ContextCompat.startActivity;

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
    public void start() {
    }


    @Override
    public void checkUsernameAndPassword(String username, String password) {
        if (username.equals("username") && password.equals("password")) {
            Intent intent = new Intent(mLoginView.getContext(), HomeScreenActivity.class);
            mLoginView.getContext().startActivity(intent);
        } else {
            Toast.makeText(mLoginView.getContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
        }

    }
}
