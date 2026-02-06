package com.example.mealsplanner.util;

import com.example.mealsplanner.data.model.User;
import com.example.mealsplanner.data.source.local.entity.UserEntity;

public class UserMapper {
    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setUid(user.getUid());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        return entity;
    }

    public static User toModel(UserEntity entity) {
        return new User(entity.getUid(), entity.getName(), entity.getEmail());
    }
}

