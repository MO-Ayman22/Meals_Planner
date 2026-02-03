package com.example.mealsplanner.util;

import android.text.TextUtils;

public class ValidationUtil {

    public static String validateEmail(String email) {
        if (TextUtils.isEmpty(email)) return "Email is required";

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return "Invalid email format";

        return null;
    }

    public static String validatePassword(String password) {
        if (TextUtils.isEmpty(password)) return "Password is required";

        if (password.length() < 8) return "Password must be at least 8 characters";

        return null;
    }

    public static String validateName(String name) {
        if (TextUtils.isEmpty(name)) return "Name is required";

        if (name.length() < 3) return "Name must be at least 3 characters";

        return null;
    }

    public static String validateConfirmPassword(String password, String confirmPassword) {
        if (TextUtils.isEmpty(confirmPassword)) return "Confirm password is required";

        if (!password.equals(confirmPassword)) return "Passwords do not match";

        return null;
    }

    public static String validateTermsAndConditions(boolean isChecked) {
        if (!isChecked) return "You must accept the terms and conditions";

        return null;
    }
}

