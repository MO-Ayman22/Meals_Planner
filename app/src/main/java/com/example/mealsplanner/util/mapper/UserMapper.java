package com.example.mealsplanner.util.mapper;

import com.example.mealsplanner.data.domain.entity.UserEntity;
import com.example.mealsplanner.data.domain.model.User;

public class UserMapper {
    public static UserEntity toEntity(User user) {
        return new UserEntity(user.getUid(), user.getName(), user.getEmail());
    }

    public static User toModel(UserEntity entity) {
        return new User(entity.getUid(), entity.getName(), entity.getEmail());
    }
}

