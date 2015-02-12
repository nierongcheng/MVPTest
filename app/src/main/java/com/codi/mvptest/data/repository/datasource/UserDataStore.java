package com.codi.mvptest.data.repository.datasource;

import com.codi.mvptest.data.entity.UserEntity;

import java.util.Collection;

/**
 * Created by Codi on 2015/2/12.
 */
public interface UserDataStore {

    interface UserListCallback {
        void onUserListLoaded(Collection<UserEntity> usersCollection);
        void onError(Exception exception);
    }

    interface UserDetailsCallback {
        void onUserEntityLoaded(UserEntity userEntity);
        void onError(Exception exception);
    }

    void getUsersEntityList(UserListCallback userListCallback);

    void getUserEntityDetails(int id, UserDetailsCallback userDetailsCallback);

}
