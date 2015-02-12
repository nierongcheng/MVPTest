package com.codi.mvptest.data.cache;

import com.codi.mvptest.data.entity.UserEntity;

/**
 * Created by Codi on 2015/2/10.
 */
public interface UserCache {

    interface UserCacheCallback {
        void onUserEntityLoaded(UserEntity userEntity);
        void onError(Exception exception);
    }

    void get(final int userId, final UserCacheCallback callback);

    void put(UserEntity userEntity);

    boolean isCached(final int userId);

    boolean isExpired();

    void evictAll();

}
