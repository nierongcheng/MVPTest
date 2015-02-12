package com.codi.mvptest.data.repository.datasource;

import android.content.Context;

import com.codi.mvptest.data.cache.UserCache;

/**
 * Created by Codi on 2015/2/12.
 */
public class UserDataStoreFactory {

    private final Context mContext;
    private final UserCache mUserCache;

    public UserDataStoreFactory(Context context, UserCache userCache) {

        if(context == null || userCache == null) {
            throw new IllegalArgumentException("Constructor parameters can not be null");
        }

        this.mContext = context;
        this.mUserCache = userCache;
    }

    public UserDataStore create(int userId) {
        UserDataStore userDataStore;

        if(!this.mUserCache.isExpired() && this.mUserCache.isCached(userId)) {
            userDataStore = new DiskUserDataStore(this.mUserCache);
        } else {
            userDataStore = createCloudDataStore();
        }

        return userDataStore;
    }

    private UserDataStore createCloudDataStore() {

        return null;
    }
}
