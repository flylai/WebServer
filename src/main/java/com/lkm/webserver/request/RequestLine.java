package com.lkm.webserver.request;

import com.lkm.webserver.constant.RequestMethod;

import java.util.Map;

public class RequestLine {
    private Map<String, String> query;
    private RequestMethod method;
    private String url;
    private String path;

    public RequestLine(Map<String, String> query, RequestMethod method, String url, String path) {
        this.query = query;
        this.method = method;
        this.url = url;
        this.path = path;
    }

    public String get(String key) {
        String value = query.get(key);
        return value == null ? "" : value;
    }
}
