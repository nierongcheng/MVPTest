package com.codi.mvptest.data.cache.serializer;

import com.codi.mvptest.data.entity.UserEntity;
import com.google.gson.Gson;

/**
 * Created by Codi on 2015/2/10.
 */
public class JsonSerializer {

    private final Gson gson = new Gson();

    public String seialize(UserEntity userEntity) {
        return gson.toJson(userEntity, UserEntity.class);
    }

    public UserEntity deserialize(String jsonString) {
        return gson.fromJson(jsonString, UserEntity.class);
    }
}
