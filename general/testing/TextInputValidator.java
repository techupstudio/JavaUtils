package com.techupstudio.otc_chingy.mychurch.core.utils.general.testing;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;

public class TextInputValidator {

    public static boolean isValidEmail(CharSequence email) {
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public static boolean isValidUrl(String inputValue) {
        if (inputValue == null || inputValue.trim().isEmpty())
            return false;

        try {
            URL url = new URL(inputValue);
            url.toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    public static boolean isValidTextLength(String inputValue, int min, int max) {
        return inputValue != null
                && !inputValue.isEmpty()
                && inputValue.length() >= min
                && inputValue.length() <= max;
    }

    public static boolean isTextNotEmpty(String inputValue) {
        return inputValue != null && !inputValue.trim().isEmpty();
    }

    public static boolean isValidNumber(String inputValue) {
        if (inputValue == null || inputValue.trim().isEmpty())
            return false;

        try {
            Double.parseDouble(inputValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidFloatNumber(String inputValue) {
        if (!inputValue.contains("."))
            return false;
        return isValidNumber(inputValue);
    }

    public static boolean isValidIntegerNumber(String inputValue) {
        if (inputValue.contains("."))
            return false;
        return isValidNumber(inputValue);
    }

    public static boolean isValidPhoneNumber(String inputValue) {
        if (inputValue == null || inputValue.trim().isEmpty())
            return false;

        inputValue = inputValue.trim().replace(" ", "");

        if (inputValue.startsWith("+") && inputValue.length() > 10 && inputValue.length() < 14) {
            return isValidIntegerNumber(inputValue.substring(inputValue.length() - 9));
        } else if (!inputValue.startsWith("+") && inputValue.length() == 10) {
            return isValidIntegerNumber(inputValue);
        }
        return false;
    }

}
