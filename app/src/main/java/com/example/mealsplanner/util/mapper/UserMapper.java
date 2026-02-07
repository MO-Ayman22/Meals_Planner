package com.example.mealsplanner.util.mapper;

import com.example.mealsplanner.data.model.domain.User;
import com.example.mealsplanner.data.model.entity.UserEntity;

public class UserMapper {
    public static UserEntity toEntity(User user) {
        return new UserEntity(user.uid(), user.name(), user.email());
    }

    public static User toModel(UserEntity entity) {
        return new User(entity.getUid(), entity.getName(), entity.getEmail());
    }
}

