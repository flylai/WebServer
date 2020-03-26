package com.lkm.webserver.api;

import com.lkm.webserver.constant.HTTPStatus;
import com.lkm.webserver.request.Request;

public interface HttpResponse {
    void put(String key, String value);

    String get(String key);

    String getBodyByString();

    byte[] getBodyByByte();

    void setBody(String body);

    void setBody(byte[] body);

    HTTPStatus getStatus();

    void setStatus(HTTPStatus status);

    void startSession(Request request);

    void setAttribute(String key, String value);

    void removeSession();
}
