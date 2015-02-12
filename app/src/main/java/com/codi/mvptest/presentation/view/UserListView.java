package com.codi.mvptest.presentation.view;

import com.codi.mvptest.presentation.model.UserModel;

import java.util.Collection;

/**
 * Created by Codi on 2015/2/10.
 */
public interface UserListView extends LoadDataView {

    void renderUserList(Collection<UserModel> userModelCollection);

    void viewUser(UserModel userModel);

}
