package com.example.paulthomaskorsvold.festivalappclean.login;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by paulthomaskorsvold on 11/7/17.
 */
public class LoginPresenterTest {

    @Mock
    private LoginContract.View mView;
    private LoginPresenter mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new LoginPresenter(mView);
    }

    @Test
    public void testPassword() {
        boolean shouldBeTrue = mPresenter.validate("username", "password");
        boolean shouldBeFalse = mPresenter.validate("wrong", "wrong");

        Assert.assertTrue(shouldBeTrue);
        Assert.assertFalse(shouldBeFalse);
    }
}