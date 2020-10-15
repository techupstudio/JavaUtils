package com.techupstudio.otc_chingy.mychurch.core.utils.general.testing;

import java.util.regex.Pattern;

public class TextInputValidator {

    public static boolean validateEmail(String email) {
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public static boolean validateTextLength(String inputValue, int min, int max) {
        return inputValue != null
                && !inputValue.isEmpty()
                && inputValue.length() >= min
                && inputValue.length() <= max;
    }

    public static boolean validateNumber(String inputValue) {
        if (inputValue == null || inputValue.trim().isEmpty())
            return false;

        try {
            Double.parseDouble(inputValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateFloatNumber(String inputValue) {
        if (!inputValue.contains("."))
            return false;
        return validateNumber(inputValue);
    }

    public static boolean validateIntegerNumber(String inputValue) {
        if (inputValue.contains("."))
            return false;
        return validateNumber(inputValue);
    }

    public static boolean validatePhoneNumber(String inputValue) {
        if (inputValue == null || inputValue.trim().isEmpty())
            return false;

        inputValue = inputValue.trim().replace(" ", "");

        if (inputValue.startsWith("+") && inputValue.length() > 10 && inputValue.length() < 14) {
            return validateIntegerNumber(inputValue.substring(inputValue.length() - 9));
        } else if (!inputValue.startsWith("+") && inputValue.length() == 10) {
            return validateIntegerNumber(inputValue);
        }
        return false;
    }

}
