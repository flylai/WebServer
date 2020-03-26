package com.lkm.webserver.request;

import com.lkm.webserver.api.HttpRequest;
import com.lkm.webserver.connection.ConnectionPool;
import com.lkm.webserver.constant.Misc;
import com.lkm.webserver.constant.RequestMethod;

import java.util.HashMap;

public class Request implements HttpRequest {
    private RequestLine requestLine;
    private RequestHeaders requestHeaders;
    private RequestBody requestBody;

    public Request(RequestLine requestLine, RequestHeaders requestHeaders, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
    }

    @Override
    public HashMap<String, String> getQueryMap() {
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
        return requestLine.getQueryString();
    }

    @Override
    public String getRequestUrl() {
        return requestLine.getUrl();
    }

    @Override
    public String getRequestPath() {
        return requestLine.getPath();
    }

    @Override
    public String getQueryValue(String key) {
        return requestLine.get(key);
    }

    @Override
    public HashMap<String, String> getCookies() {
        return requestHeaders.getCookies();
    }

    @Override
    public String getCookie(String key) {
        return requestHeaders.cookies(key);
    }

    @Override
    public String getPostData(String key) {
        if (requestBody == null) {
            return "";
        }
        String value = requestBody.getPostData().get(key);
        return value == null ? "" : value;
    }

    @Override
    public byte[] getFile(String key) {
        if (requestBody == null) {
            return new byte[0];
        }
        byte[] value = requestBody.getFile(key);
        return value == null ? new byte[0] : value;
    }

    @Override
    public String getAttribute(String key) {
        String sessionId = getCookie(Misc.SESSION_NAME);
        if (sessionId.isEmpty()) {
            return sessionId;
        }
        return ConnectionPool.getAttribute(sessionId, key);
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

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }


}
