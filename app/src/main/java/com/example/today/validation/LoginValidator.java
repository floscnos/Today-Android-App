package com.example.today.validation;

import android.content.Context;

import com.example.today.models.User;

import java.util.regex.Pattern;

public class LoginValidator {

    // Requirements PasswordRegistration
    private static String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$"; // needs at least: 1 digit, 1 lowercase, 1 uppercase, 8 and max 20 characters.
    private static Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public boolean userRegistrationValidation(String user) {
        return user != null && !user.equals("");
    }

    public boolean passwordRegistrationValidation(String firstPassword, String secondPassword) {
        if (firstPassword == null || secondPassword == null) {
            return false;
        }
        if (!firstPassword.equals(secondPassword)) {
            return false;
        }
        if (firstPassword.isEmpty()) {
            return false;
        }
        if (!pattern.matcher(firstPassword).matches()) {
            return false;
        }
        return true;
    }

    public boolean loginValidation(Context context, String username, String password) {
        User user = User.getByUsername(context, username);

        if (user == null) {
            return false;
        }
        if (user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
