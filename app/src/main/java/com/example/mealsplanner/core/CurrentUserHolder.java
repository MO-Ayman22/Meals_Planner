package com.example.mealsplanner.core;

import com.example.mealsplanner.data.domain.model.User;

public class CurrentUserHolder {
    private static User currentUser;

    public static User getUser() {
        return currentUser;
    }

    public static void setUser(User user) {
        currentUser = user;
    }

    public static boolean hasUser() {
        return currentUser != null;
    }
}
