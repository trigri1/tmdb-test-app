package com.test.project24.ui.base;

import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;

/**
 * Created by Gohar Ali on 30/10/2017.
 */

public interface BaseView {

    public void showLoading();

    public void hideLoading();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();

    Snackbar showSnackBar(String message, int duration);

    Snackbar showSnackBar(@StringRes int message, int duration);
}
