package com.codi.mvptest.presentation.view;

import android.content.Context;

/**
 * Created by Codi on 2015/2/10.
 */
public interface LoadDataView {

    void showLoading();

    void hideLoading();

    void showRetry();

    void hideRetry();

    void showError(String message);

    Context getContext();

}
