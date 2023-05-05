package ru.len4ass.api.utils;

public class TokenExtractor {
    public static String extractFromHeader(String headerBody) {
        var authHeaderSplitBySpace = headerBody.split("\\s+");
        if (authHeaderSplitBySpace.length != 2) {
            throw new IndexOutOfBoundsException("Invalid Authorization header body.");
        }

        return authHeaderSplitBySpace[1];
    }
}
