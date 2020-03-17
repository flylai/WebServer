package com.lkm.webserver.request;

import com.lkm.webserver.constant.RequestMethod;

import java.util.HashMap;

public class RequestLine {
    private HashMap<String, String> query;
    private RequestMethod method;
    private String url;
    private String path;
    private String queryString;

    public RequestLine(HashMap<String, String> query, RequestMethod method,
                       String path, String queryString) {
        this.query = query;
        this.method = method;
        this.url = path + queryString;
        this.path = path;
        this.queryString = queryString;
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

    public String getQueryString() {
        return queryString;
    }
}
