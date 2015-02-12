package com.codi.mvptest.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.codi.mvptest.R;
import com.codi.mvptest.presentation.navigation.Navigator;

/**
 * Created by Codi on 2015/2/9.
 */
public class UserListActivity extends BaseActivity {

    private Navigator mNavigator;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, UserListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_user_list);
    }
}
