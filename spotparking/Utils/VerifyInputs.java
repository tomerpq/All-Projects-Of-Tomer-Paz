package com.example.spotparking.Utils;

import android.graphics.Color;
import android.util.Patterns;

public  class VerifyInputs {
    public static boolean validateEmail(String emailInput) {
        // if the email is not a valid email address, show an error.
        return !emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches();
    }

    public static boolean validatePassword(String passwordInput) {
        return !passwordInput.isEmpty() && passwordInput.length() >= 6;
    }

    public static boolean validateColor(String colorInput) {
        try {
            int color = Color.parseColor(colorInput);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
