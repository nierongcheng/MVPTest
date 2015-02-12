package com.codi.mvptest.presentation.view.activity;

import android.os.Bundle;
import android.widget.Button;

import com.codi.mvptest.R;
import com.codi.mvptest.presentation.navigation.Navigator;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Codi on 2015/2/9.
 */
public class MainActivity extends BaseActivity {

    private Navigator mNavigator;

    @InjectView(R.id.btn_LoadData)
    Button mLoadDataBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        this.initialize();
    }

    private void initialize() {
        this.mNavigator = new Navigator();
    }

    @OnClick(R.id.btn_LoadData)
    void navigateToUserList() {
        this.mNavigator.navigateToUserList(this);
    }
}
