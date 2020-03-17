package com.lkm.webserver.request;

import com.lkm.webserver.api.HttpRequest;
import com.lkm.webserver.constant.RequestMethod;

import java.util.HashMap;

public class Request implements HttpRequest {
    private RequestLine requestLine;
    private RequestHeaders requestHeaders;

    public Request(RequestLine requestLine, RequestHeaders requestHeaders) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
    }

    @Override
    public HashMap<String, String> getArgv() {
        return requestLine.getQuery();
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return requestHeaders.getHeaders();
    }

    @Override
    public String getHeader(String key) {
        return requestHeaders.header(key);
    }

    @Override
    public RequestMethod getMethod() {
        return requestLine.getMethod();
    }

    @Override
    public String getQueryString() {
        // todo
        return requestLine.getUrl();
    }

    @Override
    public String getRequestUri() {
        return requestLine.getUrl();
    }

    @Override
    public HashMap<String, String> getCookies() {
        return requestHeaders.getCookies();
    }

    @Override
    public String getCookie(String key) {
        return requestHeaders.cookies(key);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public void setRequestLine(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public RequestHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(RequestHeaders requestHeaders) {
        this.requestHeaders = requestHeaders;
    }
}
