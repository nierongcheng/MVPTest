package com.codi.mvptest.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.codi.mvptest.presentation.view.activity.MainActivity;
import com.codi.mvptest.presentation.view.activity.UserListActivity;

/**
 * Created by Codi on 2015/2/9.
 */
public class Navigator {
    public void navigateToUserList(Context context) {
        if (context != null) {
            Intent intentToLaunch = UserListActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }
}
