package com.lkm.webserver.request;

import java.util.HashMap;

public class RequestHeaders {
    private HashMap<String, String> headers;
    private HashMap<String, String> cookies;

    public RequestHeaders(HashMap<String, String> headers, HashMap<String, String> cookies) {
        this.headers = headers;
        this.cookies = cookies;
    }

    public String header(String key) {
        String value = headers.get(key);
        return value == null ? "" : value;
    }

    public String cookies(String key) {
        String value = cookies.get(key);
        return value == null ? "" : value;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public HashMap<String, String> getCookies() {
        return cookies;
    }
}
