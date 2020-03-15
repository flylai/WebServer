package com.lkm.webserver.request;

import java.util.Map;

public class RequestHeaders {
    private Map<String, String> headers;
    private Map<String, String> cookies;

    public RequestHeaders(Map<String, String> headers, Map<String, String> cookies) {
        this.headers = headers;
        this.cookies = cookies;
    }

    public String header(String key) {
        String value = headers.get(key);
        return key == null ? "" : value;
    }

    public String cookies(String key) {
        String value = cookies.get(key);
        return value == null ? "" : value;
    }
}
