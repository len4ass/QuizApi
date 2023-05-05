package ru.len4ass.api.validators;

import java.util.regex.Pattern;

public class PasswordValidator {
    private static final String PASSWORD_PATTERN = "^(?!.*\\s)(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,128}$";
    private static final Pattern REGEX_PATTERN = Pattern.compile(PASSWORD_PATTERN);

    private static final String INVALID_CONSTRAINTS = """
            Your password doesn't match one (or more) of the following criteria:
            1) Password must contain at least one digit [0-9].
            2) Password must contain at least one lowercase Latin character [a-z].
            3) Password must contain at least one uppercase Latin character [A-Z].
            4) Password must be between 8 and 128 characters long.
            5) Password must not contain whitespaces.
            """;

    public static String getInvalidConstraints() {
        return INVALID_CONSTRAINTS;
    }

    public static boolean isValid(String password) {
        var matcher = REGEX_PATTERN.matcher(password);
        return matcher.matches();
    }



}
