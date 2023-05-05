package ru.len4ass.api.validators;

import java.util.regex.Pattern;

public class UsernameValidator {
    private static final String USERNAME_PATTERN = "^(?!.*\\s)[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,48}[a-zA-Z0-9]$";

    private static final Pattern REGEX_PATTERN = Pattern.compile(USERNAME_PATTERN);

    private static final String INVALID_CONSTRAINTS = """
            Your username doesn't match one (or more) of the following criteria:
            1) Must consist of alphanumeric characters (a-zA-Z0-9) (lowercase or uppercase).
            2) The dot (.), underscore (_), or hyphen (-) must not be the first or the last character.
            3) The dot (.), underscore (_), or hyphen (-) does not appear consecutively, e.g., loh..cvetochniy
            4) The number of characters must be between 5 to 50.
            5) Must not contain whitespaces.
            """;

    public static String getInvalidConstraints() {
        return INVALID_CONSTRAINTS;
    }

    public static boolean isValid(String username) {
        var matcher = REGEX_PATTERN.matcher(username);
        return matcher.matches();
    }

}
