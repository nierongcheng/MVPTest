package com.codi.mvptest.presentation.presenter;

import com.codi.mvptest.domain.User;
import com.codi.mvptest.domain.exception.ErrorBundle;
import com.codi.mvptest.domain.interactor.GetUserListUseCase;
import com.codi.mvptest.presentation.exception.ErrorMessageFactory;
import com.codi.mvptest.presentation.mapper.UserModelDataMapper;
import com.codi.mvptest.presentation.model.UserModel;
import com.codi.mvptest.presentation.view.UserListView;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by Codi on 2015/2/10.
 */
public class UserListPresenter implements Presenter {

    private final UserListView mViewListView;

    private final GetUserListUseCase mUserListUseCase;

    private final UserModelDataMapper mUserModelDataMapper;

    public UserListPresenter(UserListView userListView, GetUserListUseCase getUserListUseCase, UserModelDataMapper userModelDataMapper) {
        if (userListView == null || getUserListUseCase == null || userModelDataMapper == null) {
            throw new IllegalArgumentException("Constructor parameters can not be null");
        }
        this.mViewListView = userListView;
        this.mUserListUseCase = getUserListUseCase;
        this.mUserModelDataMapper = userModelDataMapper;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void initialize() {
        this.loadList();
    }

    private void loadList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserList();
    }

    private void getUserList() {
        this.mUserListUseCase.execute(mUserListCallback);
    }

    public void onUserClicked(UserModel userModel) {

    }

    private void showViewLoading() {
        this.mViewListView.showLoading();
    }

    private void hideViewLoading() {
        this.mViewListView.hideLoading();
    }

    private void showViewRetry() {
        this.mViewListView.showRetry();
    }

    private void hideViewRetry() {
        this.mViewListView.hideRetry();
    }

    private GetUserListUseCase.Callback mUserListCallback = new GetUserListUseCase.Callback() {
        @Override
        public void onUserListLoaded(Collection<User> usersCollection) {
            UserListPresenter.this.showUsersCollectionInView(usersCollection);
            UserListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            UserListPresenter.this.hideViewLoading();
            UserListPresenter.this.showErrorMessage(errorBundle);
            UserListPresenter.this.showViewRetry();
        }
    };

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.mViewListView.getContext(), errorBundle.getException());
        this.mViewListView.showError(errorMessage);
    }

    private void showUsersCollectionInView(Collection<User> usersCollection) {
        Collection<UserModel> usersModelCollection = this.mUserModelDataMapper.transform(usersCollection);
        this.mViewListView.renderUserList(usersModelCollection);
    }
}
