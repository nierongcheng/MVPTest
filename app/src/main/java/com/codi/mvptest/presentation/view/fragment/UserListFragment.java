package com.codi.mvptest.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.codi.mvptest.R;
import com.codi.mvptest.data.cache.FileManager;
import com.codi.mvptest.data.cache.UserCache;
import com.codi.mvptest.data.cache.UserCacheImpl;
import com.codi.mvptest.data.cache.serializer.JsonSerializer;
import com.codi.mvptest.data.executor.JobExecutor;
import com.codi.mvptest.data.executor.ThreadExecutor;
import com.codi.mvptest.data.repository.datasource.UserDataStoreFactory;
import com.codi.mvptest.domain.executor.PostExecutionThread;
import com.codi.mvptest.presentation.UIThread;
import com.codi.mvptest.presentation.model.UserModel;
import com.codi.mvptest.presentation.presenter.UserListPresenter;
import com.codi.mvptest.presentation.view.UserListView;
import com.codi.mvptest.presentation.view.adapter.UsersAdapter;
import com.codi.mvptest.presentation.view.adapter.UsersLayoutManager;

import java.util.Collection;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Codi on 2015/2/10.
 */
public class UserListFragment extends BaseFragment implements UserListView {

    public interface UserListListener {
        void onUserClicked(final UserModel userModel);
    }

    private UserListPresenter mUserListPresenter;

    @InjectView(R.id.rv_users)
    RecyclerView mUsersRv;
    @InjectView(R.id.rl_progress)
    RelativeLayout mProgressRl;
    @InjectView(R.id.rl_retry)
    RelativeLayout mRetryRl;
    @InjectView(R.id.bt_retry)
    Button mRetryBtn;

    private UsersLayoutManager mUsersLayoutManager;
    private UsersAdapter mUsersAdapter;
    private UserListListener mUserListListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof UserListListener) {
            mUserListListener = (UserListListener) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_user_list, container, true);
        ButterKnife.inject(this, fragmentView);
        setupUI();

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.loadUserList();
    }

    private void loadUserList() {
        this.mUserListPresenter.initialize();
    }

    private void setupUI() {
        mUsersLayoutManager = new UsersLayoutManager(getActivity());
        mUsersRv.setLayoutManager(mUsersLayoutManager);
    }

    @Override
    protected void initializePresenter() {
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        JsonSerializer userCacheSerializer = new JsonSerializer();
        UserCache userCache = UserCacheImpl.getINSTANCE(getActivity(), userCacheSerializer, FileManager.getInstance(), threadExecutor);
        UserDataStoreFactory userDataStoreFactory = new UserDataStoreFactory(getActivity(), userCache);
    }

    @Override
    public void renderUserList(Collection<UserModel> userModelCollection) {
        if (userModelCollection != null) {
            if (this.mUsersAdapter == null) {
                this.mUsersAdapter = new UsersAdapter(getActivity(), userModelCollection);
                this.mUsersAdapter.setOnItemClickListener(mOnItemClickListener);
                this.mUsersRv.setAdapter(this.mUsersAdapter);
            } else {
                this.mUsersAdapter.setUsersCollection(userModelCollection);
            }
        }
    }

    @Override
    public void viewUser(UserModel userModel) {

    }

    @Override
    public void showLoading() {
        this.mProgressRl.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.mProgressRl.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        this.mRetryRl.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.mRetryRl.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    private UsersAdapter.OnItemClickListener mOnItemClickListener = new UsersAdapter.OnItemClickListener() {
        @Override
        public void onUserItemClicked(UserModel userModel) {
            if (UserListFragment.this.mUserListPresenter != null && userModel != null) {
                UserListFragment.this.mUserListPresenter.onUserClicked(userModel);
            }
        }
    };

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        UserListFragment.this.loadUserList();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mUserListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mUserListPresenter.pause();
    }
}
