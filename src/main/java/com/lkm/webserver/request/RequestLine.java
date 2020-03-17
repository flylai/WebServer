package com.lkm.webserver.request;

import com.lkm.webserver.constant.RequestMethod;

import java.util.HashMap;

public class RequestLine {
    private HashMap<String, String> query;
    private RequestMethod method;
    private String url;
    private String path;

    public RequestLine(HashMap<String, String> query, RequestMethod method, String url, String path) {
        this.query = query;
        this.method = method;
        this.url = url;
        this.path = path;
    }

    public HashMap<String, String> getQuery() {
        return query;
    }

    public String get(String key) {
        String value = query.get(key);
        return value == null ? "" : value;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }
}
