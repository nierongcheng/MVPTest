package com.codi.mvptest.data.entity.mapper;

import com.codi.mvptest.data.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by Codi on 2015/2/12.
 */
public class UserEntityJsonMapper {

    private final Gson mGson;

    public UserEntityJsonMapper() {
        mGson = new Gson();
    }

    public UserEntity transformUserEntity(String userJsonResponse) {
        Type userEntityType = new TypeToken<UserEntity>() {}.getType();
        return this.mGson.fromJson(userJsonResponse, userEntityType);
    }

    public Collection<UserEntity> transformUserEntityCollection(String userListJsonResponse) {

        Type listOfUserEntityType = new TypeToken<Collection<UserEntity>>() {}.getType();
        return this.mGson.fromJson(userListJsonResponse, listOfUserEntityType);
    }
}
