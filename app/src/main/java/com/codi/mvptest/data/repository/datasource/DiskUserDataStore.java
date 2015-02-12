package com.codi.mvptest.data.repository.datasource;

import com.codi.mvptest.data.cache.UserCache;
import com.codi.mvptest.data.entity.UserEntity;

/**
 * Created by Codi on 2015/2/12.
 */
public class DiskUserDataStore implements UserDataStore {

    private final UserCache mUserCache;

    public DiskUserDataStore(UserCache userCache) {
        mUserCache = userCache;
    }


    @Override
    public void getUsersEntityList(UserListCallback userListCallback) {

    }

    @Override
    public void getUserEntityDetails(int id, final UserDetailsCallback userDetailsCallback) {
        this.mUserCache.get(id, new UserCache.UserCacheCallback() {
            @Override
            public void onUserEntityLoaded(UserEntity userEntity) {
                userDetailsCallback.onUserEntityLoaded(userEntity);
            }

            @Override
            public void onError(Exception exception) {
                userDetailsCallback.onError(exception);
            }
        });
    }
}
