package com.codi.mvptest.presentation.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Codi on 2015/2/10.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initializePresenter();
    }

    protected abstract void initializePresenter();

    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
