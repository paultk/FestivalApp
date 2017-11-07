package com.example.paulthomaskorsvold.festivalappclean.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paulthomaskorsvold.festivalappclean.R;
import com.example.paulthomaskorsvold.festivalappclean.home_screen.HomeScreenActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private static final String M_TAG = "LoginActivity";
    private EditText mUsername, mPassword;
    private Button mLoginButton;
    private LoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setPresenter();
        initializeViews();
    }

    @Override
    public void setPresenter() {
        mPresenter = new LoginPresenter(this);
    }

    @Override
    public void initializeViews() {
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mLoginButton = findViewById(R.id.login_button);


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.checkUsernameAndPassword(mUsername.getText().toString(), mPassword.getText().toString());
            }
        });
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
