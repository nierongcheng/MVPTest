package com.codi.mvptest.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.codi.mvptest.data.entity.UserEntity;
import com.codi.mvptest.data.entity.mapper.UserEntityJsonMapper;
import com.codi.mvptest.data.exception.NetworkConnectionException;

import java.util.Collection;

/**
 * Created by Codi on 2015/2/26.
 */
public class RestApiImpl implements RestApi {

    private final Context context;
    private final UserEntityJsonMapper userEntityJsonMapper;

    public RestApiImpl(Context context, UserEntityJsonMapper userEntityJsonMapper) {
        if (context == null || userEntityJsonMapper == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.userEntityJsonMapper = userEntityJsonMapper;
    }

    @Override
    public void getUserList(UserListCallback userListCallback) {
        if (userListCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }

        if (isInternetConnection()) {
            try {
                ApiConnection getUserListConnection =
                        ApiConnection.createGET(RestApi.API_URL_GET_USER_LIST);
                String responseUserList = getUserListConnection.requestSyncCall();
                Collection<UserEntity> userEntityList =
                        this.userEntityJsonMapper.transformUserEntityCollection(responseUserList);

                userListCallback.onUserListLoaded(userEntityList);
            } catch (Exception e) {
                userListCallback.onError(new NetworkConnectionException(e.getCause()));
            }
        } else {
            userListCallback.onError(new NetworkConnectionException());
        }
    }

    @Override
    public void getUserById(int userId, UserDetailsCallback userDetailsCallback) {
        if (userDetailsCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }

        if (isInternetConnection()) {
            try {
                String apiUrl = RestApi.API_URL_GET_USER_DETAILS + userId + ".json";
                ApiConnection getUserDetailsConnection = ApiConnection.createGET(apiUrl);
                String responseUserDetails = getUserDetailsConnection.requestSyncCall();
                UserEntity userEntity = this.userEntityJsonMapper.transformUserEntity(responseUserDetails);

                userDetailsCallback.onUserEntityLoaded(userEntity);
            } catch (Exception e) {
                userDetailsCallback.onError(new NetworkConnectionException(e.getCause()));
            }
        } else {
            userDetailsCallback.onError(new NetworkConnectionException());
        }
    }

    private boolean isInternetConnection() {
        ConnectivityManager manager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
