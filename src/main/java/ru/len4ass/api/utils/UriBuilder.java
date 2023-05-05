package ru.len4ass.api.utils;

import java.net.URI;

public class UriBuilder {
    public static URI getUri(String mainUri, String endPoint, String query) {
        return URI.create(String.format("%s%s?%s", mainUri, endPoint, query));
    }
}
