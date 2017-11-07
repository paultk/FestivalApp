package com.example.paulthomaskorsvold.festivalappclean.models;

/**
 * Created by paulthomaskorsvold on 10/29/17.
 */

public class Notification {
    public java.lang.String mTitle, mBody;

    public Notification(java.lang.String mTitle, java.lang.String mBody) {
        this.mTitle = mTitle;
        this.mBody = mBody;
    }

    @Override
    public java.lang.String toString() {
        return mTitle + '\'';
    }
}
