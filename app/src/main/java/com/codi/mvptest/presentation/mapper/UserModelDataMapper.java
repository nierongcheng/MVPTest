package com.codi.mvptest.presentation.mapper;

import com.codi.mvptest.domain.User;
import com.codi.mvptest.presentation.model.UserModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Codi on 2015/2/12.
 */
public class UserModelDataMapper {

    public UserModel transform(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Can not transform a null value");
        }
        UserModel userModel = new UserModel(user.getUserId());
        userModel.setCoverUrl(user.getCoverUrl());
        userModel.setFullName(user.getFullName());
        userModel.setEmail(user.getEmail());
        userModel.setDescription(user.getDescription());
        userModel.setFollowers(user.getFollowers());

        return userModel;
    }

    public Collection<UserModel> transform(Collection<User> usersCollection) {
        Collection<UserModel> userModelsCollection;

        if(usersCollection != null && !usersCollection.isEmpty()) {
            userModelsCollection = new ArrayList<>();
            for(User user : usersCollection) {
                userModelsCollection.add(transform(user));
            }
        } else {
            userModelsCollection = Collections.emptyList();
        }

        return userModelsCollection;
    }

}
