package com.codi.mvptest.data.net;

import com.codi.mvptest.data.entity.UserEntity;

import java.util.Collection;

/**
 * Created by Codi on 2015/2/26.
 */
public interface RestApi {

    interface UserListCallback {
        void onUserListLoaded(Collection<UserEntity> users);
        void onError(Exception exception);
    }

    interface UserDetailsCallback {
        void onUserEntityLoaded(UserEntity user);
        void onError(Exception exception);
    }

    static final String API_BASE_URL = "http://www.android10.org/myapi/";
    /** Api url for getting all users */
    static final String API_URL_GET_USER_LIST = API_BASE_URL + "users.json";
    /** Api url for getting a user profile: Remember to concatenate id + 'json' */
    static final String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";

    void getUserList(UserListCallback userListCallback);

    void getUserById(final int userId, final UserDetailsCallback userDetailsCallback);
}
