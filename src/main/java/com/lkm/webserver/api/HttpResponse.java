package com.lkm.webserver.api;

import com.lkm.webserver.constant.HTTPStatus;

public interface HttpResponse {
    void put(String key, String value);

    String get(String key);

    String getBodyByString();

    byte[] getBodyByByte();

    void setBody(String body);

    void setBody(byte[] body);

    HTTPStatus getStatus();

    void setStatus(HTTPStatus status);
}
