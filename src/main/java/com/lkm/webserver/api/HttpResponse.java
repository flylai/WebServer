package com.lkm.webserver.api;

import com.lkm.webserver.constant.HTTPStatus;
import com.lkm.webserver.request.Request;

public interface HttpResponse {
    void setHeader(String key, String value);

    String getBodyByString();

    byte[] getBodyByByte();

    void setBody(String body);

    void setBody(byte[] body);

    HTTPStatus getStatus();

    void setStatus(HTTPStatus status);

    void startSession(Request request);

    void setAttribute(String key, String value);

    void removeSession();

    void setCookies(String name, String value, String expires, String path, String domain, int maxAge);

    void setCookies(String name, String value);

    void writeToBrowser();
}
