package com.codi.mvptest.domain.interactor;

import com.codi.mvptest.domain.User;
import com.codi.mvptest.domain.exception.ErrorBundle;

import java.util.Collection;

/**
 * Created by Codi on 2015/2/10.
 */
public interface GetUserListUseCase extends Interactor {

    interface Callback {
        void onUserListLoaded(Collection<User> usersCollection);
        void onError(ErrorBundle errorBundle);
    }

    void execute(Callback callback);
}
