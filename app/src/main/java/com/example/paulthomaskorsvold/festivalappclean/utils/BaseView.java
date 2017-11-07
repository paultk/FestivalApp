package com.example.paulthomaskorsvold.festivalappclean.utils;

import android.content.Context;

/**
 * Created by paulthomaskorsvold on 9/27/17.
 */

public interface BaseView<T> {
    void setPresenter();

    void initializeViews();

    Context getContext();

}
