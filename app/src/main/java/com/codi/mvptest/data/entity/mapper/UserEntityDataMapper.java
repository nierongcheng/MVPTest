package com.codi.mvptest.data.entity.mapper;

import com.codi.mvptest.data.entity.UserEntity;
import com.codi.mvptest.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Codi on 2015/2/12.
 */
public class UserEntityDataMapper {

    public User transform(UserEntity userEntity) {
        User user = null;
        if (userEntity != null) {
            user = new User(userEntity.getUserId());
            user.setCoverUrl(userEntity.getCoverUrl());
            user.setFullName(userEntity.getFullname());
            user.setDescription(userEntity.getDescription());
            user.setFollowers(userEntity.getFollowers());
            user.setEmail(userEntity.getEmail());
        }

        return user;
    }

    public Collection<User> transform(Collection<UserEntity> userEntityCollection) {
        List<User> userList = new ArrayList<>(20);
        User user;
        for(UserEntity userEntity : userEntityCollection) {
            user = transform(userEntity);
            if(user != null) {
                userList.add(user);
            }
        }
        return userList;
    }
}
